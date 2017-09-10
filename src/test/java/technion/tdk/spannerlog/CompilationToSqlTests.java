package technion.tdk.spannerlog;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;

public class CompilationToSqlTests {

    @Test
    public void compileBasicQueryToSQL() {
        String splogSrc =
                "married(id, x, y) <-" +
                "       articles(id, c)," +
                "       <c>\\[.* x{ [A-Z][a-z]*(\\s[A-Z][a-z]*)* } .* \\s married \\s .* y{ [A-Z][a-z]*(\\s[A-Z][a-z]*)* } .* ]\\," +
                "       ner<c>(x, \"PERSON\")," +
                "       ner<c>(y, \"PERSON\").\n";

        String edbSchema =
                "{" +
                        "\"articles\": {" +
                            "\"id\":\"text\"," +
                            "\"content\":\"text\"" +
                        "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void compileBasicQueryToSQL2() {
        String splogSrc =
                "q(l) <-" +
                        "       articles(id, c)," +
                        "       <c>\\[.*x{\\b[A-Za-z0-9]+\\b\\s\\b[A-Za-z0-9]+\\b}\\sphosphorylates[^.]p53.*]\\," +
                        "       lowercase<c[x]>(l).\n";

        String edbSchema =
                "{" +
                        "\"articles\": {" +
                            "\"id\":\"text\"," +
                            "\"content\":\"text\"" +
                        "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, true));
    }

}
