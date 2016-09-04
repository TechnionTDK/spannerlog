package technion.tdk.spannerlog;


import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;

public class RgxTests {

//    @BeforeClass
//    public static void setUpStreams() {
//        System.setOut(new PrintStream(new OutputStream() {
//            public void write(int b) {
//                // DO NOTHING
//            }
//        }));
//    }

    @Test
    public void compileAnbnWithRgx() {
        String splogSrc = "Q() :- doc(s), RGX<s>\\[x{a*}y{b*}]\\, RGX<s[y]>\\[x{b*}]\\.";
        String edbSchema = "{\"doc\":{\"column1\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

}
