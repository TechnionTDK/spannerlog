package technion.tdk.spannerlog;


import java.util.List;

class Program {
    private List<Statement> statements;

    Program(List<Statement> statements) {
        this.statements = statements;
    }

    List<Statement> getStatements() {
        return statements;
    }
}


abstract class Statement {
}

class ConjunctiveQuery extends Statement {
    private ConjunctiveQueryHead head;
    private ConjunctiveQueryBody body;

    ConjunctiveQuery(ConjunctiveQueryHead head, ConjunctiveQueryBody body) {
        this.head = head;
        this.body = body;
    }

    ConjunctiveQueryHead getHead() {
        return head;
    }

    ConjunctiveQueryBody getBody() {
        return body;
    }
}

class ConjunctiveQueryHead {
    private DBAtom headAtom;

    ConjunctiveQueryHead(DBAtom headAtom) {
        this.headAtom = headAtom;
    }

    DBAtom getHeadAtom() {
        return headAtom;
    }
}

class ConjunctiveQueryBody {
    private List<Atom> bodyAtoms;

    ConjunctiveQueryBody(List<Atom> bodyAtoms) {
        this.bodyAtoms = bodyAtoms;
    }

    List<Atom> getBodyAtoms() {
        return bodyAtoms;
    }
}


abstract class Atom {
    private String schemaName;
    private List<Term> terms;
    private RelationSchema schema;

    Atom(String schemaName, List<Term> terms) {
        this.schemaName = schemaName;
        this.terms = terms;
    }

    public RelationSchema getSchema() {
        return schema;
    }

    public void setSchema(RelationSchema schema) {
        this.schema = schema;
    }

    String getSchemaName() {
        return schemaName;
    }

    List<Term> getTerms() {
        return terms;
    }

    void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
}

class DBAtom extends Atom {
    DBAtom(String schemaName, List<Term> terms) {
        super(schemaName, terms);
    }
}

class IEAtom extends Atom {
    private Term inputTerm;

    IEAtom(String schemaName, List<Term> terms, Term inputTerm) {
        super(schemaName, terms);
        this.inputTerm = inputTerm;
    }

    Term getInputTerm() {
        return inputTerm;
    }
}

class Regex extends IEAtom {

    private String regexString;
    private String compiledRegexString;

    Regex(String schemaName, List<Term> terms, Term inputTerm, String regexString) {
        super(schemaName, terms, inputTerm);
        this.regexString = regexString;
    }
    String getRegexString() {
        return regexString;
    }

    String getCompiledRegexString() {
        return compiledRegexString;
    }

    void setCompiledRegexString(String compiledRegexString) {
        this.compiledRegexString = compiledRegexString;
    }
}

abstract class Term {
}

class PlaceHolderTerm extends Term { // singleton
    private static PlaceHolderTerm ourInstance = new PlaceHolderTerm();

    static PlaceHolderTerm getInstance() {
        return ourInstance;
    }

    private PlaceHolderTerm() {
    }
}

abstract class ExprTerm extends Term {
}

class VarTerm extends ExprTerm implements StringTerm, SpanTerm {
    private String varName;
    private List<SpanTerm> spans;

    VarTerm(String varName) {
        this.varName = varName;
    }

    String getVarName() {
        return varName;
    }

    @Override
    public void setSpans(List<SpanTerm> spans) {
        this.spans = spans;
    }

    @Override
    public List<SpanTerm> getSpans() {
        return spans;
    }
}

/* A term class that implements this interface can act as a string term, and therefore may have span terms. For more details, see section 4 in http://dl.acm.org/citation.cfm?doid=2932194.2932200 */
interface StringTerm {
    void setSpans(List<SpanTerm> spans);
    List<SpanTerm> getSpans();
}

/* A term class that implements this interface can act as a span term. For more details, see section 4 in http://dl.acm.org/citation.cfm?doid=2932194.2932200 */
interface SpanTerm {
}

abstract class ConstExprTerm extends ExprTerm {
}

class StringConstExpr extends ConstExprTerm implements StringTerm {
    private String value;
    private List<SpanTerm> spans;

    StringConstExpr(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }

    @Override
    public void setSpans(List<SpanTerm> spans) {
        this.spans = spans;
    }

    @Override
    public List<SpanTerm> getSpans() {
        return spans;
    }
}

class IntConstExpr extends ConstExprTerm {
    private int value;

    IntConstExpr(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}

class BooleanConstExpr extends ConstExprTerm {
    private boolean value;

    BooleanConstExpr(boolean value) {
        this.value = value;
    }

    boolean getValue() {
        return value;
    }
}

class FloatConstExpr extends ConstExprTerm {
    private float value;

    FloatConstExpr(float value) {
        this.value = value;
    }

    float getValue() {
        return value;
    }
}

class SpanConstExpr extends ConstExprTerm implements SpanTerm {
    private int start;
    private int end;

    SpanConstExpr(int start, int end) {
        this.start = start;
        this.end = end;
    }

    int getStart() {
        return start;
    }

    int getEnd() {
        return end;
    }
}
