package technion.tdk.spannerlog;


import org.apache.commons.lang3.ObjectUtils;
import technion.tdk.spannerlog.utils.match.PatternMatching;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static technion.tdk.spannerlog.utils.match.ClassPattern.inCaseOf;
import static technion.tdk.spannerlog.utils.match.OtherwisePattern.otherwise;

class SpannerlogCompiler {

    void compile(Program program) throws IOException {

        List<RelationSchema> relationSchemas = new SpannerlogSchema().getRelationSchemas(program);

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
            return "True";
        return "False";
    }

    private String compile(StringConstExpr stringConstExpr) {
        return "\"" + stringConstExpr.getValue() + "\"";
    }

    private String compile(VarTerm varTerm) {
        return varTerm.getVarName();
    }
}

class SpannerlogSchema {

    List<RelationSchema> getRelationSchemas(Program program) {
        return mergeRelationSchemas(collectRelationSchemas(program));
    }

    private List<RelationSchema> mergeRelationSchemas(List<RelationSchema> relationSchemas) {
        Map<String, RelationSchema> relationSchemaMap = relationSchemas
                .stream()
                .collect(
                        Collectors.toMap(
                                RelationSchema::getName,
                                Function.identity(),
                                this::mergeRelationSchemas
                        )
                );

        return new ArrayList<>(relationSchemaMap.values());
    }

    private RelationSchema mergeRelationSchemas(RelationSchema oldValue, RelationSchema newValue) {
        return new RelationSchema(newValue.getName(), mergeAttributes(oldValue.getAttrs(), newValue.getAttrs()));
    }

    private List<Attribute> mergeAttributes(List<Attribute> oldAttrs, List<Attribute> newAttrs) {
        oldAttrs.addAll(newAttrs);

        Map<String, Attribute> attrMap = oldAttrs
                .stream()
                .collect(
                        Collectors.toMap(
                                Attribute::getName,
                                Function.identity(),
                                this::mergeAttributes
                        )
                );
        return new ArrayList<>(attrMap.values());
    }

    private Attribute mergeAttributes(Attribute oldValue, Attribute newValue) {
        String oldName = oldValue.getName();
        String newName = newValue.getName();

        String oldType = oldValue.getType();
        String newType = newValue.getType();

        if (oldName != null && newName != null && !oldName.equals(newName)) {
            throw new AttributeNameConflictException();
        }

        if (oldType != null && newType != null && !oldType.equals(newType)) {
            throw new AttributeTypeConflictException();
        }

        return new Attribute(oldName, ObjectUtils.firstNonNull(oldType, newType));
    }

    private List<RelationSchema> collectRelationSchemas(Program program) {
        return program.getStatements()
                .stream()
                .flatMap(stmt -> collectRelationSchemas(stmt).stream())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @SuppressWarnings("unchecked")
    private List<RelationSchema> collectRelationSchemas(Statement statement) {
        return (List<RelationSchema>) new PatternMatching(
                inCaseOf(ConjunctiveQuery.class, this::collectRelationSchemas)
        ).matchFor(statement);
    }

    private List<RelationSchema> collectRelationSchemas(ConjunctiveQuery cq) {
        List<RelationSchema> relationSchemas = collectRelationSchemas(cq.getBody());
        relationSchemas.add(collectRelationSchema(cq.getHead()));
        return relationSchemas;
    }

    private List<RelationSchema> collectRelationSchemas(ConjunctiveQueryBody body) {
        return body.getBodyAtoms()
                .stream()
                .map(atom -> collectRelationSchema(atom, body))
                .collect(Collectors.toList());
    }

    private RelationSchema collectRelationSchema(Atom atom, ConjunctiveQueryBody body) {
        return (RelationSchema) new PatternMatching(
                inCaseOf(DBAtom.class, this::collectRelationSchema),
                inCaseOf(IEFunction.class, ieFunction -> collectRelationSchema(ieFunction, body))
        ).matchFor(atom);
    }

    private RelationSchema collectRelationSchema(IEFunction ieFunction, ConjunctiveQueryBody body) {
        return new IEFunctionSchema(ieFunction.getSchemaName(), collectAttributes(ieFunction.getTerms()),
                ieFunction.getInputTerm(), body, ieFunction);
    }

    private RelationSchema collectRelationSchema(ConjunctiveQueryHead head) {
        return collectRelationSchema(head.getHeadAtom());
    }


    private RelationSchema collectRelationSchema(DBAtom atom) {
        return new RelationSchema(atom.getSchemaName(), collectAttributes(atom.getTerms()));
    }

    private List<Attribute> collectAttributes(List<Term> terms) {
        DefaultValuesGenerator valGenerator = new DefaultValuesGenerator();

        if (terms.isEmpty())
            return Stream.of(valGenerator.generate(new Attribute(null, "boolean"))).collect(Collectors.toList());

        return terms
                .stream()
                .map(this::collectAttribute)
                .map(valGenerator::generate)
                .collect(Collectors.toList());
    }

    private class DefaultValuesGenerator {
        private int counter = 1;

        Attribute generate(Attribute attr) {
            String name = attr.getName();
            if (name == null) {
                name = "column" + counter;
                counter++;
            }

            String type = attr.getType();
            if (type == null) {
                type = "text";
            }

            return new Attribute(name, type);
        }
    }

    private Attribute collectAttribute(Term term) {
        return (Attribute) new PatternMatching(
                inCaseOf(PlaceHolderTerm.class, t -> new Attribute()),
                inCaseOf(ExprTerm.class, this::collectAttribute)
        ).matchFor(term);
    }

    private Attribute collectAttribute(ExprTerm exprTerm) {
        return (Attribute) new PatternMatching(
                inCaseOf(ConstExprTerm.class, this::collectAttribute),
                inCaseOf(VarTerm.class, t -> new Attribute())
        ).matchFor(exprTerm);
    }

    private Attribute collectAttribute(ConstExprTerm constExprTerm) {
        return (Attribute) new PatternMatching(
                inCaseOf(BooleanConstExpr.class, t -> new Attribute(null, "boolean")),
                inCaseOf(IntConstExpr.class, t -> new Attribute(null, "int")),
                inCaseOf(FloatConstExpr.class, t -> new Attribute(null, "float8")),
                inCaseOf(SpanConstExpr.class, t -> new Attribute(null, "varchar(10)")),
                inCaseOf(StringConstExpr.class, t -> new Attribute(null, "text"))
        ).matchFor(constExprTerm);
    }
}

class RelationSchema {
    private String name;
    private List<Attribute> attrs;

    RelationSchema(String name, List<Attribute> attrs) {
        this.name = name;
        this.attrs = attrs;
    }

    public String getName() {
        return name;
    }

    List<Attribute> getAttrs() {
        return attrs;
    }

    @Override
    public String toString() {
        return "RelationSchema{" +
                "name='" + name + '\'' +
                ", attrs=" + attrs +
                '}';
    }
}

class IEFunctionSchema extends RelationSchema {
    private Term inputTerm;
    private List<Atom> inputAtoms;

    IEFunctionSchema(String name, List<Attribute> attrs, Term inputTerm, ConjunctiveQueryBody cqBody,
                     IEFunction ieFunction) {
        super(name, attrs);
        this.inputTerm = inputTerm;
        this.inputAtoms = determineInputAtoms(cqBody, ieFunction);
    }

    List<Atom> getInputAtoms() {
        return inputAtoms;
    }

    Term getInputTerm() {
        return inputTerm;
    }

    private List<Atom> determineInputAtoms(ConjunctiveQueryBody cqBody, IEFunction ieFunction) {
        List<Atom> bodyAtoms = cqBody.getBodyAtoms();
        DependenciesGraph depGraph = createDependenciesGraph(bodyAtoms);
        List<Integer> dependencies = depGraph.getDependencies(bodyAtoms.indexOf(ieFunction));
        return dependencies
                .stream()
                .map(bodyAtoms::get)
                .collect(Collectors.toList());
    }

    private DependenciesGraph createDependenciesGraph(List<Atom> bodyAtoms) {
        Map<Integer, List<Integer>> adjacencyList =
                IntStream.range(0, bodyAtoms.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> i, i -> calcDependencies(i, bodyAtoms)));

        return new DependenciesGraph(adjacencyList);
    }

    private List<Integer> calcDependencies(int idx, List<Atom> bodyAtoms) {
        List<Integer> dependencies = new ArrayList<>();

        Atom atom = bodyAtoms.get(idx);
        if (atom instanceof IEAtom) {
            int n = bodyAtoms.size();
            for (int i = 0; i < n; i++) {
                if (i == idx)
                    continue;
                if (isDependent((IEAtom) atom, bodyAtoms.get(i)))
                    dependencies.add(i);
            }
        }
        return dependencies;
    }

    private boolean isDependent(IEAtom a1, Atom a2) {
        return (boolean) new PatternMatching(
                inCaseOf(IEAtom.class, t -> isDependent(a1, (IEAtom) a2)),
                inCaseOf(DBAtom.class, t -> isDependent(a1, (DBAtom) a2))
        ).matchFor(a2);
    }

    private boolean isDependent(IEAtom a1, IEAtom a2) {
        List<String> varNames1 = getVars(a1.getInputTerm());

        List<Term> terms = a2.getTerms();
        Term inputTerm = a2.getInputTerm();

        if (inputTerm != null) {
            terms.remove(inputTerm);
        }

        List<String> varNames2 = terms
                .stream()
                .flatMap(term -> getVars(term).stream())
                .collect(Collectors.toList());

        if (inputTerm != null) {
            terms.add(0, inputTerm);
        }

        varNames1.retainAll(varNames2);
        return !varNames1.isEmpty();
    }

    private boolean isDependent(IEAtom a1, DBAtom a2) {
        List<String> varNames1 = getVars(a1.getInputTerm());

        List<String> varNames2 = a2.getTerms()
                .stream()
                .flatMap(term -> getVars(term).stream())
                .collect(Collectors.toList());

        varNames1.retainAll(varNames2);
        return !varNames1.isEmpty();
    }

    @SuppressWarnings("unchecked")
    private List<String> getVars(Term term) {
        return (List<String>) new PatternMatching(
                inCaseOf(PlaceHolderTerm.class, t -> Collections.<String>emptyList()),
                inCaseOf(ExprTerm.class, this::getVars)
        ).matchFor(term);
    }

    @SuppressWarnings("unchecked")
    private List<String> getVars(ExprTerm exprTerm) {
        List<String> vars = (List<String>) new PatternMatching(
                inCaseOf(VarTerm.class, varTerm -> Stream.of(varTerm.getVarName()).collect(Collectors.toList())),
                otherwise(t -> Collections.<String>emptyList())
        ).matchFor(exprTerm);

        if (exprTerm instanceof StringTerm) {
            List<SpanTerm> spans = ((StringTerm) exprTerm).getSpans();
            if (spans != null) {
                vars.addAll(spans
                        .stream()
                        .flatMap(s -> getVars(s).stream())
                        .collect(Collectors.toList()));
            }
        }
        return vars;
    }

    @SuppressWarnings("unchecked")
    private List<String> getVars(SpanTerm spanTerm) {
        return (List<String>) new PatternMatching(
                inCaseOf(VarTerm.class, varTerm -> Stream.of(varTerm.getVarName()).collect(Collectors.toList())),
                otherwise(t -> Collections.<String>emptyList())
        ).matchFor(spanTerm);
    }
}

class DependenciesGraph {
    private Map<Integer, List<Integer>> adjacencyList;

    DependenciesGraph(Map<Integer, List<Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    private List<Integer> outEdges(int i) {
        return adjacencyList.get(i);
    }

    private int nVertices() {
        return adjacencyList.size();
    }

    List<Integer> getDependencies(int startIdx) {

        Boolean[] visited = new Boolean[this.nVertices()];
        Arrays.fill(visited, Boolean.FALSE);
        Stack<Integer> s = new Stack<>();
        s.push(startIdx);
        while (!s.isEmpty()) {
            int i = s.pop();
            if (!visited[i]) {
                visited[i] = true;
                for (int j : this.outEdges(i)) {
                    if (visited[j])
                        throw new CircularDependencyException();
                    s.push(j);
                }
            }
        }
        return IntStream.range(0, visited.length)
                .filter(i -> visited[i] && i != startIdx)
                .boxed()
                .collect(Collectors.toList());
    }

    private class CircularDependencyException extends RuntimeException {
    }
}

class Attribute {
    private String name;

    private String type;

    Attribute() {
    }

    Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}

class AttributeTypeConflictException extends RuntimeException {

}

class AttributeNameConflictException extends RuntimeException {

}
