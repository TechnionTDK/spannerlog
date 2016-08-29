package technion.tdk.spannerlog;


import org.apache.commons.lang3.StringUtils;
import org.junit.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;


public class CompilationTests {

    private boolean checkCompilation(String splogSrc, String edbSchema, String udfSchema) {
        try {

            SpannerlogInputParser parser = new SpannerlogInputParser();
            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            SpannerlogSchema.Builder builder = SpannerlogSchema
                    .builder();
            if (!StringUtils.isEmpty(edbSchema))
                    builder.readSchemaFromJson(new StringReader(edbSchema),
                            RelationSchema.builder().type(RelationSchemaType.EXTENSIONAL));
            if (!StringUtils.isEmpty(udfSchema))
                    builder.readSchemaFromJson(new StringReader(udfSchema),
                            RelationSchema.builder().type(RelationSchemaType.IEFUNCTION));

            SpannerlogSchema schema = builder.extractRelationSchemas(program).build();

            new SpannerlogCompiler().compile(program, schema);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @BeforeClass
    public static void setUpStreams() {
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                // DO NOTHING
            }
        }));
    }

//    @Ignore
    @Test(expected = undefinedRelationSchema.class)
    public void compileBooleanCQ() {

        String splogSrc = "Q() :- R(True).";
        assertTrue(checkCompilation(splogSrc, null, null));
    }

//    @Ignore
    @Test(expected = undefinedRelationSchema.class)
    public void failCompilationForQueryWithUndefinedSchema() {

        String splogSrc = "Q(x) :- S(x), Q(3).";
        assertTrue(checkCompilation(splogSrc, null, null));
    }

//    @Ignore
    @Test
    public void compileNonBooleanCQ() {
        String splogSrc = "Path(x,y) :- Edge(x,y).\n" +
                "Path(x,z) :- Edge(x,y),Path(y,z).";
        String edbSchema = "{\"Edge\":{\"node1\":\"int\", \"node2\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Test
    public void compileQueryWithLiterals() {
        String splogSrc = "Q() :- R(False, \"Hello\", 4, -2, 0.01, - 1.0,  [3,4]).";
        String edbSchema = "{\"R\":{\"a1\":\"boolean\", \"a2\":\"text\", \"a3\":\"int\", \"a4\":\"int\", \"a5\":\"float8\", \"a6\":\"float8\", \"a7\":\"span\"}}";
        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }


    @Test
    public void compileQueryWithSpans() {
        String splogSrc = "Q(\"Hello World\"[1,6][2,4], s[t]) :- R(s,t).";
        String edbSchema = "{\"R\":{\"a1\":\"int\", \"a2\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Test
    public void compileQueryWithIEFunctions() {
        String splogSrc = "Q() :- Doc(s), R<s[y]>(x).";  // TODO This should fail
        String edbSchema = "{\"Doc\":{\"column1\":\"text\"}}";
        String udfSchema = "{\"R\":{\"s\":\"text\",\"x\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema));
    }

//    @Ignore
    @Test(expected = unboundVariableException.class)
    public void failCompilationForQueryWithUnboundVar() {
        String splogSrc = "Q(z) :- doc(s), rgx1<s>(x,y), rgx2<s[y]>(x).";
        String edbSchema = "{\"doc\":{\"column1\":\"text\"}}";
        String udfSchema = "{\"rgx1\":{\"s\":\"text\",\"x\":\"span\",\"y\":\"span\"},\"rgx2\":{\"s\":\"text\",\"x\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema));
    }

//    @Ignore
    @Test
    public void compileAnbnQuery() {
        String splogSrc = "Q() :- doc(s), rgx1<s>(x,y), rgx2<s[y]>(x).";
        String edbSchema = "{\"doc\":{\"column1\":\"text\"}}";
        String udfSchema = "{\"rgx1\":{\"s\":\"text\",\"x\":\"span\",\"y\":\"span\"},\"rgx2\":{\"s\":\"text\",\"x\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema));
    }
}
