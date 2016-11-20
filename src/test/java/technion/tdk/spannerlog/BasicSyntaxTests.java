package technion.tdk.spannerlog;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicSyntaxTests {
    @Test
    public void parseRulesWithRegexFormulas() {
        SpannerlogInputParser parser = new SpannerlogInputParser();
        String src;

        try {
            src = "Out() <- Doc(s), RGX<s>\\[x{a*}y{b*}]\\, RGX<s[y]>\\[x{b*}]\\.";
            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

            src = "Out() <- Doc(s), RGX<s>\\[x{0*}y{1*}]\\, RGX<s[y]>\\[x{1*}]\\.";
            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

            src = "Out() <- Doc(\"sdfsd  \"), <s>   \\[   x  {0*}  y               {1*}]\\, <s[y]>\\[x{1*}  ]\\  .\n";
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
            src = "Q() <- R(x[y][z]).";
            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

            src = "Q() <- R(s[y][1,2][z]), S(s[y]), T(\"abc\"[1,2][y], s[y]).";
            parser.parseProgram(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void valuesCheckForQueryWithRegex() {
        SpannerlogInputParser parser = new SpannerlogInputParser();

        try {
            String splogSrc = "Out() <- Doc(s), RGX<s>\\[x{a*}y{b*}]\\, <s[y][0,5]>\\[x{b*}]\\.";

            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            List<Statement> statements = program.getStatements();
            ExtractionRule cq = (ExtractionRule) statements.get(0);

            List<Atom> bodyAtoms = cq.getBody().getBodyElements()
                    .stream()
                    .filter(bodyElement -> bodyElement instanceof Atom)
                    .map(bodyElement -> (Atom) bodyElement)
                    .collect(Collectors.toList());

            assertEquals(3, bodyAtoms.size());

            Regex rgx;
            // First regex
            rgx = (Regex) bodyAtoms.get(1);
            VarTerm varTerm = (VarTerm) rgx.getInputTerm();
            assertEquals("s", varTerm.getVarName());
            assertTrue(varTerm.getSpans().isEmpty());
            assertEquals("x{a*}y{b*}", rgx.getRegexString());

            // Second regex
            rgx = (Regex) bodyAtoms.get(2);
            varTerm = (VarTerm) rgx.getInputTerm();
            assertEquals("s", varTerm.getVarName());
            List<SpanTerm> spans = varTerm.getSpans();
            assertEquals(2, spans.size());
            assertEquals("y", ((VarTerm) spans.get(0)).getVarName());
            SpanConstExpr spanConstExpr = (SpanConstExpr) spans.get(1);
            assertEquals(0, spanConstExpr.getStart());
            assertEquals(5, spanConstExpr.getEnd());
            assertEquals("x{b*}", rgx.getRegexString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
