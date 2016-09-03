package technion.tdk.spannerlog;


import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertTrue;

public class RgxTests {

    private boolean checkCompilation(String splogSrc, String edbSchema, String udfSchema, boolean skipExport) {
        try {

            InputStream programInputStream = new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8));
            Reader edbReader = !StringUtils.isEmpty(edbSchema) ? new StringReader(edbSchema) : null;
            Reader udfReader = !StringUtils.isEmpty(udfSchema) ? new StringReader(udfSchema) : null;

            new Spannerlog().init(programInputStream, edbReader, udfReader, skipExport);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

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
        String splogSrc = "Q() :- doc(s), RGX<\"aaabbb\">\\[x{a*}y{b*}]\\, RGX<s[y]>\\[x{b*}]\\.";
        String edbSchema = "{\"doc\":{\"column1\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }
}
