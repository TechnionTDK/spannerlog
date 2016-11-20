package technion.tdk.spannerlog;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;

public class CompilationToSqlTests {

    @Test
    public void compileBasicQueryToSQL() {
        String splogSrc =
                "Married(id, x, y) <-" +
                "       Articles(id, c)," +
                "       <c>\\[.* x{ [A-Z][a-z]*(\\s[A-Z][a-z]*)* } .* \\s married \\s .* y{ [A-Z][a-z]*(\\s[A-Z][a-z]*)* } .* ]\\," +
                "       NER<c>(x, \"PERSON\")," +
                "       NER<c>(y, \"PERSON\").\n";

        String edbSchema =
                "{" +
                        "\"Articles\": {" +
                            "\"id\":\"text\"," +
                            "\"content\":\"text\"" +
                        "}" +
                "}";

        String iefSchema =
                "{" +
                        "\"NER\": {" +
                            "\"input\":\"text\"," +
                            "\"entity\":\"span\"," +
                            "\"category\":\"text\"" +
                        "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, iefSchema, true));
    }

}
