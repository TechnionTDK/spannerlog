package technion.tdk.spannerlog;


import technion.tdk.spannerlog.utils.match.PatternMatching;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static technion.tdk.spannerlog.utils.match.ClassPattern.inCaseOf;
import static technion.tdk.spannerlog.utils.match.OtherwisePattern.otherwise;

class SpannerlogCompiler {

    Map<String, String> compile(SpannerlogSchema schema) {
        return schema.getRelationSchemas()
                .stream()
                .filter(s -> s instanceof IEFunctionSchema)
                .map(s -> (IEFunctionSchema) s)
                .collect(Collectors.toMap(IEFunctionSchema::getName, this::compile));
    }


    List<Map<String, String>> compile(Program program) throws IOException {
        return program.getStatements()
                .stream()
                .map(this::compile)
                .collect(Collectors.toList());

    }


    private String compile(IEFunctionSchema ieFunctionSchema) {
        String name = ieFunctionSchema.getName();

        String inputAtomsBlock = ieFunctionSchema.getInputAtoms()
                .stream()
                .map(this::compile)
                .collect(Collectors.joining(", "));

        return name + " += " + name + "(" + compile(ieFunctionSchema.getInputTerm()) + ") :- " + inputAtomsBlock
                + ".";
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> compile(Statement statement) {
        return (Map<String, String>) new PatternMatching(
                inCaseOf(ExtractionRule.class, this::compile),
                inCaseOf(SupervisionRule.class, this::compile),
                inCaseOf(InferenceRule.class, this::compile),
                otherwise(stmt -> new HashMap<>())
        ).matchFor(statement);

    }

    private Map<String, String> compile(ExtractionRule rule) {
        Map<String, String> cqBlock = new HashMap<>();
        cqBlock.put("extraction_rule", compile(rule.getHead()) + " *:- " + compile(rule.getBody()) + ".");
        return cqBlock;
    }

    private Map<String, String> compile(SupervisionRule rule) {
        Map<String, String> cqBlock = new HashMap<>();
        cqBlock.put("supervision_rule", compile(rule.getHead()) + " = " + compile(rule.getSupervisionExpr())
                + " :- " + compile(rule.getBody()) + ".");
        return cqBlock;
    }

    private Map<String, String> compile(InferenceRule rule) {
        Map<String, String> cqBlock = new HashMap<>();
        cqBlock.put("inference_rule", compile(rule.getHead()) + " :- " + compile(rule.getBody()) + ".");
        return cqBlock;
    }

    private String compile(InferenceRuleHead head) {
        return (String) new PatternMatching(
                inCaseOf(IsTrue.class, f -> compile(head.getHeadAtoms().get(0))),
                inCaseOf(Imply.class, f -> {
                    List<DBAtom> atoms = head.getHeadAtoms();
                    List<DBAtom> lhs = head.getHeadAtoms().subList(0, atoms.size() - 2); // there are at least two atoms in this case.
                    return lhs.stream().map(this::compile).collect(Collectors.joining(", "))
                            + " => " + compile(atoms.get(atoms.size() - 1));
                }),
                inCaseOf(Or.class, f -> head.getHeadAtoms().stream().map(this::compile).collect(Collectors.joining(" v "))),
                inCaseOf(And.class, f -> head.getHeadAtoms().stream().map(this::compile).collect(Collectors.joining(" ^ ")))
        ).matchFor(head.getFactorFunction());
    }

    private String compile(ConjunctiveQueryBody body) {
        return body.getBodyElements()
                .stream()
                .map(this::compile)
                .collect(Collectors.joining(", "));
    }

    private String compile(BodyElement bodyElement) {
        return (String) new PatternMatching(
                inCaseOf(Atom.class, this::compile),
                inCaseOf(Condition.class, this::compile)
        ).matchFor(bodyElement);
    }

    private String compile(Condition condition) {
        return (String) new PatternMatching(
                inCaseOf(BinaryCondition.class, this::compile)
        ).matchFor(condition);
    }

    private String compile(BinaryCondition bc) {
        String lhsString;
        ExprTerm lhs = bc.getLhs();
        if (lhs instanceof VarTerm) {
            lhsString = compileVarInCondition((VarTerm) lhs);
        } else
            lhsString = compile(lhs);

        String rhsString;
        ExprTerm rhs = bc.getRhs();
        if (rhs instanceof VarTerm)
            rhsString = compileVarInCondition((VarTerm) rhs);
        else
            rhsString = compile(rhs);

        return lhsString + " " + bc.getOp() + " " + rhsString;
    }

    private String compileVarInCondition(VarTerm varTerm) {
        return varTerm.getVarName()
                + (varTerm.getType().equals("span") ? "_start" : "");
    }

    private String compile(Atom atom) {
        return (String) new PatternMatching(
                inCaseOf(DBAtom.class, this::compile),
                inCaseOf(IEAtom.class, this::compile)
        ).matchFor(atom);
    }

    private String compile(IEAtom ieAtom) {
        return ieAtom.getSchemaName() + "(" + compile(ieAtom.getTerms()) + ")";
    }

    private String compile(DBAtom dbAtom) {
        return dbAtom.getSchemaName() + "(" + compile(dbAtom.getTerms()) + ")";
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
            if (exprTerm instanceof VarTerm && spans != null && !spans.isEmpty() && !((VarTerm) exprTerm).getType().equals("text"))
                throw new SpanAppliedToNonStringTypeAttribute(((VarTerm) exprTerm).getVarName());
            if (spans != null && !spans.isEmpty()) {
                SpanTerm spanTerm = spans.get(spans.size() - 1);
                spans.remove(spans.size() - 1);
                return compile(exprTerm, spanTerm);
            }
        }

        return (String) new PatternMatching(
                inCaseOf(ConstExprTerm.class, this::compile),
                inCaseOf(VarTerm.class, this::compile),
                inCaseOf(IfThenElseExpr.class, this::compile),
                inCaseOf(FuncExpr.class, this::compile)
        ).matchFor(exprTerm);
    }

    private String compile(FuncExpr e) {
        return e.getFunction() + "(" + e.getArgs().stream().map(this::compile).collect(Collectors.joining(", ")) + ")";
    }

    private String compile(IfThenElseExpr e) {
        return "if " + compile(e.getCond()) +
                " then " + compile(e.getExpr()) +
                ((e.getElseExpr() != null) ? " else " + compile(e.getElseExpr()) : "") +
                " end";
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
                ", " + varName + "_start" +
                ", (" + varName + "_end - " + varName + "_start)" +
                ')';

        // adding the span term that was removed in compile(ExprTerm exprTerm)
        ((StringTerm) exprTerm).getSpans().add(varTerm);

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

        // adding the span term that was removed in compile(ExprTerm exprTerm)
        ((StringTerm) exprTerm).getSpans().add(spanConstExpr);

        return block;
    }

    private String compile(ConstExprTerm constExprTerm) {
        return (String) new PatternMatching(
                inCaseOf(BooleanConstExpr.class, e -> (e.getValue()) ? "TRUE" : "FALSE"),
                inCaseOf(IntConstExpr.class, e -> Integer.toString(e.getValue())),
                inCaseOf(FloatConstExpr.class, e -> Float.toString(e.getValue())),
                inCaseOf(SpanConstExpr.class, e -> Integer.toString(e.getStart()) + ", " + Integer.toString(e.getEnd())),
                inCaseOf(StringConstExpr.class, e -> "\"" + e.getValue() + "\""),
                inCaseOf(NullExpr.class, e -> "NULL")
        ).matchFor(constExprTerm);
    }

    private String compile(VarTerm varTerm) {
        if (varTerm.getType().equals("span"))
            return varTerm.getVarName() + "_start, " + varTerm.getVarName() + "_end";

        return varTerm.getVarName();
    }
}

class SpanAppliedToNonStringTypeAttribute extends RuntimeException {
    SpanAppliedToNonStringTypeAttribute(String varName) {
        super("The variable '" + varName + "' must be of type string in order to apply span to it.");
    }
}
