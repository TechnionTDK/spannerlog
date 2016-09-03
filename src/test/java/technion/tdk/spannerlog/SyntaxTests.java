package technion.tdk.spannerlog;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SyntaxTests {
    @Test
    public void parseDatalog() {
        SpannerlogInputParser parser = new SpannerlogInputParser();

        try {
            parser.parseProgram(SyntaxTests.class.getClassLoader().getResourceAsStream("graph.splog"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseRulesWithRegexFormulas() {
        SpannerlogInputParser parser = new SpannerlogInputParser();
        String src;

        try {
            src = "Out() :- Doc(s), RGX<s>\\[x{a*}y{b*}]\\, RGX<s[y]>\\[x{b*}]\\.";
            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

            src = "Out() :- Doc(s), RGX<s>\\[x{0*}y{1*}]\\, RGX<s[y]>\\[x{1*}]\\.";
            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

            src = "Out() :- Doc(\"sdfsd  \"), <s>   \\[   x  {0*}  y               {1*}]\\, <s[y]>\\[x{1*}  ]\\  .\n";
            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

//            src = "Out() :- RGX<\"aaabbb\">\\[x{a*}y{b*}]\\, RGX<s[y]>\\[x{b*}]\\.";
//            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseRulesWithSpans() {
        SpannerlogInputParser parser = new SpannerlogInputParser();
        String src;

        try {
            src = "Q() :- R(x[y][z]).";
            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

            src = "Q() :- R(s[y][1,2][z]), S(s[y]), T(\"abc\"[1,2][y], s[y]).";
            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
