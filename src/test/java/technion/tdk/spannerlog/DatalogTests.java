package technion.tdk.spannerlog;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DatalogTests {

    @Test
    public void parseGraphProgram() {
        SpannerlogInputParser parser = new SpannerlogInputParser();

        try {
            parser.parseProgram(BasicSyntaxTests.class.getClassLoader().getResourceAsStream("graph.splog"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void valuesCheckForQueryWithBooleanLiteral() {
        SpannerlogInputParser parser = new SpannerlogInputParser();

        try {
            String splogSrc = "Q() <- R(True).";

            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            List<Statement> statements = program.getStatements();
            assertEquals(1, statements.size());

            ExtractionRule cq = (ExtractionRule) statements.get(0);

            DBAtom head = cq.getHead();
            assertEquals(head.getSchemaName(), "q");
            assertEquals(0, head.getTerms().size());

            List<Atom> body = cq.getBody().getBodyElements()
                    .stream()
                    .filter(bodyElement -> bodyElement instanceof Atom)
                    .map(bodyElement -> (Atom) bodyElement)
                    .collect(Collectors.toList());

            assertEquals(1, body.size());
            DBAtom atomR = (DBAtom) body.get(0);
            assertEquals(atomR.getSchemaName(), "r");
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
            String splogSrc = "Q() <- R(False, \"Hello\", 4, -2, 0.01, - 1.0,  [3,4]).";

            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            List<Statement> statements = program.getStatements();
            ExtractionRule cq = (ExtractionRule) statements.get(0);

            List<Atom> bodyAtoms = cq.getBody().getBodyElements()
                    .stream()
                    .filter(bodyElement -> bodyElement instanceof Atom)
                    .map(bodyElement -> (Atom) bodyElement)
                    .collect(Collectors.toList());

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
}
