package technion.tdk.spannerlog;


import org.junit.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CompilationTests {

    @Test
    public void compileBooleanCQ() {

        try {
            String splogSrc = "Q() :- R(True).";

            SpannerlogInputParser parser = new SpannerlogInputParser();
            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            SpannerlogSchema schema = SpannerlogSchema
                    .builder()
                    .extractRelationSchemas(program)
                    .build();

            SpannerlogCompiler compiler = new SpannerlogCompiler();
            compiler.compile(program, schema);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void compileNonBooleanCQ() {
        try {
            String splogSrc = "Path(x,y) :- Edge(x,y).\n" +
                              "Path(x,z) :- Edge(x,y),Path(y,z).";
            String edbSchema = "{\"Edge\":{\"node1\":\"int\", \"node2\":\"int\"}}";

            SpannerlogInputParser parser = new SpannerlogInputParser();
            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            SpannerlogSchema schema = SpannerlogSchema
                    .builder()
                    .readSchemaFromJson(new StringReader(edbSchema),
                            RelationSchema.builder().type(RelationSchemaType.EXTENSIONAL))
                    .extractRelationSchemas(program)
                    .build();

            SpannerlogCompiler compiler = new SpannerlogCompiler();
            compiler.compile(program, schema);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    @Test
//    public void compileQueryWithLiterals() {
//        try {
//            String splogSrc = "Q() :- R(False, \"Hello\", 4, -2, 0.01, - 1.0,  [3,4]).";
//            SpannerlogInputParser parser = new SpannerlogInputParser();
//            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));
//
//            SpannerlogCompiler compiler = new SpannerlogCompiler();
//            compiler.compile(program);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void compileQueryWithSpans() {
//        try {
//            String splogSrc = "Q(\"Hello World\"[1,6][2,4], s[t]) :- R(s,t).";
//            SpannerlogInputParser parser = new SpannerlogInputParser();
//            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));
//
//            SpannerlogCompiler compiler = new SpannerlogCompiler();
//            compiler.compile(program);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void compileQueryWithIEFunctions() {
//        try {
//            String splogSrc = "Q() :- Doc(s), R<s[y]>(x).";
//            SpannerlogInputParser parser = new SpannerlogInputParser();
//            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));
//
//            SpannerlogCompiler compiler = new SpannerlogCompiler();
//            compiler.compile(program);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @BeforeClass
    public static void setUpStreams() {
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                //DO NOTHING
            }
        }));
    }

    @Test(expected = AttributeTypeCannotBeInferredException.class)
    public void failCompilationForQueryWithUntypedVar() {
        try {
            String splogSrc = "Q(z) :- doc(s), rgx1<s>(x,y), rgx2<s[y]>(x).";
            String edbSchema = "{\"doc\":{\"column1\":\"text\"}}";
            String udfSchema = "{\"rgx1\":{\"s\":\"text\",\"x\":\"span\",\"y\":\"span\"},\"rgx2\":{\"s\":\"text\",\"x\":\"span\"}}";

            SpannerlogInputParser parser = new SpannerlogInputParser();
            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            SpannerlogSchema schema = SpannerlogSchema
                    .builder()
                    .readSchemaFromJson(new StringReader(edbSchema),
                            RelationSchema.builder().type(RelationSchemaType.EXTENSIONAL))
                    .readSchemaFromJson(new StringReader(udfSchema),
                            RelationSchema.builder().type(RelationSchemaType.IEFUNCTION))
                    .extractRelationSchemas(program)
                    .build();

            SpannerlogCompiler compiler = new SpannerlogCompiler();
            compiler.compile(program, schema);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void compileAnbnQuery() {
        try {
            String splogSrc = "Q() :- doc(s), rgx1<s>(x,y), rgx2<s[y]>(x).";
            String edbSchema = "{\"doc\":{\"column1\":\"text\"}}";
            String udfSchema = "{\"rgx1\":{\"s\":\"text\",\"x\":\"span\",\"y\":\"span\"},\"rgx2\":{\"s\":\"text\",\"x\":\"span\"}}";

            SpannerlogInputParser parser = new SpannerlogInputParser();
            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            SpannerlogSchema schema = SpannerlogSchema
                    .builder()
                    .readSchemaFromJson(new StringReader(edbSchema),
                            RelationSchema.builder().type(RelationSchemaType.EXTENSIONAL))
                    .readSchemaFromJson(new StringReader(udfSchema),
                            RelationSchema.builder().type(RelationSchemaType.IEFUNCTION))
                    .extractRelationSchemas(program)
                    .build();

            SpannerlogCompiler compiler = new SpannerlogCompiler();
            compiler.compile(program, schema);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
