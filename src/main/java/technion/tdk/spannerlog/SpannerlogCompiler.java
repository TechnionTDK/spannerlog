package technion.tdk.spannerlog;


import technion.tdk.spannerlog.utils.match.PatternMatching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static technion.tdk.spannerlog.utils.match.ClassPattern.inCaseOf;

class SpannerlogCompiler {

    void compile(Program program, SpannerlogSchema schema) throws IOException {

        List<RelationSchema> relationSchemas = schema.getRelationSchemas();

        List<String> schemaDeclarationBlocks = relationSchemas
                .stream()
                .map(this::compile)
                .collect(Collectors.toList());

        List<String> udfDeclarationBlocks = relationSchemas
                .stream()
                .filter(s -> s instanceof IEFunctionSchema)
                .flatMap(s -> compile((IEFunctionSchema) s).stream())
                .collect(Collectors.toList());

        List<String> statementBlocks = program.getStatements()
                .stream()
                .map(this::compile)
                .collect(Collectors.toList());


        for (String block : schemaDeclarationBlocks) {
            System.out.println(block + '\n');
        }

        for (String block : udfDeclarationBlocks) {
            System.out.println(block + '\n');
        }

        statementBlocks.forEach(System.out::println);
    }

    private String compile(RelationSchema relationSchema) {
        return relationSchema.getName() +
                "(\n\t" +
                relationSchema.getAttrs()
                        .stream()
                        .map(this::compile)
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
        blocks.add(name + " += " + name + "(" + compile(ieFunctionSchema.getInputTerm()) + ") :- " + inputAtomsBlock
                + ".");

        return blocks;
    }

    private String compile(Attribute attribute) {
        if (attribute.getType() == null)
            throw new AttributeTypeCannotBeInferredException();

        return attribute.getName() + "\t\t" + attribute.getType();
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
                inCaseOf(IEFunction.class, this::compile)
        ).matchFor(atom);
    }

    private String compile(ConjunctiveQueryHead head) {
        return compile(head.getHeadAtom());
    }

    private String compile(IEFunction ieFunction) {
        return ieFunction.getSchemaName() + "(" + compile(ieFunction.getTerms()) + ")";
    }

    private String compile(DBAtom atom) {
        return atom.getSchemaName() + "(" + compile(atom.getTerms()) + ")";
    }

    private String compile(List<Term> terms) {
        if (terms.isEmpty())
            return "TRUE";

        return terms
                .stream()
                .map(this::compile)
                .collect(Collectors.joining(", "));
    }

    private String compile(Term term) {
        return (String) new PatternMatching(
                inCaseOf(PlaceHolderTerm.class, t -> "_"),
                inCaseOf(ExprTerm.class, this::compile)
        ).matchFor(term);
    }

    private String compile(ExprTerm exprTerm) {
        if (exprTerm instanceof StringTerm) {
            List<SpanTerm> spans = ((StringTerm) exprTerm).getSpans();
            if (spans != null && !spans.isEmpty()) {
                SpanTerm spanTerm = spans.get(spans.size() - 1);
                spans.remove(spans.size() - 1);
                return compile(exprTerm, spanTerm);
            }
        }

        return (String) new PatternMatching(
                inCaseOf(ConstExprTerm.class, this::compile),
                inCaseOf(VarTerm.class, this::compile)
        ).matchFor(exprTerm);
    }

    private String compile(ExprTerm exprTerm, SpanTerm spanTerm) {
        return (String) new PatternMatching(
                inCaseOf(SpanConstExpr.class, spanConstExpr -> compile(exprTerm, spanConstExpr)),
                inCaseOf(VarTerm.class, varTerm -> compile(exprTerm, varTerm))
        ).matchFor(spanTerm);
    }

    private String compile(ExprTerm exprTerm, VarTerm varTerm) {
        String varName = varTerm.getVarName();

        String block = "substr(" +
                compile(exprTerm) +
                ", split_part(" + varName + ", \",\", 1)::int" +
                ", (split_part(" + varName + ", \",\", 2)::int) - (split_part(" + varName + ", \",\", 1)::int)" +
                ')';

//        assert (exprTerm instanceof StringTerm);
        ((StringTerm) exprTerm).getSpans().add(varTerm); // adding the span term that was removed
        // in compile(ExprTerm exprTerm)

        return block;
    }

    private String compile(ExprTerm exprTerm, SpanConstExpr spanConstExpr) {
        int start = spanConstExpr.getStart();
        int end = spanConstExpr.getEnd();

        String block = "substr(" +
                compile(exprTerm) +
                ", " + start +
                ", " + (end - start) +
                ')';

//        assert (exprTerm instanceof StringTerm);
        ((StringTerm) exprTerm).getSpans().add(spanConstExpr); // adding the span term that was removed
        // in compile(ExprTerm exprTerm)

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
        return "\"" + Integer.toString(spanConstExpr.getStart()) + "," + Integer.toString(spanConstExpr.getEnd()) + "\"";
    }

    private String compile(BooleanConstExpr booleanConstExpr) {
        if (booleanConstExpr.getValue())
            return "TRUE";
        return "FALSE";
    }

    private String compile(StringConstExpr stringConstExpr) {
        return "\"" + stringConstExpr.getValue() + "\"";
    }

    private String compile(VarTerm varTerm) {
        return varTerm.getVarName();
    }
}
