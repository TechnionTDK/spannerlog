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

class SupervisionRule extends ExtractionRule {
    private Condition posCond;
    private Condition negCond;

    SupervisionRule(DBAtom head, ConjunctiveQueryBody body, Condition posCond, Condition negCond) {
        super(head, body);
        this.posCond = posCond;
        this.negCond = negCond;
    }

    Condition getPosCond() {
        return posCond;
    }

    Condition getNegCond() {
        return negCond;
    }
}

class InferenceRule extends RuleWithConjunctiveQuery {
    private InferenceRuleHead head;
    private FactorWeight weight;

    InferenceRule(InferenceRuleHead head, ConjunctiveQueryBody body, FactorWeight weight) {
        super(body);
        this.head = head;
        this.weight = weight;
    }

    InferenceRuleHead getHead() {
        return head;
    }

    FactorWeight getWeight() {
        return weight;
    }
}

class FactorWeight {
    private Float value;
    private String FeatureVariable;

    FactorWeight(Float value) {
        this.value = value;
    }

    FactorWeight(int value) {
        this.value = (float) value;
    }

    FactorWeight(String featureVariable) {
        this.FeatureVariable = featureVariable;
    }

    FactorWeight() {
    }

    Float getValue() {
        return value;
    }

    String getFeatureVariable() {
        return FeatureVariable;
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

class NegationCondition extends Condition {
    private Condition cond;

    NegationCondition(Condition cond) {
        this.cond = cond;
    }

    Condition getCond() {
        return cond;
    }
}

class ExprCondition extends Condition {
    private ExprTerm expr;

    ExprCondition(ExprTerm expr) {
        this.expr = expr;

    }

    ExprTerm getExpr() {
        return expr;
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
    private boolean isMaterialized;

    IEAtom(String schemaName, List<Term> terms, Term inputTerm, boolean isMaterialized) {
        super(schemaName, terms);
        this.inputTerm = inputTerm;
        this.isMaterialized = isMaterialized;
    }

    Term getInputTerm() {
        return inputTerm;
    }

    boolean isMaterialized() {
        return isMaterialized;
    }
}

class Regex extends IEAtom {

    private String regexString;
    private String compiledRegexString;

    Regex(String schemaName, List<Term> terms, Term inputTerm, String regexString, boolean isMaterialized) {
        super(schemaName, terms, inputTerm, isMaterialized);
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
    private Attribute attr;

    void setAttribute(Attribute attr) {
        this.attr = attr;
    }

    Attribute getAttribute() {
        return attr;
    }
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

    VarTerm(String varName, List<SpanTerm> spans) {
        this.varName = varName;
        this.spans = spans;
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

class FuncExpr extends ExprTerm {
    private String function;
    private List<ExprTerm> args;
    private boolean isAggregation;

    static final String[] AGGREGATION_FUNCTIONS = {"MAX", "SUM", "MIN", "COUNT"};

    FuncExpr(String function, List<ExprTerm> args, boolean isAggregation) {
        this.function = function;
        this.args = args;
        this.isAggregation = isAggregation;
    }

    String getFunction() {
        return function;
    }

    List<ExprTerm> getArgs() {
        return args;
    }

    boolean isAggregation() {
        return isAggregation;
    }
}

class DotFuncExpr extends ExprTerm {
    private VarTerm varTerm;
    private String function;
    private List<ExprTerm> args;

    DotFuncExpr(VarTerm varTerm, String function, List<ExprTerm> args) {
        this.varTerm = varTerm;
        this.function = function;
        this.args = args;
    }

    VarTerm getVarTerm() {
        return varTerm;
    }

    String getFunction() {
        return function;
    }

    List<ExprTerm> getArgs() {
        return args;
    }
}

class BinaryOpExpr extends ExprTerm {
    private ExprTerm lhs;
    private ExprTerm rhs;
    private String op;

    BinaryOpExpr(ExprTerm lhs, ExprTerm rhs, String op) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    ExprTerm getLhs() {
        return lhs;
    }

    ExprTerm getRhs() {
        return rhs;
    }

    String getOp() {
        return op;
    }
}
