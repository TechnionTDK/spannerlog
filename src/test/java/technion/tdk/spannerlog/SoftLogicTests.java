package technion.tdk.spannerlog;


import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;


public class SoftLogicTests {

    @Test
    public void compileSimpleSoftRule() {
        String splogSrc = "R(x) :~ S(x, _).";
        String edbSchema = "{\"S\":{\"column1\":\"text\",\"column2\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test(expected = VariableConflictException.class)
    public void RigidSoftConflictShouldFail() {
        String splogSrc = "R(x) :~ S(x, _).\n" +
                          "R(x) :- S(_, x).";
        String edbSchema = "{\"S\":{\"column1\":\"text\",\"column2\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void CompileQueryWithWeight() {
        String splogSrc = "@weight(3.0) R(x) :~ S(x, _).";
        String edbSchema = "{\"S\":{\"column1\":\"text\",\"column2\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

}
