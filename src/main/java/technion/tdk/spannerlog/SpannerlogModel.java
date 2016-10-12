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

class PredVarDec extends Statement {
    private String relationSchemaName;

    PredVarDec(String relationSchemaName) {
        this.relationSchemaName = relationSchemaName;
    }

    String getRelationSchemaName() {
        return relationSchemaName;
    }
}

abstract class RuleWithConjunctiveQuery extends Statement {
    private ConjunctiveQueryBody body;

    RuleWithConjunctiveQuery(ConjunctiveQueryBody body) {
        this.body = body;
    }

    ConjunctiveQueryBody getBody() {
        return body;
    }
}

class ExtractionRule extends RuleWithConjunctiveQuery {
    private DBAtom head;

    ExtractionRule(DBAtom head, ConjunctiveQueryBody body) {
        super(body);
        this.head = head;
    }

    DBAtom getHead() {
        return head;
    }
}

class SupervisionRule extends RuleWithConjunctiveQuery {
    private DBAtom head;
    private ExprTerm supervisionExpr;

    SupervisionRule(DBAtom head, ExprTerm supervisionExpr, ConjunctiveQueryBody body) {
        super(body);
        this.head = head;
        this.supervisionExpr = supervisionExpr;
    }

    DBAtom getHead() {
        return head;
    }

    ExprTerm getSupervisionExpr() {
        return supervisionExpr;
    }
}

class InferenceRule extends RuleWithConjunctiveQuery {
    private InferenceRuleHead head;

    InferenceRule(InferenceRuleHead head, ConjunctiveQueryBody body) {
        super(body);
        this.head = head;
    }

    InferenceRuleHead getHead() {
        return head;
    }
}

class InferenceRuleHead {
    private FactorFunction factorFunction;
    private List<DBAtom> headAtoms;

    InferenceRuleHead(FactorFunction factorFunction, List<DBAtom> headAtoms) {
        this.factorFunction = factorFunction;
        this.headAtoms = headAtoms;
    }

    FactorFunction getFactorFunction() {
        return factorFunction;
    }

    List<DBAtom> getHeadAtoms() {
        return headAtoms;
    }
}

abstract class FactorFunction {
}

class IsTrue extends FactorFunction {
}

class Imply extends FactorFunction {
}

class Or extends FactorFunction {
}

class And extends FactorFunction {
}

class ConjunctiveQueryBody {
    private List<BodyElement> bodyElements;

    ConjunctiveQueryBody(List<BodyElement> bodyElements) {
        this.bodyElements = bodyElements;
    }

    List<BodyElement> getBodyElements() {
        return bodyElements;
    }
}

abstract class BodyElement {
}

abstract class Condition extends BodyElement {
}

class BinaryCondition extends Condition {
    private ExprTerm lhs;
    private String op;
    private ExprTerm rhs;

    BinaryCondition(ExprTerm lhs, String op, ExprTerm rhs) {
        this.lhs = lhs;
        this.op = op;
        this.rhs = rhs;
    }

    ExprTerm getLhs() {
        return lhs;
    }

    public String getOp() {
        return op;
    }

    ExprTerm getRhs() {
        return rhs;
    }
}

abstract class Atom extends BodyElement {
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
    private String type;

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

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
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

class NullExpr extends ConstExprTerm {
}

class IfThenElseExpr extends ExprTerm {
    private Condition cond;
    private ExprTerm expr;
    private ExprTerm elseExpr;

    IfThenElseExpr(Condition cond, ExprTerm expr, ExprTerm elseExpr) {
        this.cond = cond;
        this.expr = expr;
        this.elseExpr = elseExpr;
    }

    Condition getCond() {
        return cond;
    }

    ExprTerm getExpr() {
        return expr;
    }

    ExprTerm getElseExpr() {
        return elseExpr;
    }
}

class FuncExpr extends ExprTerm {
    private String function;
    private List<ExprTerm> args;

    FuncExpr(String function, List<ExprTerm> args) {
        this.function = function;
        this.args = args;
    }

    String getFunction() {
        return function;
    }

    List<ExprTerm> getArgs() {
        return args;
    }
}
