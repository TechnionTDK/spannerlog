package technion.tdk.spannerlog;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;

public class ParsingTests {

    @Test
    public void valuesCheckForQueryWithBooleanLiteral() {
        SpannerlogInputParser parser = new SpannerlogInputParser();

        try {
            String splogSrc = "Q() :- R(True).";

            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            List<Statement> statements = program.getStatements();
            assertEquals(1, statements.size());

            ConjunctiveQuery cq = (ConjunctiveQuery) statements.get(0);

            DBAtom head = cq.getHead().getHeadAtom();
            assertEquals(head.getSchemaName(), "Q");
            assertEquals(0, head.getTerms().size());

            List<Atom> body = cq.getBody().getBodyAtoms();
            assertEquals(1, body.size());
            DBAtom atomR = (DBAtom) body.get(0);
            assertEquals(atomR.getSchemaName(), "R");
            List<Term> terms = atomR.getTerms();
            assertEquals(1, terms.size());
            ExprTerm exprTerm = (ExprTerm) terms.get(0);
            BooleanConstExpr booleanConstExpr = (BooleanConstExpr) exprTerm;
            assertTrue(booleanConstExpr.getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void valuesCheckForQueryWithLiterals() {
        SpannerlogInputParser parser = new SpannerlogInputParser();

        try {
            String splogSrc = "Q() :- R(False, \"Hello\", 4, -2, 0.01, - 1.0,  [3,4]).";

            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            List<Statement> statements = program.getStatements();
            ConjunctiveQuery cq = (ConjunctiveQuery) statements.get(0);

            List<Atom> bodyAtoms = cq.getBody().getBodyAtoms();
            assertEquals(1, bodyAtoms.size());
            DBAtom atomR = (DBAtom) bodyAtoms.get(0);
            List<Term> terms = atomR.getTerms();

            assertEquals(7, terms.size());

            // Boolean Literal
            BooleanConstExpr booleanConstExpr = (BooleanConstExpr) terms.get(0);
            assertFalse(booleanConstExpr.getValue());

            // String Literal
            StringConstExpr stringConstExpr = (StringConstExpr) terms.get(1);
            assertEquals("Hello", stringConstExpr.getValue());
            assertTrue(stringConstExpr.getSpans().isEmpty());

            // Integer Literal
            IntConstExpr intConstExpr = (IntConstExpr) terms.get(2);
            assertEquals(4, intConstExpr.getValue());

            // Negative Integer Literal
            intConstExpr = (IntConstExpr) terms.get(3);
            assertEquals(-2, intConstExpr.getValue());

            // Float Literal
            FloatConstExpr floatConstExpr = (FloatConstExpr) terms.get(4);
            assertEquals(Float.toString(0.01f), Float.toString(floatConstExpr.getValue()));

            // Negative Float Literal
            floatConstExpr = (FloatConstExpr) terms.get(5);
            assertEquals(Float.toString(-1.0f), Float.toString(floatConstExpr.getValue()));

            // Span Literal
            SpanConstExpr spanConstExpr = (SpanConstExpr) terms.get(6);
            assertEquals(3, spanConstExpr.getStart());
            assertEquals(4, spanConstExpr.getEnd());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void valuesCheckForQueryWithRegex() {
        SpannerlogInputParser parser = new SpannerlogInputParser();

        try {
            String splogSrc = "Out() :- Doc(s), RGX<s>\\[x{a*}y{b*}]\\, <s[y][0,5]>\\[x{b*}]\\.";

            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            List<Statement> statements = program.getStatements();
            ConjunctiveQuery cq = (ConjunctiveQuery) statements.get(0);

            List<Atom> bodyAtoms = cq.getBody().getBodyAtoms();
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
