package technion.tdk.spannerlog;


import technion.tdk.spannerlog.utils.match.PatternMatching;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


    Map<String, List<CompiledStmt>> compile(Program program) throws IOException {
        return program.getStatements()
                .stream()
                .map(this::compile)
                .filter(c -> c != null && c.getKey() != null)
                .collect(Collectors.toMap(CompiledStmt::getKey, s -> {
                    ArrayList<CompiledStmt> l = new ArrayList<>();
                    l.add(s);
                    return l;
                }, (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }));
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

    private CompiledStmt compile(Statement statement) {
        return (CompiledStmt) new PatternMatching(
                inCaseOf(ExtractionRule.class, this::compile),
                inCaseOf(SupervisionRule.class, this::compile),
                inCaseOf(InferenceRule.class, this::compile),
                otherwise(stmt -> null)
        ).matchFor(statement);

    }

    private CompiledStmt compile(ExtractionRule rule) {
        CompiledExtractionRule compiledRule = new CompiledExtractionRule();

        compiledRule.setKey(rule.getHead().getSchemaName());
        List<BodyElement> bodyElements = rule.getBody().getBodyElements();
        if (bodyElements.stream().anyMatch(e -> e instanceof IEAtom)) {
            compiledRule.setValue(new QueryCompiler(rule, this).generateSQL());
            compiledRule.setTarget("sql");
        }
        else
            compiledRule.setValue(compile(rule.getHead()) + " *:- " + compile(rule.getBody()) + ".");

        return compiledRule;
    }

    private CompiledStmt compile(SupervisionRule rule) {
        CompiledSupervisionRule compiledRule = new CompiledSupervisionRule();
        compiledRule.setKey(rule.getHead().getSchemaName());
        compiledRule.setValue(compile(rule.getHead())
                + " = if " + compile(rule.getPosCond()) + " then TRUE"
                + " else if " + compile(rule.getNegCond()) + " then FALSE"
                + " else NULL end"
                + " :- " + compile(rule.getBody()) + ".");
        return compiledRule;
    }

    private CompiledStmt compile(InferenceRule rule) {
        CompiledInferenceRule compiledRule = new CompiledInferenceRule();
        compiledRule.setValue("@weight(" + compile(rule.getWeight()) + ")\n" + compile(rule.getHead()) + " :- " + compile(rule.getBody()) + ".");
        return compiledRule;
    }

    private String compile(FactorWeight weight) {
        Float value = weight.getValue();
        String featureVar = weight.getFeatureVariable();

        if (value != null)
            return Float.toString(value);
        if (featureVar != null)
            return featureVar; // TODO check if variable is bound
        return "\"?\"";
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
                        return e.getName() + "_start";
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

    String compile(Term term) {
        return (String) new PatternMatching(
                inCaseOf(PlaceHolderTerm.class, t -> "_"),
                inCaseOf(ExprTerm.class, this::compile)
        ).matchFor(term);
    }

    private String compile(ExprTerm exprTerm) {
        if (exprTerm instanceof StringTerm) {
            List<SpanTerm> spans = ((StringTerm) exprTerm).getSpans();
            if (exprTerm instanceof VarTerm && !spans.isEmpty() && !((VarTerm) exprTerm).getType().equals("text"))
                throw new SpanAppliedToNonStringTypeAttributeException(((VarTerm) exprTerm).getName());
            if (!spans.isEmpty()) {
                SpanTerm spanTerm = spans.get(spans.size() - 1);
                spans.remove(spans.size() - 1);
                return compile(exprTerm, spanTerm);
            }
        }

        return (String) new PatternMatching(
                inCaseOf(ConstExprTerm.class, this::compile),
                inCaseOf(VarTerm.class, this::compile),
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
                String tn = t.getName();
                String sn = s2.getName();
                return "(" + tn + "_start - 1) < " + sn + "_start, (" + tn + "_end + 1) > " + sn + "_end" ;
        }
        throw new UnknownFunctionException(e.getFunction());
    }

    private String compile(FuncExpr e) {
        return e.getFunction() + "(" + e.getArgs().stream().map(this::compile).collect(Collectors.joining(", ")) + ")";
    }

    private String compile(ExprTerm exprTerm, SpanTerm spanTerm) {
        return (String) new PatternMatching(
                inCaseOf(SpanConstExpr.class, spanConstExpr -> compile(exprTerm, spanConstExpr)),
                inCaseOf(VarTerm.class, varTerm -> compile(exprTerm, varTerm))
        ).matchFor(spanTerm);
    }

    private String compile(ExprTerm exprTerm, VarTerm varTerm) {
        String varName = varTerm.getName();

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
            return varTerm.getName() + "_start, " + varTerm.getName() + "_end";

        return varTerm.getName();
    }
}

class QueryCompiler {
    private RuleWithConjunctiveQuery query;
    private Map<String, Variable> dbVars = new HashMap<>();
    private SpannerlogCompiler compiler;

    private class Variable {
        int attrIndex;
        int relIndex;
        VarTerm varTerm;
    }

    QueryCompiler(RuleWithConjunctiveQuery query, SpannerlogCompiler compiler) {
        this.query = query;
        this.compiler = compiler;
        generateCanonicalDBVarMap();
    }

    // Maps each variable name to a canonical version of itself (first occurrence in body in left-to-right order)
    // This is useful to translate to SQL (join conditions, select, etc.)
    private void generateCanonicalDBVarMap() {

        List<BodyElement> bodyElements = query.getBody().getBodyElements();

        IntStream.range(0, bodyElements.size())
                .forEach( i -> {
                    if (bodyElements.get(i) instanceof Atom) {
                        Atom a = (Atom) bodyElements.get(i);
                        List<Term> terms = a.getTerms();
                        IntStream.range((a instanceof IEAtom) ? 1 : 0, terms.size())
                                .filter(j -> terms.get(j) instanceof VarTerm)
                                .forEach(j -> {
                                    VarTerm v = (VarTerm) terms.get(j);
                                    if (!dbVars.containsKey(v.getName())) {
                                        Variable var = new Variable();
                                        var.varTerm = v;
                                        var.relIndex = i;
                                        var.attrIndex = j;
                                        dbVars.put(v.getName(), var);
                                    }
                                });
                    }
                });
    }

    String generateSQL() {
        String head = generateSQLHead();
        String body = generateSQLBody();

        return head + body; // TODO support union of CQs.

    }

    private String generateSQLHead() {
        List<Term> terms = ((ExtractionRule) query).getHead().getTerms();
        if (terms.isEmpty()) {
            return "SELECT TRUE";
        }
        StringJoiner joiner = new StringJoiner(", ");
        List<Attribute> attrs = ((ExtractionRule) query).getHead().getSchema().getAttrs();
        IntStream.range(0, terms.size()).forEach( i -> {
            Term t = terms.get(i);

            String attrName = attrs.get(i).getName();

            if (t instanceof VarTerm) {
                ((VarTerm) t).setName(resolveCanonicalAttr(t));
            }

            if (t instanceof VarTerm && ((VarTerm) t).getType().equals("span")) {
                joiner.add(((VarTerm) t).getName() + "_start AS " + attrName + "_start, " + ((VarTerm) t).getName() + "_end AS " + attrName + "_end");
            } else {
                String compiledTerm = compiler.compile(t);
                joiner.add(compiledTerm + " AS " + attrName);
            }
        });

        return "SELECT " + joiner;
    }

    private String maybeToSpanStart(VarTerm t, String compiledAttr) {
        if (t.getType().equals("span"))
            return compiledAttr + "_start";
        return compiledAttr;
    }

    private String maybeToSpanEnd(VarTerm t, String compiledAttr) {
        if (t.getType().equals("span"))
            return compiledAttr + "_end";
        return compiledAttr;
    }

    private String generateSQLBody() {

        return " FROM " + generateFromClause() + optionalClause(" WHERE", generateWhereClause());
    }

    private String generateWhereClause() {

        List<BodyElement> bodyElements = query.getBody().getBodyElements();

        StringJoiner joiner = new StringJoiner(" AND ");

        IntStream.range(0, bodyElements.size())
                .forEach(i -> {
                    if (bodyElements.get(i) instanceof Atom) {
                        Atom a = (Atom) bodyElements.get(i);
                        List<Term> terms = a.getTerms();
                        IntStream.range((a instanceof IEAtom) ? 1 : 0, terms.size())
                                .forEach(j -> {
                                    Term t = terms.get(j);
                                    if (t instanceof VarTerm && dbVars.containsKey(((VarTerm) terms.get(j)).getName())) {
                                        VarTerm v = (VarTerm) t;
                                        Variable var = dbVars.get((v).getName());
                                        if (i != var.relIndex) {
                                            joiner.add(maybeToSpanStart(v, resolveAttr(v, i)) + " = " + maybeToSpanStart(var.varTerm, resolveCanonicalAttr(var.varTerm)));
                                        }
                                    } else if (t instanceof StringConstExpr) {
                                        joiner.add("R" + i + "." + t.getAttribute().getName() + " = '"
                                                + ((StringConstExpr) t).getValue() + "'");
//                                    } else if (t instanceof IntConstExpr) {
//                                        joiner.add("R" + i + "." + t.getAttribute().getName() + " = '"
//                                                + ((IntConstExpr) t).getValue() + "'");
                                    } else if (t instanceof PlaceHolderTerm) {
                                        return;
                                    } else {
                                        throw new UnsupportedOperationException(); // TODO remove exception
                                    }
                                });
                    }
                });

        return joiner.toString();
    }

    private String optionalClause(String prefix, String clause) {
        if (clause.isEmpty())
            return "";
        return prefix + " " + clause;
    }

    private String generateFromClause() {

        StringJoiner relationsJoiner = new StringJoiner(", ");

        // Handle DB atoms
        List<Atom> atoms = query.getBody().getBodyElements()
                .stream()
                .filter(e -> e instanceof Atom)
                .map(e -> (Atom) e)
                .collect(Collectors.toList());

        IntStream.range(0, atoms.size())
                .forEach(i ->
                    new PatternMatching(
                        inCaseOf(DBAtom.class, a -> relationsJoiner.add(atoms.get(i).getSchemaName() + " R" + i)),
                        inCaseOf(IEAtom.class, a -> relationsJoiner.add(atoms.get(i).getSchemaName() + "("
                                  + resolveCanonicalAttr(((IEAtom) atoms.get(i)).getInputTerm()) + ") R" + i)
                        )
                    ).matchFor(atoms.get(i))
                );

        return relationsJoiner.toString();
    }

    private String resolveAttr(Term t, int i) {
        if (t instanceof VarTerm) {
            return "R" + i + "." + t.getAttribute().getName();
        }

        throw new UnsupportedOperationException(); // TODO remove exception
    }

    private String resolveCanonicalAttr(Term t) {
        if (t instanceof VarTerm) {
            VarTerm v = (VarTerm) t;
            if (dbVars.containsKey(v.getName())) {
                Variable var = dbVars.get(v.getName());
                return resolveAttr(var.varTerm, var.relIndex);
            } else
                throw new UndefinedInputVariableException(v.getName());
        }

        throw new UnsupportedOperationException(); // TODO remove exception
    }
}

class CompiledStmt {
    private String key;
    private String value;
    private String target = "ddlog";

    String getTarget() {
        return target;
    }

    void setTarget(String target) {
        this.target = target;
    }

    String getKey() {
        return key;
    }

    void setKey(String key) {
        this.key = key;
    }

    String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }
}

class CompiledExtractionRule extends CompiledStmt {}
class CompiledSupervisionRule extends CompiledStmt {}
class CompiledInferenceRule extends CompiledStmt {}


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

class UndefinedInputVariableException extends RuntimeException {
    UndefinedInputVariableException(String varName) {
        super("The variable '" + varName + "' is undefined");
    }
}