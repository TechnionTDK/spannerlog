package technion.tdk.spannerlog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.List;
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
        List<String> blocks = new SpannerlogCompiler().compile(program);

        if (!skipExport)
            export(schema, program, blocks);
    }

    private void export(SpannerlogSchema schema, Program program, List<String> blocks) {
        JsonObject jsonTree = new JsonObject();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
//               .registerTypeAdapter(Regex.class, new RegexAdapter().nullSafe())
                .create();

        jsonTree.add("schema", gson.toJsonTree(schema.getRelationSchemas()
                .stream()
                .map(this::toJson)
                .collect(Collectors.toList()))
        );

        jsonTree.add("ie-function", gson.toJsonTree(schema.getRelationSchemas()
                .stream()
                .filter(s -> s instanceof IEFunctionSchema)
                .map(RelationSchema::getName)
                .collect(Collectors.toList()))
        );

        jsonTree.add("rules", gson.toJsonTree(blocks));

        jsonTree.add("rgx", gson.toJsonTree(program.getStatements()
                .stream()
                .filter(stmt -> stmt instanceof ConjunctiveQuery)
                .map(stmt -> (ConjunctiveQuery) stmt)
                .flatMap(cq -> cq.getBody().getBodyAtoms().stream())
                .filter(atom -> atom instanceof Regex)
                .map(atom -> toJson((Regex) atom))
                .collect(Collectors.toList()))
        );

        System.out.println(gson.toJson(jsonTree));
    }

    private JsonObject toJson(Regex regex) {
        JsonObject regexJsonObject = new JsonObject();

        regexJsonObject.addProperty("name", regex.getSchemaName());
        regexJsonObject.addProperty("regex", regex.getCompiledRegexString());

        return regexJsonObject;
    }

    private JsonObject toJson(RelationSchema schema) {
        JsonObject relationSchemaJsonObject = new JsonObject();
        relationSchemaJsonObject.addProperty("name", schema.getName());

        JsonObject attributesJsonObject = new JsonObject();
        schema.getAttrs().forEach(attr -> attributesJsonObject.addProperty(attr.getName(), attr.getType()));

        relationSchemaJsonObject.add("attributes", attributesJsonObject);

        return relationSchemaJsonObject;
    }

    public static void main(String[] args) {
        Options options = setCommandLineOptions();

        try {
            CommandLine line = new DefaultParser().parse(options, args);

            InputStream programInputStream = new FileInputStream(line.getOptionValue("program"));
            Reader edbReader = line.hasOption("edb") ? new FileReader(line.getOptionValue("edb")) : null;
            Reader udfReader = line.hasOption("udf") ? new FileReader(line.getOptionValue("edb")) : null;

            new Spannerlog().init(programInputStream, edbReader, udfReader, line.hasOption("s"));

        } catch (ParseException e) {
            System.err.println(e.getMessage());
            printHelp(options);
            exit(1);
        } catch (IOException e) {
            e.printStackTrace();
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
