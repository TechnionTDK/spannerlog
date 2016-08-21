package technion.tdk.spannerlog;

import org.apache.commons.cli.*;

import java.io.IOException;

public class Spannerlog {

    private void initCompilation(String programFilePath) throws IOException {
        // parse program
        SpannerlogInputParser parser = new SpannerlogInputParser();
        Program program = parser.parseProgram(programFilePath);

        // desugar
        program = new SpannerlogDesugarRewriter().derive(program);

        // compile
        SpannerlogCompiler compiler = new SpannerlogCompiler();
        compiler.compile(program);
    }

    public static void main(String[] args) {
        Options options = setCommandLineOptions();

        try {
            CommandLine line = new DefaultParser().parse(options, args);
            Spannerlog sp = new Spannerlog();

            if (line.hasOption("compile"))  sp.initCompilation(line.getOptionValue("compile"));
            else printHelp(options);

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            printHelp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("splog", options);
    }

    private static Options setCommandLineOptions() {
        Options options = new Options();
        options.addOption(new Option("compile", true, "Compile app.splog"));

        return options;
    }
}
