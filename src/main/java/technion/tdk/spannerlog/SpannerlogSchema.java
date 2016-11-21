package technion.tdk.spannerlog;


import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.ObjectUtils;
import technion.tdk.spannerlog.utils.graph.DiGraph;
import technion.tdk.spannerlog.utils.match.PatternMatching;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static technion.tdk.spannerlog.utils.match.ClassPattern.inCaseOf;
import static technion.tdk.spannerlog.utils.match.OtherwisePattern.otherwise;


class SpannerlogSchema {

    static class Builder {
        private SpannerlogSchema spannerlogSchema = new SpannerlogSchema();
        private Program program;

        Builder readSchemaFromJson(Reader reader, RelationSchemaBuilder builder) throws IOException {
            spannerlogSchema.readSchemaFromJson(reader, builder);
            return this;
        }

        Builder extractRelationSchemas(Program program) {
            spannerlogSchema.extractRelationSchemas(program);
            this.program = program;
            return this;
        }

        SpannerlogSchema build() {
            spannerlogSchema.validate();
            spannerlogSchema.inferAttributeTypes(program);
            spannerlogSchema.setVariablesType(program);
            spannerlogSchema.setPredictionVariables(program);
//            spannerlogSchema.buildExecutionPlan(program);
            return spannerlogSchema;
        }
    }

    private SpannerlogSchema() {
    }

    private List<RelationSchema> relationSchemas = new ArrayList<>();

    static Builder builder() {
        return new Builder();
    }

    private void readSchemaFromJson(Reader reader, RelationSchemaBuilder builder) throws IOException {
        try (JsonReader jsonReader = new JsonReader(reader)) {
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

        /*
        * Possible cases:
        *   Intensional, Intensional
        *   Intensional, Ambiguous
        *   Extensional, Ambiguous
        *   Ambiguous, Ambiguous
        *   IEF, IEF
        */

        if ( (!oldSchema.getClass().equals(newSchema.getClass()) && !(oldSchema instanceof AmbiguousRelationSchema) && !(newSchema instanceof AmbiguousRelationSchema)) // Checking if not comparing extensional schema with an intensional one.
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

        hotSchema.setAttrs(mergeAttributes(hotSchema.getAttrs(), coldSchema.getAttrs(), hotSchema.getName()));
        hotSchema.getAttrs().forEach(attr -> attr.setSchema(hotSchema));
        for (Atom atom : coldSchema.getAtoms())
            atom.setSchema(hotSchema);

        if (hotSchema instanceof IEFunctionSchema) { // According to a previous check, if one schema is an IE Function schema, then the other one is as well.
            IEFunctionSchema coldIESchema = (IEFunctionSchema) coldSchema;
            IEFunctionSchema hotIESchema = (IEFunctionSchema) hotSchema;

            hotIESchema.setInputTerm(ObjectUtils.firstNonNull(hotIESchema.getInputTerm(), coldIESchema.getInputTerm()));
            hotIESchema.setInputAtoms(ObjectUtils.firstNonNull(hotIESchema.getInputAtoms(), coldIESchema.getInputAtoms()));

            hotIESchema.setMaterialized(hotIESchema.isMaterialized() || coldIESchema.isMaterialized());
        } else if (hotSchema instanceof IntensionalRelationSchema && coldSchema instanceof IntensionalRelationSchema) {
            IntensionalRelationSchema coldISchema = (IntensionalRelationSchema) coldSchema;
            IntensionalRelationSchema hotISchema = (IntensionalRelationSchema) hotSchema;

            hotISchema.getInputSchemas().addAll(coldISchema.getInputSchemas());
        }

        return hotSchema;
    }

    private List<Attribute> mergeAttributes(List<Attribute> oldAttrs, List<Attribute> newAttrs, String schemaName) {
        if (oldAttrs.size() != newAttrs.size())
            throw new NumberOfAttributesInSchemaConflictException(schemaName);

        List<Attribute> mergedAttrs = new ArrayList<>();

        ListIterator<Attribute> it1 = oldAttrs.listIterator();
        ListIterator<Attribute> it2 = newAttrs.listIterator();

        while (it1.hasNext()) {
            mergedAttrs.add(mergeAttributes(it1.next(), it2.next()));
        }

        return mergedAttrs;
    }

    private Attribute mergeAttributes(Attribute oldAttr, Attribute newAttr) {
        String oldName = oldAttr.getName();
        String newName = newAttr.getName();

        if (oldName != null && newName != null && !oldName.equals(newName)) {
            throw new AttributeNameConflictException();
        }

        String oldType = oldAttr.getType();
        String newType = newAttr.getType();

        if (oldType != null && newType != null && !oldType.equals(newType)) {
            throw new AttributeTypeConflictException(oldAttr, newAttr);
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
                .collect(Collectors.toList());

        mergeRelationSchemas(relationSchemas);
    }

    @SuppressWarnings("unchecked")
    private List<RelationSchema> extractRelationSchemas(Statement statement) {
        return (List<RelationSchema>) new PatternMatching(
                inCaseOf(ExtractionRule.class, this::extractRelationSchemas),
                inCaseOf(InferenceRule.class, this::extractRelationSchemas),
                otherwise(stmt -> new ArrayList<>())
        ).matchFor(statement);
    }

    private List<RelationSchema> extractRelationSchemas(ExtractionRule rule) {
        ConjunctiveQueryBody body = rule.getBody();
        List<RelationSchema> relationSchemas = extractRelationSchemas(body);
        IntensionalRelationSchema iSchema = extractIntensionalRelationSchema(rule.getHead());
        iSchema.getInputSchemas().addAll(body.getBodyElements()
                .stream()
                .filter(be -> be instanceof Atom)
                .map(be -> ((Atom) be).getSchemaName())
                .collect(Collectors.toList()));
        relationSchemas.add(iSchema);
        return relationSchemas;
    }

    private List<RelationSchema> extractRelationSchemas(InferenceRule rule) {
        List<RelationSchema> relationSchemas = extractRelationSchemas(rule.getBody());
        relationSchemas.addAll(rule.getHead().getHeadAtoms()
                .stream()
                .map(this::extractRelationSchema)
                .collect(Collectors.toList())
            );
        return relationSchemas;
    }

    private List<RelationSchema> extractRelationSchemas(ConjunctiveQueryBody body) {
        return body.getBodyElements()
                .stream()
                .filter(bodyElement -> bodyElement instanceof Atom)
                .map(bodyElement -> extractRelationSchema(bodyElement, body))
                .collect(Collectors.toList());
    }

    private RelationSchema extractRelationSchema(BodyElement bodyElement, ConjunctiveQueryBody body) {
        return (RelationSchema) new PatternMatching(
                inCaseOf(Atom.class, atom -> extractRelationSchema(atom, body))
        ).matchFor(bodyElement);
    }

    private RelationSchema extractRelationSchema(Atom atom, ConjunctiveQueryBody body) {
        return (RelationSchema) new PatternMatching(
                inCaseOf(DBAtom.class, this::extractRelationSchema),
                inCaseOf(Regex.class, a -> extractRegexSchema(a, body)),
                inCaseOf(IEAtom.class, a -> extractIEFunctionSchema(a, body))
        ).matchFor(atom);
    }

    private RelationSchema extractRegexSchema(Regex regex, ConjunctiveQueryBody body) {
        return new IEFunctionSchema(regex, body, extractAttributes(regex.getTerms()), regex.isMaterialized());
    }

    private RelationSchema extractIEFunctionSchema(IEAtom ieAtom, ConjunctiveQueryBody body) {
        return new IEFunctionSchema(ieAtom, body, extractAttributes(ieAtom.getTerms()), ieAtom.isMaterialized());
    }

    private RelationSchema extractRelationSchema(DBAtom dbAtom) {
        // Schema could be intensional or extensional. TBD at a later stage.
        return new AmbiguousRelationSchema(dbAtom, extractAttributes(dbAtom.getTerms()));
    }

    private IntensionalRelationSchema extractIntensionalRelationSchema(DBAtom dbAtom) {
        return new IntensionalRelationSchema(dbAtom,
                extractAttributesAndGenColumnNames(dbAtom.getTerms()));
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
                inCaseOf(VarTerm.class, t -> new Attribute()),
                inCaseOf(FuncExpr.class, fe -> {
                    if (!fe.isAggregation())
                        throw new functionIsNotAggregationFunctionException(fe.getFunction());
                    return new Attribute(null, "int");
                })
        ).matchFor(exprTerm);
    }

    private Attribute extractAttribute(ConstExprTerm constExprTerm) {
        return (Attribute) new PatternMatching(
                inCaseOf(BooleanConstExpr.class, t -> new Attribute(null, "boolean")),
                inCaseOf(IntConstExpr.class, t -> new Attribute(null, "int")),
                inCaseOf(FloatConstExpr.class, t -> new Attribute(null, "float8")),
                inCaseOf(SpanConstExpr.class, t -> new Attribute(null, "span")),
                inCaseOf(StringConstExpr.class, t -> new Attribute(null, "text"))
        ).matchFor(constExprTerm);
    }

    private void inferAttributeTypes(Program program) {

        Map<Attribute, Set<Attribute>> dependenciesMap = createDependenciesMap(program);
        DiGraph<Attribute> depGraph = new DiGraph<>(dependenciesMap);
        Set<Attribute> rootAttrs = dependenciesMap.keySet()
                .stream()
                .filter(attr -> attr.getType() == null)
                .collect(Collectors.toSet());

        for (Attribute rootAttr : rootAttrs) {
            List<String> possibleTypes = depGraph.getReachables(rootAttr)
                    .stream()
                    .filter(attr -> attr.getType() != null)
                    .map(Attribute::getType)
                    .distinct()
                    .collect(Collectors.toList());

            if (possibleTypes.size() != 1) {
                throw new AttributeTypeCannotBeInferredException(rootAttr);
            }

            rootAttr.setType(possibleTypes.get(0));
        }
    }

    private Map<Attribute, Set<Attribute>> createDependenciesMap(Program program) {
        List<RuleWithConjunctiveQuery> rules = program.getStatements()
                .stream()
                .filter(stmt -> stmt instanceof ExtractionRule)
                .map(stmt -> (RuleWithConjunctiveQuery) stmt)
                .collect(Collectors.toList());

        Map<Attribute, Set<Attribute>> dependenciesMap = new HashMap<>();

        for (RuleWithConjunctiveQuery rule : rules) {
            DBAtom headAtom = ((ExtractionRule) rule).getHead(); // TODO handle the InferenceRule case
            List<Atom> bodyAtoms = rule.getBody().getBodyElements()
                    .stream()
                    .filter(bodyElement -> bodyElement instanceof Atom)
                    .map(bodyElement -> (Atom) bodyElement)
                    .collect(Collectors.toList());

            RelationSchema headAtomSchema = headAtom.getSchema();

            ListIterator<Term> it1 = headAtom.getTerms().listIterator();
            while (it1.hasNext()) {
                Term headTerm = it1.next();
                if (!(headTerm instanceof VarTerm))
                    continue;

                Attribute headAttr = headAtomSchema.getAttrs().get(it1.previousIndex());
                if (headAttr.getType() != null)
                    continue;
                List<Attribute> localDependencies = new ArrayList<>();

                String headVarName = ((VarTerm) headTerm).getVarName();

                for (Atom bodyAtom : bodyAtoms) {
                    ListIterator<Term> it2 = bodyAtom.getTerms().listIterator();
                    while (it2.hasNext()) {
                        Term bodyTerm = it2.next();
                        if (!(bodyTerm instanceof VarTerm))
                            continue;
                        String bodyVarName = ((VarTerm) bodyTerm).getVarName();
                        if (headVarName.equals(bodyVarName)) {
                            Attribute bodyAttr = bodyAtom.getSchema().getAttrs().get(it2.previousIndex());
                            localDependencies.add(bodyAttr);
                            if (!dependenciesMap.containsKey(bodyAttr))
                                dependenciesMap.put(bodyAttr, new HashSet<>());
                        }
                    }
                }

                if (localDependencies.isEmpty())
                    throw new UnboundVariableException(headVarName, headAtomSchema.getName());

                localDependencies.remove(headAttr);

                Set<Attribute> dependencies = dependenciesMap.getOrDefault(headAttr, new HashSet<>());
                dependencies.addAll(localDependencies);
                dependenciesMap.put(headAttr, dependencies);
            }
        }

        return dependenciesMap;
    }

    private void setPredictionVariables(Program program) {
        List<String> predVarNames = program.getStatements()
                .stream()
                .filter(stmt -> stmt instanceof PredVarDec)
                .map(stmt -> ((PredVarDec) stmt).getRelationSchemaName())
                .distinct()
                .collect(Collectors.toList());

        this.relationSchemas
                .stream()
                .filter(sch -> sch instanceof IntensionalRelationSchema)
                .map(sch -> (IntensionalRelationSchema) sch)
                .filter(sch -> predVarNames.contains(sch.getName()))
                .forEach(sch -> sch.setPredictionVariableSchema(true));
    }

    private void validate() {
        List<String> AmbiguousSchemaNames = relationSchemas
                .stream()
                .filter(schema -> schema instanceof AmbiguousRelationSchema)
                .map(RelationSchema::getName)
                .collect(Collectors.toList());

        List<String> UnambiguousSchemaNames = relationSchemas
                .stream()
                .filter(schema -> !(schema instanceof AmbiguousRelationSchema))
                .map(RelationSchema::getName)
                .collect(Collectors.toList());

        AmbiguousSchemaNames.removeAll(UnambiguousSchemaNames);

        if (!AmbiguousSchemaNames.isEmpty())
            throw new UndefinedRelationSchema(AmbiguousSchemaNames.get(0));
    }

    private void setVariablesType(Program program) {
        List<RuleWithConjunctiveQuery> rules = program.getStatements()
                .stream()
                .filter(stmt -> stmt instanceof RuleWithConjunctiveQuery)
                .map(stmt -> (RuleWithConjunctiveQuery) stmt)
                .collect(Collectors.toList());

        for (RuleWithConjunctiveQuery rule : rules) {
            List<Atom> atoms = rule.getBody().getBodyElements()
                    .stream()
                    .filter(bodyElement -> bodyElement instanceof Atom)
                    .map(bodyElement -> (Atom) bodyElement)
                    .collect(Collectors.toList());

            if (rule instanceof InferenceRule) {
                atoms.addAll(((InferenceRule) rule).getHead().getHeadAtoms());
            } else {
                atoms.add(((ExtractionRule) rule).getHead());
            }

            for (Atom atom : atoms) {
                List<Attribute> atomAttrs = atom.getSchema().getAttrs();
                ListIterator<Term> it = atom.getTerms().listIterator();
                while (it.hasNext()) {
                    Term term = it.next();
                    if (!(term instanceof VarTerm))
                        continue;

                    ((VarTerm) term).setType(atomAttrs.get(it.previousIndex()).getType());
                }
            }

            // set types in conditions
            List<Condition> conditions = rule.getBody().getBodyElements()
                    .stream()
                    .filter(bodyElement -> bodyElement instanceof Condition)
                    .map(bodyElement -> (Condition) bodyElement)
                    .collect(Collectors.toList());

            if (!conditions.isEmpty()) {
                List<VarTerm> atomsVars = atoms
                        .stream()
                        .flatMap(atom -> atom.getTerms().stream())
                        .filter(term -> term instanceof VarTerm)
                        .map(term -> (VarTerm) term)
                        .collect(Collectors.toList());

                for (Condition c : conditions) {
                    List<VarTerm> condVars = getVarsInCondition(c);
                    for (VarTerm condVar : condVars) {
                        List<String> possibleTypes = atomsVars
                                .stream()
                                .filter(atomVar -> atomVar.getVarName().equals(condVar.getVarName()))
                                .map(VarTerm::getType)
                                .distinct()
                                .collect(Collectors.toList());

                        if (possibleTypes.size() != 1) {
                            throw new VariableTypeCannotBeInferredException(condVar);
                        }
                        condVar.setType(possibleTypes.get(0));
                    }

                    // validateCondition(c); // TODO check binary condition is legal
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<VarTerm> getVarsInCondition(Condition c) {
        return (List<VarTerm>) new PatternMatching(
                inCaseOf(NegationCondition.class, nc -> getVarsInCondition(nc.getCond())),
                inCaseOf(ExprCondition.class, ec -> getVarsInCondition(ec.getExpr()))
        ).matchFor(c);
    }

    @SuppressWarnings("unchecked")
    private List<VarTerm> getVarsInCondition(ExprTerm e) {
        return (List<VarTerm>) new PatternMatching(
                inCaseOf(VarTerm.class, varTerm -> {
                    List<VarTerm> vars = Stream.of(varTerm).collect(Collectors.toList());
                    vars.addAll(varTerm.getSpans().stream().filter(s -> s instanceof VarTerm).map(s -> (VarTerm) s).collect(Collectors.toList()));
                    return vars;
                }),
                inCaseOf(DotFuncExpr.class, fe -> {
                    List<VarTerm> vars = fe.getArgs().stream().flatMap(arg -> getVarsInCondition(arg).stream()).collect(Collectors.toList());
                    vars.add(fe.getVarTerm());
                    return vars;
                }),
                inCaseOf(BinaryOpExpr.class, boe -> Stream.of(boe.getLhs(), boe.getRhs()).flatMap(expr -> getVarsInCondition(expr).stream()).collect(Collectors.toList())),
                otherwise(expr -> new ArrayList<>())
        ).matchFor(e);
    }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationSchema that = (RelationSchema) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
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

class AmbiguousRelationSchema extends RelationSchema {
    AmbiguousRelationSchema(DBAtom dbAtom, List<Attribute> attrs) {
        super(dbAtom.getSchemaName(), attrs);
        getAttrs().forEach(attr -> attr.setSchema(this));
        dbAtom.setSchema(this);
        getAtoms().add(dbAtom);
    }
}

class ExtensionalRelationSchema extends RelationSchema {
    ExtensionalRelationSchema(String name, List<Attribute> attrs) {
        super(name, attrs);
    }
}

class IntensionalRelationSchema extends RelationSchema {
    private boolean isPredictionVariableSchema;
    private Set<String> inputSchemas = new HashSet<>();

    IntensionalRelationSchema(DBAtom dbAtom, List<Attribute> attrs) {
        super(dbAtom.getSchemaName(), attrs);
        getAttrs().forEach(attr -> attr.setSchema(this));
        dbAtom.setSchema(this);
        getAtoms().add(dbAtom);
    }

    boolean isPredictionVariableSchema() {
        return isPredictionVariableSchema;
    }

    void setPredictionVariableSchema(boolean predictionVariableSchema) {
        isPredictionVariableSchema = predictionVariableSchema;
    }

    Set<String> getInputSchemas() {
        return inputSchemas;
    }
}

class IEFunctionSchema extends RelationSchema {
    private List<Atom> inputAtoms;
    private Term inputTerm;
    private boolean isMaterialized;

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

    IEFunctionSchema(String name, List<Attribute> attrs, boolean isMaterialized) {
        super(name, attrs);
        this.isMaterialized = isMaterialized;
    }

    IEFunctionSchema(Regex regex, ConjunctiveQueryBody body, List<Attribute> attrs, boolean isMaterialized) {
        this((IEAtom) regex, body, attrs, isMaterialized);

        attrs.get(0).setName("input");
        attrs.get(0).setType("text");
        int n = getAttrs().size();
        for (int i = 1; i < n; i++) { // skipping the input term (always the first term)
            Attribute attr = getAttrs().get(i);
            attr.setType("span");
            attr.setName(((VarTerm) regex.getTerms().get(i)).getVarName());
        }
    }

    IEFunctionSchema(IEAtom ieAtom, ConjunctiveQueryBody body, List<Attribute> attrs, boolean isMaterialized) {
        super(ieAtom.getSchemaName(), attrs);
        getAttrs().forEach(attr -> attr.setSchema(this));
        inputTerm = ieAtom.getInputTerm();
        validateInputTerm(ieAtom, body);
        determineInputAtoms(body, ieAtom);
        ieAtom.setSchema(this);
        getAtoms().add(ieAtom);
        this.isMaterialized = isMaterialized;
    }

    public boolean isMaterialized() {
        return isMaterialized;
    }

    public void setMaterialized(boolean materialized) {
        isMaterialized = materialized;
    }

    private void validateInputTerm(IEAtom ieAtom, ConjunctiveQueryBody body) {
        if (!(inputTerm instanceof VarTerm))
            return;

        List<SpanTerm> spans = ((VarTerm) inputTerm).getSpans();

        List<String> bodyVarNames = body.getBodyElements()
                .stream()
                .filter(bodyElement -> bodyElement instanceof Atom)
                .map(bodyElement -> (Atom) bodyElement)
                .filter(atom -> atom != ieAtom)
                .flatMap(atom -> atom.getTerms().stream())
                .filter(term -> term instanceof VarTerm)
                .map(term -> ((VarTerm) term).getVarName())
                .collect(Collectors.toList());

        List<String> spanVarNames = spans
                .stream()
                .filter(spanTerm -> spanTerm instanceof VarTerm)
                .map(spanTerm -> ((VarTerm) spanTerm).getVarName())
                .collect(Collectors.toList());

        spanVarNames.removeAll(bodyVarNames);

        if (!spanVarNames.isEmpty())
            throw new UnboundVariableException(spanVarNames.get(0), getName());
    }

    private void determineInputAtoms(ConjunctiveQueryBody cqBody, IEAtom ieAtom) {
        List<Atom> bodyAtoms = cqBody.getBodyElements()
                .stream()
                .filter(bodyElement -> bodyElement instanceof Atom)
                .map(bodyElement -> (Atom) bodyElement)
                .collect(Collectors.toList());

        DiGraph<Integer> depGraph = createDependenciesGraph(bodyAtoms);
        List<Integer> dependencies = depGraph.getReachables(bodyAtoms.indexOf(ieAtom));
        inputAtoms = dependencies
                .stream()
                .map(bodyAtoms::get)
                .collect(Collectors.toList());
    }

    private DiGraph<Integer> createDependenciesGraph(List<Atom> bodyAtoms) {
        Map<Integer, Set<Integer>> adjacencyMap =
                IntStream.range(0, bodyAtoms.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> i, i -> calcDependencies(i, bodyAtoms)));

        return new DiGraph<>(adjacencyMap);
    }

    private Set<Integer> calcDependencies(int idx, List<Atom> bodyAtoms) {
        Set<Integer> dependencies = new HashSet<>();

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

        terms.remove(inputTerm);

        List<String> varNames2 = terms
                .stream()
                .flatMap(term -> getVars(term).stream())
                .collect(Collectors.toList());

        terms.add(0, inputTerm);

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
                otherwise(t -> new ArrayList<>())
        ).matchFor(exprTerm);

        if (exprTerm instanceof StringTerm) {
            List<SpanTerm> spans = ((StringTerm) exprTerm).getSpans();
            vars.addAll(spans
                    .stream()
                    .flatMap(s -> getVars(s).stream())
                    .collect(Collectors.toList())
            );
        }
        return vars;
    }

    @SuppressWarnings("unchecked")
    private List<String> getVars(SpanTerm spanTerm) {
        return (List<String>) new PatternMatching(
                inCaseOf(VarTerm.class, varTerm -> Stream.of(varTerm.getVarName()).collect(Collectors.toList())),
                otherwise(t -> new ArrayList<>())
        ).matchFor(spanTerm);
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
            case IEFUNCTION:
                relationSchema = new IEFunctionSchema(name, attrs, false);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return relationSchema;
    }

    private class SchemaBuilderHasNoTypeException extends RuntimeException {
    }
}

enum RelationSchemaType {
    EXTENSIONAL, IEFUNCTION
}

class Attribute {
    private String name;
    private String type;
    private RelationSchema schema;

    Attribute() {
    }

    Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RelationSchema getSchema() {
        return schema;
    }

    public void setSchema(RelationSchema schema) {
        this.schema = schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        return name.equals(attribute.name) && schema.equals(attribute.schema);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + schema.hashCode();
        return result;
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
    AttributeTypeConflictException(Attribute attr1, Attribute attr2) {
        super("The attribute '" + ObjectUtils.firstNonNull(attr1.getName(), attr2.getName()) + "' in '"
                + ObjectUtils.firstNonNull(attr1.getSchema(), attr2.getSchema()).getName()
                + "' have conflicting types: " + attr1.getType() + ", " + attr2.getType());
    }
}

class AttributeNameConflictException extends RuntimeException {
}

class NumberOfAttributesInSchemaConflictException extends RuntimeException {
    NumberOfAttributesInSchemaConflictException(String schemaName) {
        super("The number of attributes in schema '" + schemaName + "' is inconsistent throughout the program");
    }
}

class RelationSchemaNameConflictException extends RuntimeException {
}

class AttributeTypeCannotBeInferredException extends RuntimeException {
    AttributeTypeCannotBeInferredException(Attribute attr) {
        super("The type of attribute '" + attr.getName() + "' in '" + attr.getSchema().getName() + "' cannot be inferred");
    }
}

class VariableTypeCannotBeInferredException extends RuntimeException {
    VariableTypeCannotBeInferredException(VarTerm varTerm) {
        super("The type of the variable '" + varTerm.getVarName() + "' cannot be inferred");
    }
}

class UnboundVariableException extends RuntimeException {
    UnboundVariableException(String varName, String schemaName) {
        super("The variable '" + varName + "' in '" + schemaName + "' is unbound");
    }
}

class UndefinedRelationSchema extends RuntimeException {
    UndefinedRelationSchema(String message) {
        super("The relation schema for '" + message + "' is undefined");
    }
}

class functionIsNotAggregationFunctionException extends RuntimeException {
    functionIsNotAggregationFunctionException(String funcName) {
        super("The function '" + funcName + "' must be a known aggregation function");
    }
}
