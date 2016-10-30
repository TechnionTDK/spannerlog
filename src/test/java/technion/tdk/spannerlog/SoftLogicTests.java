package technion.tdk.spannerlog;


import com.google.gson.JsonObject;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;
import static technion.tdk.spannerlog.Utils.compileToJson;
import static technion.tdk.spannerlog.Utils.printJsonTree;


public class SoftLogicTests {

    @Ignore
    @Test
    public void compileSimpleSoftRule() {
        String splogSrc = "_ * [ R(x) ] <- S(x, _).";
        String edbSchema = "{\"S\":{\"column1\":\"text\",\"column2\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void RigidSoftConflictShouldSucceed() {
        String splogSrc = "R(x) <- S(x, _).\n" +
                          "R?.\n" +
                          "_ * [ R(x) ] <- S(_, x).";
        String edbSchema = "{\"S\":{\"column1\":\"text\",\"column2\":\"text\"}}";

        JsonObject jsonTree = compileToJson(splogSrc, edbSchema, null);
        JsonObject rSchema = jsonTree.getAsJsonArray("schema").get(0).getAsJsonObject();
        assertEquals("R", rSchema.get("name").getAsString());
        assertEquals(rSchema.get("variable_type").getAsString(), "boolean");

//        printJsonTree(jsonTree);
    }
}
