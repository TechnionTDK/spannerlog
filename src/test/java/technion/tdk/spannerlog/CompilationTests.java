package technion.tdk.spannerlog;


import org.junit.Ignore;
import org.junit.Test;
import technion.tdk.spannerlog.utils.dependencies.CircularDependencyException;

import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;


public class CompilationTests {

    @Test(expected = UndefinedRelationSchema.class)
    public void programWithUndefinedSchemaShouldFail() {

        String splogSrc = "Q() <- R(True).";
        assertTrue(checkCompilation(splogSrc, null, null, false));
    }

    @Test(expected = UndefinedRelationSchema.class)
    public void programWithUndefinedSchemaShouldFail2() {

        String splogSrc = "Q(x) <- S(x), Q(3).";
        assertTrue(checkCompilation(splogSrc, null, null, false));
    }

    @Test
    public void inferBasicDependency() {

        String splogSrc = "Q(x) <- S(x), T(3).";
        String edbSchema = "{\"S\":{\"col_s\":\"int\"}, \"T\":{\"col_t\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = AttributeTypeConflictException.class)
    public void programWithConflictingAttrTypesShouldFail() {

        String splogSrc = "Q(x) <- S(x), T(\"3\").";
        String edbSchema = "{\"S\":{\"col\":\"int\"}, \"T\":{\"col\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = AttributeTypeCannotBeInferredException.class)
    public void programWithConflictingAttrTypesShouldFail2() {

        String splogSrc = "Q(x) <- S(x), T(x).";
        String edbSchema = "{\"S\":{\"col\":\"int\"}, \"T\":{\"col\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = NumberOfAttributesInSchemaConflictException.class)
    public void programWithInCosistentSchemaShouldFail() {
        String splogSrc = "Path(x,y) <- Edge(x,y).\n" +
                          "Path(x) <- Edge(x,y),Path(y).";
        String edbSchema = "{\"Edge\":{\"node1\":\"int\", \"node2\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void compileNonBooleanCQ() {
        String splogSrc = "Path(x,y) <- Edge(x,y).\n" +
                "Path(x,z) <- Edge(x,y),Path(y,z).";
        String edbSchema = "{\"Edge\":{\"node1\":\"int\", \"node2\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = AttributeTypeCannotBeInferredException.class)
    public void programWithCircularDependencyShouldFail() {
        String splogSrc = "R(x,y) <- S(x), T(y).\n" +
                          "T(z) <- R(x,z), S(x).";
        String edbSchema = "{\"S\":{\"col\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = UnboundVariableException.class)
    public void programWithUnboundVarShouldFail() {
        String splogSrc = "R(x,y) <- S(x).";
        String edbSchema = "{\"S\":{\"col\":\"int\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = UnboundVariableException.class)
    public void programWithUnboundVarShouldFail2() {
        String splogSrc = "Q(z) <- doc(s), rgx1<s>(x,y), rgx2<s[y]>(x).";
        String edbSchema = "{\"doc\":{\"column1\":\"text\"}}";
        String udfSchema = "{\"rgx1\":{\"s\":\"text\",\"x\":\"span\",\"y\":\"span\"},\"rgx2\":{\"s\":\"text\",\"x\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema, false));
    }

    @Test
    public void compileQueryWithSpans() {
        String splogSrc = "Q(\"Hello World\"[1,6][2,4], s[t]) <- R(s,t).";
        String edbSchema = "{\"R\":{\"a1\":\"text\", \"a2\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = SpanAppliedToNonStringTypeAttributeException.class)
    public void ApplyingSpansToNonStringTypeAttributeShouldFail() {
        String splogSrc = "Q(\"Hello World\"[1,6][2,4], s[t]) <- R(s,t).";
        String edbSchema = "{\"R\":{\"a1\":\"int\", \"a2\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = UnboundVariableException.class)
    public void programWithUnboundSpanVarShouldFail() {
        String splogSrc = "Q() <- Doc(s), R<s[y]>(x).";
        String edbSchema = "{\"Doc\":{\"column1\":\"text\"}}";
        String udfSchema = "{\"R\":{\"s\":\"text\",\"x\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema, false));
    }

    @Test
    public void compileAnbnQuery() {
        String splogSrc = "Q() <- doc(s), rgx1<s>(x,y), rgx2<s[y]>(x).";
        String edbSchema = "{\"doc\":{\"column1\":\"text\"}}";
        String udfSchema = "{\"rgx1\":{\"s\":\"text\",\"x\":\"span\",\"y\":\"span\"},\"rgx2\":{\"s\":\"text\",\"x\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema, false));
    }

    @Test(expected = UndefinedRelationSchema.class)
    public void compileExampleQueryWithIefSchemaMissing() {
        String splogSrc = "Q(s[x]) <- Articles(s,_,_,_,_,_), MyNer<s>(x, \"ORG\").";
        String edbSchema = "{\n" +
                "    \"Articles\": {\n" +
                "        \"column1\": \"text\",\n" +
                "        \"column2\": \"text\",\n" +
                "        \"column3\": \"text\",\n" +
                "        \"column4\": \"text\",\n" +
                "        \"column5\": \"text\",\n" +
                "        \"column6\": \"text\"\n" +
                "    }\n" +
                "}\n";
//        String udfSchema = "{\"rgx1\":{\"s\":\"text\",\"x\":\"span\",\"y\":\"span\"},\"rgx2\":{\"s\":\"text\",\"x\":\"span\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = UndefinedRelationSchema.class)
    public void compileExampleQueryWithEdbSchemaMissing() {
        String splogSrc = "Q(s[x]) <- Articles(s,_,_,_,_,_), NER<s>(x, \"ORG\").";
        String edbSchema = "{\n" +
                "}\n";
//        String udfSchema = "{\"NER\":{\"s\":\"text\", \"x\":\"span\",\"y\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = BuiltIeFunctionNameConflict.class)
    public void compileExampleQueryWithIefSchemaConflict() {
        String splogSrc = "Q(s[x]) <- Articles(s,_,_,_,_,_), NER<s>(x, \"ORG\").";
        String edbSchema = "{\n" +
                "}\n";
        String udfSchema = "{\"NER\":{\"s\":\"text\", \"x\":\"span\",\"y\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema, false));
    }

    @Test
    public void compileExampleQuery() {
        String splogSrc = "Q(s[x]) <- Articles(s,_,_,_,_,_), NER<s>(x, \"ORG\").";
        String edbSchema = "{\n" +
                "    \"Articles\": {\n" +
                "        \"column1\": \"text\",\n" +
                "        \"column2\": \"text\",\n" +
                "        \"column3\": \"text\",\n" +
                "        \"column4\": \"text\",\n" +
                "        \"column5\": \"text\",\n" +
                "        \"column6\": \"text\"\n" +
                "    }\n" +
                "}\n";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }
}
