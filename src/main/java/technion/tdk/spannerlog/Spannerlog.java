package technion.tdk.spannerlog;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
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
        Map<String, List<String>> blocks = new SpannerlogCompiler().compile(program, schema);

        if (!skipExport)
            export(program, blocks);
    }

    private void export(Program program, Map<String, List<String>> blocks) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapter(Regex.class, new RegexAdapter().nullSafe())
                .create();

        JsonObject jsonTree = (JsonObject) gson.toJsonTree(blocks);

        List<Regex> RegexList = program.getStatements()
                .stream()
                .filter(stmt -> stmt instanceof ConjunctiveQuery)
                .map(stmt -> (ConjunctiveQuery) stmt)
                .flatMap(cq -> cq.getBody().getBodyAtoms().stream())
                .filter(atom -> atom instanceof Regex)
                .map(atom -> (Regex) atom)
                .collect(Collectors.toList());

        jsonTree.add("rgx", gson.toJsonTree(RegexList));

        System.out.println(gson.toJson(jsonTree));
    }

    private class RegexAdapter extends TypeAdapter<Regex> {

        @Override
        public void write(JsonWriter jsonWriter, Regex regex) throws IOException {
            jsonWriter.beginObject();
            writeRegex(jsonWriter, regex);
            jsonWriter.endObject();
        }

        private void writeRegex(JsonWriter jsonWriter, Regex regex) throws IOException {
            jsonWriter.name(regex.getSchema().getName());
            jsonWriter.beginObject();
            jsonWriter.name("regex").value(regex.getCompiledRegexString());
            writeAttributes(jsonWriter, regex.getSchema().getAttrs());
            jsonWriter.endObject();
        }

        private void writeAttributes(JsonWriter jsonWriter, List<Attribute> attrs) throws IOException {
            jsonWriter.name("attributes");
            jsonWriter.beginObject();
            for (Attribute attr : attrs)
                jsonWriter.name(attr.getName()).value(attr.getType());
            jsonWriter.endObject();
        }


        @Override
        public Regex read(JsonReader jsonReader) throws IOException {
            return null;
        }
    }

    public static void main(String[] args) {
        Options options = setCommandLineOptions();

        try {
            CommandLine line = new DefaultParser().parse(options, args);

            if (line.getArgs().length % 2 != 0) {
                System.err.println("Missing a required option or an argument");
                printHelp(options);
                exit(1);
            }

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
