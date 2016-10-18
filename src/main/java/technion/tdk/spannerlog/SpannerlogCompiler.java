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
                .filter(s -> s.getInputAtoms() != null)
                .collect(Collectors.toMap(IEFunctionSchema::getName, this::compile));
    }


    List<Map<String, String>> compile(Program program) throws IOException {
        return program.getStatements()
                .stream()
                .map(this::compile)
                .filter(map -> !map.isEmpty())
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
        cqBlock.put("inference_rule", "@weight(\"?\")\n" + compile(rule.getHead()) + " :- " + compile(rule.getBody()) + ".");
        return cqBlock;
    }

    private String compile(InferenceRuleHead head) {
        return (String) new PatternMatching(
                inCaseOf(IsTrue.class, f -> compile(head.getHeadAtoms().get(0))),
                inCaseOf(Imply.class, f -> {
                    List<DBAtom> atoms = head.getHeadAtoms();
                    List<DBAtom> lhs = head.getHeadAtoms().subList(0, atoms.size() - 1); // the last atom is the right hand side
                    return lhs.stream().map(this::compile).collect(Collectors.joining(", "))
                            + " => " + compile(atoms.get(atoms.size() - 1));
                }),
                inCaseOf(Or.class, f -> head.getHeadAtoms().stream().map(this::compile).collect(Collectors.joining(" v "))),
                inCaseOf(And.class, f -> head.getHeadAtoms().stream().map(this::compile).collect(Collectors.joining(" ^ ")))
        ).matchFor(head.getFactorFunction());
    }

    private String compile(ConjunctiveQueryBody body) {
        String compiledString = body.getBodyElements()
                .stream()
                .filter(bodyElement -> bodyElement instanceof Atom)
                .map(this::compile)
                .collect(Collectors.joining(", "));

        List<String> conditions = body.getBodyElements()
                .stream()
                .filter(bodyElement -> bodyElement instanceof Condition)
                .map(this::compile)
                .collect(Collectors.toList());

        if (!conditions.isEmpty())
            compiledString += ", " + "[" +conditions.stream().collect(Collectors.joining(", ")) + "]";

        return compiledString;
    }

    private String compile(BodyElement bodyElement) {
        return (String) new PatternMatching(
                inCaseOf(Atom.class, this::compile),
                inCaseOf(Condition.class, this::compile)
        ).matchFor(bodyElement);
    }

    private String compile(Condition condition) {
        return (String) new PatternMatching(
                inCaseOf(NegationCondition.class, nc -> "!" + compile(nc.getCond())),
                inCaseOf(ExprCondition.class, ec -> compile(ec.getExpr()))
        ).matchFor(condition);
    }

    private String compileBinaryOp(ExprTerm lhs, ExprTerm rhs, String op) {
        PatternMatching pattern = new PatternMatching(
                inCaseOf(SpanConstExpr.class, e -> String.valueOf(e.getStart())),
                inCaseOf(VarTerm.class, e -> {
                    if (e.getType() != null && e.getType().equals("span")) // TODO fix in the case of spans in "IF-ELSE" statements.
                        return e.getVarName() + "_start";
                    return compile((ExprTerm) e);
                }),
                otherwise(e -> compile((ExprTerm) e))
        );
        return pattern.matchFor(lhs) + " " + op + " " + pattern.matchFor(rhs);
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
            if (exprTerm instanceof VarTerm && !spans.isEmpty() && !((VarTerm) exprTerm).getType().equals("text"))
                throw new SpanAppliedToNonStringTypeAttributeException(((VarTerm) exprTerm).getVarName());
            if (!spans.isEmpty()) {
                SpanTerm spanTerm = spans.get(spans.size() - 1);
                spans.remove(spans.size() - 1);
                return compile(exprTerm, spanTerm);
            }
        }

        return (String) new PatternMatching(
                inCaseOf(ConstExprTerm.class, this::compile),
                inCaseOf(VarTerm.class, this::compile),
                inCaseOf(IfThenElseExpr.class, this::compile),
                inCaseOf(FuncExpr.class, this::compile),
                inCaseOf(BinaryOpExpr.class, e -> compileBinaryOp(e.getLhs(), e.getRhs(), e.getOp())),
                inCaseOf(DotFuncExpr.class, this::compile)
        ).matchFor(exprTerm);
    }

    private String compile(DotFuncExpr e) {
        VarTerm t = e.getVarTerm();
        switch (e.getFunction()) {
            case "equalsIgnoreCase":
                if (e.getArgs().size() != 1)
                    throw new IllegalArgumentException("Illegal argument in '" + e.getFunction() + "': illegal number of arguments");
                VarTerm s1 = (VarTerm) e.getArgs().get(0); // TODO handle constants
                if (!t.getType().equals(s1.getType()))
                    throw new IllegalArgumentException("Illegal argument in '" + e.getFunction() + "': incompatible data types");
                return "lower(" + compile((ExprTerm) t) + ") = lower(" + compile((ExprTerm) s1) + ")";
            case "contains":
                if (e.getArgs().size() != 1)
                    throw new IllegalArgumentException("Illegal argument in '" + e.getFunction() + "': illegal number of arguments");
                VarTerm s2 = (VarTerm) e.getArgs().get(0); // TODO handle constants
                if (!t.getType().equals("span") || !s2.getType().equals("span"))
                    throw new IllegalArgumentException("Illegal argument in '" + e.getFunction() + "': arguments must be of type 'span'");
                String tn = t.getVarName();
                String sn = s2.getVarName();
                return "(" + tn + "_start - 1) < " + sn + "_start, (" + tn + "_end + 1) > " + sn + "_end" ;
        }
        throw new UnknownFunctionException(e.getFunction());
    }

    private String compile(FuncExpr e) {
        return e.getFunction() + "(" + e.getArgs().stream().map(this::compile).collect(Collectors.joining(", ")) + ")";
    }

    private String compile(IfThenElseExpr e) {
        return "if " + compile(e.getCond()) +
                " then " + compile(e.getExpr()) +
                e.getElseIfExprs().stream().map(this::compile).collect(Collectors.joining()) +
                ((e.getElseExpr() != null) ? " else " + compile(e.getElseExpr()) : "") +
                " end";
    }

    private String compile(ElseIfExpr elif) {
        return " else if " + compile(elif.getCond()) + " then " + compile(elif.getExpr());
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
        if (varTerm.getType() != null && varTerm.getType().equals("span"))
            return varTerm.getVarName() + "_start, " + varTerm.getVarName() + "_end";

        return varTerm.getVarName();
    }
}

class SpanAppliedToNonStringTypeAttributeException extends RuntimeException {
    SpanAppliedToNonStringTypeAttributeException(String varName) {
        super("The variable '" + varName + "' must be of type string in order to apply span to it");
    }
}

class UnknownFunctionException extends RuntimeException {
    UnknownFunctionException(String functionName) {
        super("The function '" + functionName + "' is unknown");
    }
}