package technion.tdk.spannerlog;


import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class RgxTests {

    private boolean checkCompilation(String splogSrc, String edbSchema, String udfSchema) {
        try {

            SpannerlogInputParser parser = new SpannerlogInputParser();
            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            Map<String, String> rgxMap = new SpannerlogDesugarRewriter().derive(program);
            System.out.println(rgxMap);

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

        assertTrue(checkCompilation(splogSrc, edbSchema, null));
    }
}
