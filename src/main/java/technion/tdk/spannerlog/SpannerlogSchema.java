package technion.tdk.spannerlog;


import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.ObjectUtils;
import technion.tdk.spannerlog.utils.match.PatternMatching;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static technion.tdk.spannerlog.utils.match.ClassPattern.inCaseOf;
import static technion.tdk.spannerlog.utils.match.OtherwisePattern.otherwise;


class SpannerlogSchema {

    private List<RelationSchema> relationSchemas = new ArrayList<>();

    void readSchemaFromJsonFile(String schemaPath) throws IOException {
        try (JsonReader reader = new JsonReader(new FileReader(schemaPath))){
            List<RelationSchema> relationSchemas = new ArrayList<>();

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                List<Attribute> attrs = readAttributes(reader);

                relationSchemas.add(new RelationSchema(name, attrs));
            }
            reader.endObject();

            mergeRelationSchemas(relationSchemas);
        }
    }

    private List<Attribute> readAttributes(JsonReader reader) throws IOException {
        List<Attribute> attrs = new ArrayList<>();
        reader.beginObject();
        while (reader.hasNext()) {
            attrs.add(new Attribute(reader.nextName(), reader.nextString()));
        }

        return attrs;
    }

    void extractRelationSchemas(Program program) {
        List<RelationSchema> relationSchemas = program.getStatements()
                .stream()
                .flatMap(stmt -> extractRelationSchemas(stmt).stream())
                .collect(Collectors.toCollection(ArrayList::new));

        mergeRelationSchemas(relationSchemas);
    }

    List<RelationSchema> getRelationSchemas() {
        return this.relationSchemas;
    }

    private void mergeRelationSchemas(List<RelationSchema> relationSchemas) {
        this.relationSchemas.addAll(relationSchemas);
        Map<String, RelationSchema> relationSchemaMap = relationSchemas
                .stream()
                .collect(
                        Collectors.toMap(
                                RelationSchema::getName,
                                Function.identity(),
                                this::mergeRelationSchemas
                        )
                );

        this.relationSchemas = new ArrayList<>(relationSchemaMap.values());
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

    @SuppressWarnings("unchecked")
    private List<RelationSchema> extractRelationSchemas(Statement statement) {
        return (List<RelationSchema>) new PatternMatching(
                inCaseOf(ConjunctiveQuery.class, this::extractRelationSchemas)
        ).matchFor(statement);
    }

    private List<RelationSchema> extractRelationSchemas(ConjunctiveQuery cq) {
        List<RelationSchema> relationSchemas = extractRelationSchemas(cq.getBody());
        relationSchemas.add(extractRelationSchema(cq.getHead()));
        return relationSchemas;
    }

    private List<RelationSchema> extractRelationSchemas(ConjunctiveQueryBody body) {
        return body.getBodyAtoms()
                .stream()
                .map(atom -> extractRelationSchema(atom, body))
                .collect(Collectors.toList());
    }

    private RelationSchema extractRelationSchema(Atom atom, ConjunctiveQueryBody body) {
        return (RelationSchema) new PatternMatching(
                inCaseOf(DBAtom.class, this::extractRelationSchema),
                inCaseOf(IEFunction.class, ieFunction -> extractRelationSchema(ieFunction, body))
        ).matchFor(atom);
    }

    private RelationSchema extractRelationSchema(IEFunction ieFunction, ConjunctiveQueryBody body) {
        return new IEFunctionSchema(ieFunction.getSchemaName(), extractAttributes(ieFunction.getTerms()),
                ieFunction.getInputTerm(), body, ieFunction);
    }

    private RelationSchema extractRelationSchema(ConjunctiveQueryHead head) {
        return extractRelationSchema(head.getHeadAtom());
    }


    private RelationSchema extractRelationSchema(DBAtom atom) {
        return new RelationSchema(atom.getSchemaName(), extractAttributes(atom.getTerms()));
    }

    private List<Attribute> extractAttributes(List<Term> terms) {
        DefaultValuesGenerator valGenerator = new DefaultValuesGenerator();

        if (terms.isEmpty())
            return Stream.of(valGenerator.generate(new Attribute(null, "boolean"))).collect(Collectors.toList());

        return terms
                .stream()
                .map(this::extractAttribute)
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

    private Attribute extractAttribute(Term term) {
        return (Attribute) new PatternMatching(
                inCaseOf(PlaceHolderTerm.class, t -> new Attribute()),
                inCaseOf(ExprTerm.class, this::extractAttribute)
        ).matchFor(term);
    }

    private Attribute extractAttribute(ExprTerm exprTerm) {
        return (Attribute) new PatternMatching(
                inCaseOf(ConstExprTerm.class, this::extractAttribute),
                inCaseOf(VarTerm.class, t -> new Attribute())
        ).matchFor(exprTerm);
    }

    private Attribute extractAttribute(ConstExprTerm constExprTerm) {
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
