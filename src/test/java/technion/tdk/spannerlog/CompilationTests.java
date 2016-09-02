package technion.tdk.spannerlog;


import org.apache.commons.lang3.StringUtils;
import org.junit.*;
import technion.tdk.spannerlog.utils.dependencies.CircularDependencyException;

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

            new SpannerlogCompiler().compile(program);

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

    @Test(expected = UndefinedRelationSchema.class)
    public void programWithUndefinedSchemaShouldFail() {

        String splogSrc = "Q() :- R(True).";
        assertTrue(checkCompilation(splogSrc, null, null));
    }

    @Test(expected = UndefinedRelationSchema.class)
    public void programWithUndefinedSchemaShouldFail2() {

        String splogSrc = "Q(x) :- S(x), Q(3).";
        assertTrue(checkCompilation(splogSrc, null, null));
    }

    @Ignore
    @Test
    public void inferBasicDependency() {

        String splogSrc = "Q(x) :- S(x), T(3).";
        String edbSchema = "{\"S\":{\"col\":\"int\"}, \"T\":{\"col\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Test(expected = AttributeTypeConflictException.class)
    public void programWithConflictingAttrTypesShouldFail() {

        String splogSrc = "Q(x) :- S(x), T(\"3\").";
        String edbSchema = "{\"S\":{\"col\":\"int\"}, \"T\":{\"col\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Test(expected = AttributeTypeCannotBeInferredException.class)
    public void programWithConflictingAttrTypesShouldFail2() {

        String splogSrc = "Q(x) :- S(x), T(x).";
        String edbSchema = "{\"S\":{\"col\":\"int\"}, \"T\":{\"col\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Test(expected = AttributeSchemaConflictException.class)
    public void programWithInCosistentSchemaShouldFail() {
        String splogSrc = "Path(x,y) :- Edge(x,y).\n" +
                          "Path(x) :- Edge(x,y),Path(y).";
        String edbSchema = "{\"Edge\":{\"node1\":\"int\", \"node2\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Ignore
    @Test
    public void compileNonBooleanCQ() {
        String splogSrc = "Path(x,y) :- Edge(x,y).\n" +
                "Path(x,z) :- Edge(x,y),Path(y,z).";
        String edbSchema = "{\"Edge\":{\"node1\":\"int\", \"node2\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Test(expected = CircularDependencyException.class)
    public void programWithCircularDependencyShouldFail() {
        String splogSrc = "R(x,y) :- S(x), T(y).\n" +
                          "T(z) :- R(x,z), S(x).";
        String edbSchema = "{\"S\":{\"col\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Test(expected = UnboundVariableException.class)
    public void programWithUnboundVarShouldFail() {
        String splogSrc = "R(x,y) :- S(x).";
        String edbSchema = "{\"S\":{\"col\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Test(expected = UnboundVariableException.class)
    public void programWithUnboundVarShouldFail2() {
        String splogSrc = "Q(z) :- doc(s), rgx1<s>(x,y), rgx2<s[y]>(x).";
        String edbSchema = "{\"doc\":{\"column1\":\"text\"}}";
        String udfSchema = "{\"rgx1\":{\"s\":\"text\",\"x\":\"span\",\"y\":\"span\"},\"rgx2\":{\"s\":\"text\",\"x\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema));
    }


    @Ignore
    @Test
    public void compileQueryWithLiterals() {
        String splogSrc = "Q() :- R(False, \"Hello\", 4, -2, 0.01, - 1.0,  [3,4]).";
        String edbSchema = "{\"R\":{\"a1\":\"boolean\", \"a2\":\"text\", \"a3\":\"int\", \"a4\":\"int\", \"a5\":\"float8\", \"a6\":\"float8\", \"a7\":\"span\"}}";
        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Ignore
    @Test
    public void compileQueryWithSpans() {
        String splogSrc = "Q(\"Hello World\"[1,6][2,4], s[t]) :- R(s,t).";
        String edbSchema = "{\"R\":{\"a1\":\"int\", \"a2\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }

    @Test(expected = UnboundVariableException.class)
    public void programWithUnboundSpanVarShouldFail() {
        String splogSrc = "Q() :- Doc(s), R<s[y]>(x).";
        String edbSchema = "{\"Doc\":{\"column1\":\"text\"}}";
        String udfSchema = "{\"R\":{\"s\":\"text\",\"x\":\"span\"}}";

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
