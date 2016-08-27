package technion.tdk.spannerlog;


import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.ObjectUtils;
import technion.tdk.spannerlog.utils.DependenciesGraph;
import technion.tdk.spannerlog.utils.match.PatternMatching;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static technion.tdk.spannerlog.utils.match.ClassPattern.inCaseOf;
import static technion.tdk.spannerlog.utils.match.OtherwisePattern.otherwise;


class SpannerlogSchema {

    static class Builder {
        private SpannerlogSchema spannerlogSchema;

        Builder() {
            this.spannerlogSchema = new SpannerlogSchema();
        }

        Builder readSchemaFromJson(Reader reader, RelationSchemaBuilder builder) throws IOException {
            spannerlogSchema.readSchemaFromJson(reader, builder);
            return this;
        }

        Builder extractRelationSchemas(Program program) {
            spannerlogSchema.extractRelationSchemas(program);
            return this;
        }

        SpannerlogSchema build() {
            spannerlogSchema.inferAttributeTypes();
            return spannerlogSchema;
        }
    }

    private SpannerlogSchema() {}

    private List<RelationSchema> relationSchemas = new ArrayList<>();

    static Builder builder() {
        return new Builder();
    }

    private void inferAttributeTypes() {
        relationSchemas
                .stream()
                .filter(s -> s instanceof IntensionalRelationSchema)
                .map(s -> (IntensionalRelationSchema) s)
                .forEach(IntensionalRelationSchema::inferAttributeTypes);
//                .collect(Collectors.toList());
//        System.out.println(iSchemas);
    }

    private void readSchemaFromJson(Reader reader, RelationSchemaBuilder builder) throws IOException {
        try (JsonReader jsonReader = new JsonReader(reader)){
            List<RelationSchema> relationSchemas = new ArrayList<>();

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                List<Attribute> attrs = readAttributes(jsonReader);

                relationSchemas.add(builder.build(name, attrs));
            }
            jsonReader.endObject();

            mergeRelationSchemas(relationSchemas);
        }
    }

    private List<Attribute> readAttributes(JsonReader jsonReader) throws IOException {
        List<Attribute> attrs = new ArrayList<>();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            attrs.add(new Attribute(jsonReader.nextName(), jsonReader.nextString()));
        }
        jsonReader.endObject();
        return attrs;
    }

    private void mergeRelationSchemas(List<RelationSchema> relationSchemas) {
        this.relationSchemas.addAll(relationSchemas);
        Map<String, RelationSchema> relationSchemaMap = this.relationSchemas
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

    @SuppressWarnings("unchecked")
    private RelationSchema mergeRelationSchemas(RelationSchema oldSchema, RelationSchema newSchema) {
        if (
             (!oldSchema.getClass().equals(newSchema.getClass()) && !(oldSchema instanceof AmbiguousRelationSchema) && !(newSchema instanceof AmbiguousRelationSchema)) // Checking if not comparing extensional schema with an intensional one.
             ||
             (oldSchema instanceof IEFunctionSchema && !(newSchema instanceof IEFunctionSchema)) || (!(oldSchema instanceof IEFunctionSchema) && newSchema instanceof IEFunctionSchema) // Checking that if one schema is of an IE-function, then the other is also.
           ) {
            throw new RelationSchemaNameConflictException();
        }

        RelationSchema hotSchema;
        RelationSchema coldSchema;

        if (oldSchema instanceof AmbiguousRelationSchema) {
            hotSchema = newSchema;
            coldSchema = oldSchema;
        } else {
            hotSchema = oldSchema;
            coldSchema = newSchema;
        }

        hotSchema.setAttrs(mergeAttributes(oldSchema.getAttrs(), newSchema.getAttrs()));
        for (Atom atom : coldSchema.getAtoms())
            atom.setSchema(hotSchema);

        if (hotSchema instanceof IEFunctionSchema) { // According to a previous check, if one schema is of an IE Function, then the other one is as well.
            IEFunctionSchema coldIESchema = (IEFunctionSchema) newSchema;
            IEFunctionSchema hotIESchema = (IEFunctionSchema) hotSchema;

            hotIESchema.setInputTerm(ObjectUtils.firstNonNull(hotIESchema.getInputTerm(), coldIESchema.getInputTerm()));
            hotIESchema.setInputAtoms(ObjectUtils.firstNonNull(hotIESchema.getInputAtoms(), coldIESchema.getInputAtoms()));
        }
        if (hotSchema instanceof IntensionalRelationSchema && coldSchema instanceof IntensionalRelationSchema) {
            ((IntensionalRelationSchema) hotSchema).addInputAtoms(((IntensionalRelationSchema) coldSchema).getInputAtoms());
        }

        return hotSchema;
    }

    private List<Attribute> mergeAttributes(List<Attribute> oldAttrs, List<Attribute> newAttrs) {
        oldAttrs.addAll(newAttrs);

        Map<String, Attribute> attrMap = oldAttrs
                .stream()
                .filter(attr -> attr.getName() != null)
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

        if (oldName != null && newName != null && !oldName.equals(newName)) {
            throw new AttributeNameConflictException();
        }

        String oldType = oldValue.getType();
        String newType = newValue.getType();

        if (oldType != null && newType != null && !oldType.equals(newType)) {
            throw new AttributeTypeConflictException();
        }

        return new Attribute(ObjectUtils.firstNonNull(oldName, newName), ObjectUtils.firstNonNull(oldType, newType));
    }

    List<RelationSchema> getRelationSchemas() {
        return this.relationSchemas;
    }

    private void extractRelationSchemas(Program program) {
        List<RelationSchema> relationSchemas = program.getStatements()
                .stream()
                .flatMap(stmt -> extractRelationSchemas(stmt).stream())
                .collect(Collectors.toCollection(ArrayList::new));

        mergeRelationSchemas(relationSchemas);
    }

    @SuppressWarnings("unchecked")
    private List<RelationSchema> extractRelationSchemas(Statement statement) {
        return (List<RelationSchema>) new PatternMatching(
                inCaseOf(ConjunctiveQuery.class, this::extractRelationSchemas)
        ).matchFor(statement);
    }

    private List<RelationSchema> extractRelationSchemas(ConjunctiveQuery cq) {
        ConjunctiveQueryBody body = cq.getBody();
        List<RelationSchema> relationSchemas = extractRelationSchemas(body);
        relationSchemas.add(extractRelationSchema(cq.getHead(), body));
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
                inCaseOf(IEAtom.class, a -> extractIEFunctionSchema(a, body))
        ).matchFor(atom);
    }

    private RelationSchema extractIEFunctionSchema(IEAtom ieAtom, ConjunctiveQueryBody body) {
        IEFunctionSchema schema = new IEFunctionSchema(ieAtom.getSchemaName(),
                extractAttributes(ieAtom.getTerms()));
        schema.setInputTerm(ieAtom.getInputTerm());
        schema.determineInputAtoms(body, ieAtom);
        ieAtom.setSchema(schema);
        schema.getAtoms().add(ieAtom);
        return schema;
    }

    private RelationSchema extractRelationSchema(DBAtom dbAtom) {
        // Schema could be intensional or extensional. TBD at a later stage.
        RelationSchema schema = new AmbiguousRelationSchema(dbAtom.getSchemaName(), extractAttributes(dbAtom.getTerms()));
        dbAtom.setSchema(schema);
        schema.getAtoms().add(dbAtom);
        return schema;
    }

    private RelationSchema extractIntensionalRelationSchema(DBAtom dbAtom, ConjunctiveQueryBody body) {
        IntensionalRelationSchema schema = new IntensionalRelationSchema(dbAtom.getSchemaName(),
                extractAttributesAndGenColumnNames(dbAtom.getTerms()));
        dbAtom.setSchema(schema);
        schema.getAtoms().add(dbAtom);
        schema.addInputAtoms(body.getBodyAtoms());
        return schema;
    }

    private RelationSchema extractRelationSchema(ConjunctiveQueryHead head, ConjunctiveQueryBody body) {
        return extractIntensionalRelationSchema(head.getHeadAtom(), body);
    }

    private List<Attribute> extractAttributes(List<Term> terms) {
        if (terms.isEmpty())
            return Stream.of(new Attribute(null, "boolean")).collect(Collectors.toList());

        return terms.stream().map(this::extractAttribute).collect(Collectors.toList());
    }

    private List<Attribute> extractAttributesAndGenColumnNames(List<Term> terms) {
        ColumnNamesGenerator generator = new ColumnNamesGenerator();

        // Boolean relations ( e.g., Q() )
        if (terms.isEmpty())
            return Stream.of(generator.generate(new Attribute(null, "boolean"))).collect(Collectors.toList());

        return terms.stream().map(this::extractAttribute).map(generator::generate).collect(Collectors.toList());
    }

    private class ColumnNamesGenerator {
        private int counter = 1;

        Attribute generate(Attribute attr) {
            String name = attr.getName();
            if (name == null) {
                name = "column" + counter;
                counter++;
            }

            return new Attribute(name, attr.getType());
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

    private class AttributeTypeConflictException extends RuntimeException {}

    private class AttributeNameConflictException extends RuntimeException {}

    private class RelationSchemaNameConflictException extends RuntimeException {}
}

abstract class RelationSchema {
    private String name;
    private List<Attribute> attrs;
    private List<Atom> atoms;

    RelationSchema(String name, List<Attribute> attrs) {
        this.name = name;
        this.attrs = attrs;
        this.atoms = new ArrayList<>();
    }

    List<Atom> getAtoms() {
        return atoms;
    }

    public String getName() {
        return name;
    }

    List<Attribute> getAttrs() {
        return attrs;
    }

    void setAttrs(List<Attribute> attrs) {
        this.attrs = attrs;
    }

    @Override
    public String toString() {
        return "RelationSchema{" +
                "name='" + name + '\'' +
                ", attrs=" + attrs +
                '}';
    }

    static RelationSchemaBuilder builder() {
        return new RelationSchemaBuilder();
    }
}

class RelationSchemaBuilder {
    private RelationSchemaType type;

    RelationSchemaBuilder type(RelationSchemaType type) {
        this.type = type;
        return this;
    }

    RelationSchema build(String name, List<Attribute> attrs) {

        if (type == null) {
            throw new SchemaBuilderHasNoTypeException();
        }

        RelationSchema relationSchema;

        switch (type) {
            case EXTENSIONAL:
                relationSchema = new ExtensionalRelationSchema(name, attrs);
                break;
            case INTENSIONAL:
                relationSchema = new IntensionalRelationSchema(name, attrs);
                break;
            case IEFUNCTION:
                relationSchema = new IEFunctionSchema(name, attrs);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return relationSchema;
    }

    private class SchemaBuilderHasNoTypeException extends RuntimeException {}
}

enum RelationSchemaType {
    EXTENSIONAL, INTENSIONAL, IEFUNCTION
}

class AmbiguousRelationSchema extends RelationSchema {
    AmbiguousRelationSchema(String name, List<Attribute> attrs) {
        super(name, attrs);
    }
}

class ExtensionalRelationSchema extends RelationSchema {
    ExtensionalRelationSchema(String name, List<Attribute> attrs) {
        super(name, attrs);
    }
}

class IntensionalRelationSchema extends RelationSchema {
    private List<Atom> inputAtoms;

    List<Atom> getInputAtoms() {
        return inputAtoms;
    }

    void addInputAtoms(List<Atom> bodyAtoms) {
        this.inputAtoms.addAll(bodyAtoms);
    }

    IntensionalRelationSchema(String name, List<Attribute> attrs) {
        super(name, attrs);
        this.inputAtoms = new ArrayList<>();
    }

    void inferAttributeTypes() {
        this.getAttrs()
                .stream()
                .filter(attr -> attr.getType() != null)
                .forEach(this::inferAttributeTypes);
    }

    private void inferAttributeTypes(Attribute attr) {
        DependenciesGraph depGraph = createDependenciesGraph(attr);
    }

    private DependenciesGraph createDependenciesGraph(Attribute attr) {
        return null;
    }
}

class IEFunctionSchema extends ExtensionalRelationSchema {
    private List<Atom> inputAtoms;
    private Term inputTerm;

    Term getInputTerm() {
        return inputTerm;
    }

    List<Atom> getInputAtoms() {
        return inputAtoms;
    }

    void setInputTerm(Term inputTerm) {
        this.inputTerm = inputTerm;
    }

    void setInputAtoms(List<Atom> inputAtoms) {
        this.inputAtoms = inputAtoms;
    }

    IEFunctionSchema(String name, List<Attribute> attrs) {
        super(name, attrs);
    }

    void determineInputAtoms(ConjunctiveQueryBody cqBody, IEAtom ieAtom) {
        List<Atom> bodyAtoms = cqBody.getBodyAtoms();
        DependenciesGraph depGraph = createDependenciesGraph(bodyAtoms);
        List<Integer> dependencies = depGraph.getDependencies(bodyAtoms.indexOf(ieAtom));
        inputAtoms = dependencies
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

class AttributeTypeCannotBeInferredException extends RuntimeException {}
