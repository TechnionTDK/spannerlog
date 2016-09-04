package technion.tdk.spannerlog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Spannerlog {

    void init(InputStream programInputStream, Reader edbReader, Reader udfReader, boolean skipExport) throws IOException {
        // parse program
        Program program = new SpannerlogInputParser().parseProgram(programInputStream);

        // desugar
        new SpannerlogDesugarRewriter().derive(program);

        // build schema
        SpannerlogSchema.Builder builder = SpannerlogSchema
                .builder();
        if (edbReader != null)
            builder.readSchemaFromJson(edbReader, RelationSchema.builder().type(RelationSchemaType.EXTENSIONAL));
        if (udfReader != null)
            builder.readSchemaFromJson(udfReader, RelationSchema.builder().type(RelationSchemaType.IEFUNCTION));
        SpannerlogSchema schema = builder
                .extractRelationSchemas(program)
                .build();

        // compile
        SpannerlogCompiler compiler = new SpannerlogCompiler();
        Map<String, String> iefBlocksMap = compiler.compile(schema);
        List<String> ruleBlocks = compiler.compile(program);

        // export to json
        if (!skipExport)
            export(schema, iefBlocksMap, ruleBlocks);
    }

    private void export(SpannerlogSchema schema, Map<String, String> iefBlocksMap, List<String> ruleBlocks) {
        JsonObject jsonTree = new JsonObject();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        jsonTree.add("schema", gson.toJsonTree(schema.getRelationSchemas()
                .stream()
                .sorted((s1, s2) -> String.CASE_INSENSITIVE_ORDER.compare(s1.getName(), s2.getName()))
                .map(this::toJson)
                .collect(Collectors.toList()))
        );

        jsonTree.add("ie-functions", gson.toJsonTree(schema.getRelationSchemas()
                .stream()
                .filter(s -> s instanceof IEFunctionSchema)
                .sorted((s1, s2) -> String.CASE_INSENSITIVE_ORDER.compare(s1.getName(), s2.getName()))
                .map(s -> toJson((IEFunctionSchema) s, iefBlocksMap))
                .collect(Collectors.toList()))
        );

        jsonTree.add("rules", gson.toJsonTree(ruleBlocks));

        System.out.println(gson.toJson(jsonTree));
    }

    private JsonObject toJson(IEFunctionSchema schema, Map<String, String> iefBlocksMap) {
        JsonObject schemaJsonObject = new JsonObject();

        String name = schema.getName();
        schemaJsonObject.addProperty("name", name);
        schemaJsonObject.addProperty("function-call-rule", iefBlocksMap.get(name));

        List<Atom> atoms = schema.getAtoms();
        if (atoms.size() == 1 && atoms.get(0) instanceof Regex) {
            schemaJsonObject.addProperty("regex", ((Regex) atoms.get(0)).getCompiledRegexString());
        }

        return schemaJsonObject;
    }

    private JsonObject toJson(RelationSchema schema) {
        JsonObject schemaJsonObject = new JsonObject();
        schemaJsonObject.addProperty("name", schema.getName());

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
            Reader udfReader = line.hasOption("udf") ? new FileReader(line.getOptionValue("udf")) : null;

            new Spannerlog().init(programInputStream, edbReader, udfReader, line.hasOption("s"));

        } catch (ParseException e) {
            System.err.println(e.getMessage());
            printHelp(options);
            exit(1);
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
                .builder("udf")
                .hasArg()
                .numberOfArgs(1)
                .desc("the UDF schemas (required if UDFs are used)")
                .build());

        options.addOption(Option
                .builder("s")
                .longOpt("skip-export")
                .desc("Whether to skip exporting")
                .build());

        return options;
    }
}
