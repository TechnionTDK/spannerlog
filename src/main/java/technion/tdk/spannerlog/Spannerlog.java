package technion.tdk.spannerlog;

import org.apache.commons.cli.*;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.exit;

public class Spannerlog {

    private void init(CommandLine line) throws IOException {

        // parse program
        SpannerlogInputParser parser = new SpannerlogInputParser();
        Program program = parser.parseProgram(line.getOptionValue("program"));

        // desugar
        program = new SpannerlogDesugarRewriter().derive(program);

        SpannerlogSchema schema = SpannerlogSchema
                .builder()
                .readSchemaFromJson(new FileReader(line.getOptionValue("edb")),
                        RelationSchema.builder().type(RelationSchemaType.EXTENSIONAL))
                .readSchemaFromJson(new FileReader(line.getOptionValue("udf")),
                        RelationSchema.builder().type(RelationSchemaType.IEFUNCTION))
                .extractRelationSchemas(program)
                .build();

        // compile
        SpannerlogCompiler compiler = new SpannerlogCompiler();
        compiler.compile(program, schema);
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

            Spannerlog sp = new Spannerlog();
            sp.init(line);
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
                .desc("the program to compile"
                ).build());

        options.addOption(Option
                .builder("edb")
                .required(false)
                .hasArg()
                .numberOfArgs(1)
                .desc("the EDB schema of the program (required if EDB is not empty)")
                .build());

        options.addOption(Option
                .builder("udf")
                .required(false)
                .hasArg()
                .numberOfArgs(1)
                .desc("the UDF schemas (required if UDFs are used)")
                .build());

        return options;
    }
}
