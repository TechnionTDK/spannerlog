package technion.tdk.spannerlog;


import technion.tdk.spannerlog.utils.match.PatternMatching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static technion.tdk.spannerlog.utils.match.ClassPattern.inCaseOf;

class SpannerlogCompiler {

    List<String> compile(Program program) throws IOException {
        return program.getStatements()
                .stream()
                .map(this::compile)
                .collect(Collectors.toList());
    }

    private String compile(RelationSchema relationSchema) {
        if (relationSchema instanceof AmbiguousRelationSchema)
            throw new UndefinedRelationSchema(relationSchema.getName());

        return relationSchema.getName() +
                "(\n\t" +
                relationSchema.getAttrs()
                        .stream()
                        .flatMap(attribute -> compile(attribute).stream())
                        .collect(Collectors.joining(",\n\t")) +
                "\n\t).";
    }

    private List<String> compile(IEFunctionSchema ieFunctionSchema) {
        ArrayList<String> blocks = new ArrayList<>();
        String name = ieFunctionSchema.getName();

        blocks.add("function " + name + " over (s text)\n" +
                "\treturns rows like " + name + "\n" +
                "\timplementation \"udf/" + name + ".py\" handles tsv lines.");

        String inputAtomsBlock = ieFunctionSchema.getInputAtoms()
                .stream()
                .map(this::compile)
                .collect(Collectors.joining(", "));
        blocks.add(name + " += " + name + "(" + compile(ieFunctionSchema.getInputTerm(),
                ieFunctionSchema.getAttrs().get(0)) + ") :- " + inputAtomsBlock
                + ".");

        return blocks;
    }

    private List<String> compile(Attribute attribute) {
        if (attribute.getType() == null)
            throw new AttributeTypeCannotBeInferredException(attribute);

        List<String> attrBlock = new ArrayList<>();

        if (attribute.getType().equals("span")) {
            attrBlock.add(String.format("%-15s int", (attribute.getName()+"_start")));
            attrBlock.add(String.format("%-15s int", (attribute.getName()+"_end")));
        } else {
            attrBlock.add(String.format("%-15s %s", attribute.getName(), attribute.getType()));
        }

        return attrBlock;
    }

    private String compile(Statement statement) {
        return (String) new PatternMatching(
                inCaseOf(ConjunctiveQuery.class, this::compile)
        ).matchFor(statement);

    }

    private String compile(ConjunctiveQuery cq) {
        return compile(cq.getHead()) + " :- " + compile(cq.getBody()) + ".";
    }

    private String compile(ConjunctiveQueryBody body) {
        return body.getBodyAtoms()
                .stream()
                .map(this::compile)
                .collect(Collectors.joining(", "));
    }

    private String compile(Atom atom) {
        return (String) new PatternMatching(
                inCaseOf(DBAtom.class, this::compile),
                inCaseOf(IEAtom.class, this::compile)
        ).matchFor(atom);
    }

    private String compile(ConjunctiveQueryHead head) {
        return compile(head.getHeadAtom());
    }

    private String compile(IEAtom ieAtom) {
        return ieAtom.getSchemaName() + "(" + compile(ieAtom.getTerms(), ieAtom.getSchema()) + ")";
    }

    private String compile(DBAtom dbAtom) {
        return dbAtom.getSchemaName() + "(" + compile(dbAtom.getTerms(), dbAtom.getSchema()) + ")";
    }

    private String compile(List<Term> terms, RelationSchema schema) {
        if (terms.isEmpty())
            return "TRUE";

        return terms
                .stream()
                .map(term -> compile(term, schema.getAttrs().get(terms.indexOf(term))))
                .collect(Collectors.joining(", "));
    }

    private String compile(Term term, Attribute attr) {
        return (String) new PatternMatching(
                inCaseOf(PlaceHolderTerm.class, t -> "_"),
                inCaseOf(ExprTerm.class, t -> compile(t, attr))
        ).matchFor(term);
    }

    private String compile(ExprTerm exprTerm, Attribute attr) {
        if (exprTerm instanceof StringTerm) {
            List<SpanTerm> spans = ((StringTerm) exprTerm).getSpans();
            if (spans != null && !spans.isEmpty()) {
                SpanTerm spanTerm = spans.get(spans.size() - 1);
                spans.remove(spans.size() - 1);
                return compile(exprTerm, spanTerm, attr);
            }
        }

        return (String) new PatternMatching(
                inCaseOf(ConstExprTerm.class, this::compile),
                inCaseOf(VarTerm.class, t -> compile(t, attr))
        ).matchFor(exprTerm);
    }

    private String compile(ExprTerm exprTerm, SpanTerm spanTerm, Attribute attr) {
        return (String) new PatternMatching(
                inCaseOf(SpanConstExpr.class, spanConstExpr -> compile(exprTerm, spanConstExpr, attr)),
                inCaseOf(VarTerm.class, varTerm -> compile(exprTerm, varTerm, attr))
        ).matchFor(spanTerm);
    }

    private String compile(ExprTerm exprTerm, VarTerm varTerm, Attribute attr) {
        String varName = varTerm.getVarName();

        String block = "substr(" +
                compile(exprTerm, attr) +
                ", " + varName + "_start" +
                ", (" + varName + "_end - " + varName + "_start)" +
                ')';

        // adding the span term that was removed in compile(ExprTerm exprTerm)
        ((StringTerm) exprTerm).getSpans().add(varTerm);

        return block;
    }

    private String compile(ExprTerm exprTerm, SpanConstExpr spanConstExpr, Attribute attr) {
        int start = spanConstExpr.getStart();
        int end = spanConstExpr.getEnd();

        String block = "substr(" +
                compile(exprTerm, attr) +
                ", " + start +
                ", " + (end - start) +
                ')';

        // adding the span term that was removed in compile(ExprTerm exprTerm)
        ((StringTerm) exprTerm).getSpans().add(spanConstExpr);

        return block;
    }

    private String compile(ConstExprTerm constExprTerm) {
        return (String) new PatternMatching(
                inCaseOf(BooleanConstExpr.class, this::compile),
                inCaseOf(IntConstExpr.class, this::compile),
                inCaseOf(FloatConstExpr.class, this::compile),
                inCaseOf(SpanConstExpr.class, this::compile),
                inCaseOf(StringConstExpr.class, this::compile)
        ).matchFor(constExprTerm);
    }

    private String compile(IntConstExpr intConstExpr) {
        return Integer.toString(intConstExpr.getValue());
    }

    private String compile(FloatConstExpr floatConstExpr) {
        return Float.toString(floatConstExpr.getValue());
    }

    private String compile(SpanConstExpr spanConstExpr) {
        return Integer.toString(spanConstExpr.getStart()) + ", " + Integer.toString(spanConstExpr.getEnd());
    }

    private String compile(BooleanConstExpr booleanConstExpr) {
        if (booleanConstExpr.getValue())
            return "TRUE";
        return "FALSE";
    }

    private String compile(StringConstExpr stringConstExpr) {
        return "\"" + stringConstExpr.getValue() + "\"";
    }

    private String compile(VarTerm varTerm, Attribute attr) {
        if (attr.getType().equals("span"))
            return varTerm.getVarName() + "_start, " + varTerm.getVarName() + "_end";

        return varTerm.getVarName();
    }
}
