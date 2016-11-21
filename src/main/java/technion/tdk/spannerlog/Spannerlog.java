package technion.tdk.spannerlog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.cli.*;
import technion.tdk.spannerlog.utils.graph.DiGraph;
import technion.tdk.spannerlog.utils.match.PatternMatching;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.exit;
import static technion.tdk.spannerlog.utils.match.ClassPattern.inCaseOf;

public class Spannerlog {

    JsonObject init(InputStream programInputStream, Reader edbReader, Reader udfReader) throws IOException {
        // parse program
        Program program = new SpannerlogInputParser().parseProgram(programInputStream);

        // desugar
        new SpannerlogDesugarRewriter().derive(program);

        // build schema
        SpannerlogSchema.Builder builder = SpannerlogSchema.builder();
        if (edbReader != null)
            builder.readSchemaFromJson(edbReader, RelationSchema.builder().type(RelationSchemaType.EXTENSIONAL));
        if (udfReader != null)
            builder.readSchemaFromJson(udfReader, RelationSchema.builder().type(RelationSchemaType.IEFUNCTION));
        SpannerlogSchema schema = builder
                .extractRelationSchemas(program)
                .build();

        // compile
        SpannerlogCompiler compiler = new SpannerlogCompiler();
        Map<String, String> iefDeclarationsBlocks = compiler.compile(schema);
        List<Map<String, String>> cqBlocks = compiler.compile(program);

        // build execution plan
        List<String> plan = BuildExecutionPlan(schema);

        return export(schema, iefDeclarationsBlocks, cqBlocks, plan);
    }

    private List<String> BuildExecutionPlan(SpannerlogSchema schema) {

        List<String> iSchemasNames = schema.getRelationSchemas()
                .stream()
                .filter(sch -> sch instanceof IntensionalRelationSchema)
                .map(RelationSchema::getName)
                .collect(Collectors.toList());

        DiGraph.Builder<String> builder = new DiGraph.Builder<>();

        schema.getRelationSchemas()
                .stream()
                .filter(sch -> sch instanceof IntensionalRelationSchema)
                .map(sch -> (IntensionalRelationSchema) sch)
                .forEach(sch -> {
                    builder.addNode(sch.getName());
                    sch.getInputSchemas()
                            .stream()
                            .filter(iSchemasNames::contains)
                            .forEach(iSch -> {if (!iSch.equals(sch.getName())) builder.addEdge(iSch, sch.getName());});
                });

        return builder.build().sortTopologically();
    }

    private JsonObject export(SpannerlogSchema schema, Map<String, String> iefDeclarationsBlocks,
                              List<Map<String, String>> cqBlocks, List<String> plan) {

        JsonObject jsonTree = new JsonObject();
        Gson gson = new GsonBuilder().serializeNulls().create();

        List<JsonObject> edbSchemas = new ArrayList<>();
        List<JsonObject> iefSchemas = new ArrayList<>();
        List<JsonObject> idbSchemas = new ArrayList<>();

        PatternMatching pattern = new PatternMatching(
                inCaseOf(ExtensionalRelationSchema.class, s -> edbSchemas.add(toJson(s, gson))),
                inCaseOf(IEFunctionSchema.class, s -> iefSchemas.add(toJson(s, gson))),
                inCaseOf(IntensionalRelationSchema.class, s -> idbSchemas.add(toJson(s, gson)))
        );

        schema.getRelationSchemas().forEach(pattern::matchFor);

        JsonObject schemaObject = new JsonObject();
        schemaObject.add("edb", gson.toJsonTree(edbSchemas));
        schemaObject.add("ief", gson.toJsonTree(iefSchemas));
        schemaObject.add("idb", gson.toJsonTree(idbSchemas));

        jsonTree.add("schema", schemaObject);

        jsonTree.add("ie_functions", gson.toJsonTree(schema.getRelationSchemas()
                .stream()
                .filter(s -> s instanceof IEFunctionSchema)
                .sorted((s1, s2) -> String.CASE_INSENSITIVE_ORDER.compare(s1.getName(), s2.getName()))
                .map(s -> toJson((IEFunctionSchema) s, iefDeclarationsBlocks))
                .collect(Collectors.toList()))
        );

        jsonTree.add("rules", gson.toJsonTree(cqBlocks));

        jsonTree.add("execution", gson.toJsonTree(plan));

        return jsonTree;
    }

    private JsonObject toJson(IEFunctionSchema schema, Map<String, String> iefDeclarationsBlocks) {
        JsonObject schemaJsonObject = new JsonObject();

        String name = schema.getName();
        schemaJsonObject.addProperty("name", name);
        schemaJsonObject.addProperty("statement", iefDeclarationsBlocks.get(name));

        List<Atom> atoms = schema.getAtoms();
        if (atoms.size() == 1 && atoms.get(0) instanceof Regex) {
            schemaJsonObject.addProperty("regex", ((Regex) atoms.get(0)).getCompiledRegexString());
        }

        if (schema.isMaterialized())
            schemaJsonObject.addProperty("materialized", true);

        return schemaJsonObject;
    }

    private JsonObject toJson(RelationSchema schema, Gson gson) {
        JsonObject schemaJsonObject = new JsonObject();
        schemaJsonObject.addProperty("name", schema.getName());

        if (schema instanceof IntensionalRelationSchema) {
            IntensionalRelationSchema iSchema = (IntensionalRelationSchema) schema;

            schemaJsonObject.add("input_schemas", gson.toJsonTree(iSchema.getInputSchemas()));

            if (iSchema.isPredictionVariableSchema())
                schemaJsonObject.addProperty("variable_type", "boolean"); // TODO support categorical variables
        }

        JsonObject attributesJsonObject = new JsonObject();
        schema.getAttrs().forEach(attr -> attributesJsonObject.addProperty(attr.getName(), attr.getType()));

        schemaJsonObject.add("attributes", attributesJsonObject);

        return schemaJsonObject;
    }

    public static void main(String[] args) {
        Options options = setCommandLineOptions();

        try {
            CommandLine line = new DefaultParser().parse(options, args);

            InputStream programInputStream = new FileInputStream(line.getOptionValue("program"));
            Reader edbReader = line.hasOption("edb") ? new FileReader(line.getOptionValue("edb")) : null;
            Reader udfReader = line.hasOption("ief") ? new FileReader(line.getOptionValue("ief")) : null;

            JsonObject jsonTree = new Spannerlog().init(programInputStream, edbReader, udfReader);

            // print json tree to stdout
            if (!line.hasOption("s")) {
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .disableHtmlEscaping()
                        .serializeNulls()
                        .create();

                System.out.println(gson.toJson(jsonTree));
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getSimpleName());
            System.err.println(e.getMessage());
            exit(1);
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setOptionComparator(null);
        System.setOut(System.err);
        formatter.printHelp("splog", options, true);
    }

    private static Options setCommandLineOptions() {
        Options options = new Options();

        options.addOption(Option
                .builder("program")
                .required(true)
                .hasArg()
                .numberOfArgs(1)
                .desc("the program to compile")
                .build());

        options.addOption(Option
                .builder("edb")
                .hasArg()
                .numberOfArgs(1)
                .desc("the EDB schema of the program (required if EDB is not empty)")
                .build());

        options.addOption(Option
                .builder("ief")
                .hasArg()
                .numberOfArgs(1)
                .desc("the ief schemas (required if IEFs are used)")
                .build());

        options.addOption(Option
                .builder("s")
                .longOpt("skip-print")
                .desc("Whether to skip printing the compiled json object to stdout")
                .build());

        return options;
    }
}
