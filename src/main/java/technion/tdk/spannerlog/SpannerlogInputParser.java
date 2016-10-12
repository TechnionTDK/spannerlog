package technion.tdk.spannerlog;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import technion.tdk.spannerlog.grammar.SpannerlogBaseVisitor;
import technion.tdk.spannerlog.grammar.SpannerlogLexer;
import technion.tdk.spannerlog.grammar.SpannerlogParser;
import technion.tdk.spannerlog.utils.antlr.ExceptionThrowerListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static technion.tdk.spannerlog.FuncExpr.AGGREGATION_FUNCTIONS;


class SpannerlogInputParser {

    Program parseProgram(InputStream is) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(is);
        SpannerlogLexer lexer = new SpannerlogLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ExceptionThrowerListener.getInstance());

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SpannerlogParser parser = new SpannerlogParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ExceptionThrowerListener.getInstance());
        ParseTree tree = parser.program();

        return new ProgramVisitor().visit(tree);
    }

    private class ProgramVisitor extends SpannerlogBaseVisitor<Program> {
        @Override
        public Program visitProgram(SpannerlogParser.ProgramContext ctx) {
            StatementVisitor statementVisitor = new StatementVisitor();
            List<Statement> statements = ctx.statement()
                    .stream()
                    .map(statement -> statement.accept(statementVisitor))
                    .collect(Collectors.toList());
            return new Program(statements);
        }
    }

    private class StatementVisitor extends SpannerlogBaseVisitor<Statement> {
        @Override
        public Statement visitPredictionVariableDeclaration(SpannerlogParser.PredictionVariableDeclarationContext ctx) {
            return new PredVarDec(ctx.relationSchemaName().getText());
        }

        @Override
        public Statement visitExtractionRule(SpannerlogParser.ExtractionRuleContext ctx) {
            return new ExtractionRule(
                    ctx.dbAtom().accept(new DBAtomVisitor()),
                    ctx.conjunctiveQueryBody().accept(new ConjunctiveQueryBodyVisitor())
            );
        }

        @Override
        public Statement visitSupervisionRule(SpannerlogParser.SupervisionRuleContext ctx) {
            return new SupervisionRule(
                    ctx.dbAtom().accept(new DBAtomVisitor()),
                    ctx.supervisionExpr().accept(new ExprVisitor()),
                    ctx.conjunctiveQueryBody().accept(new ConjunctiveQueryBodyVisitor())
            );
        }

        @Override
        public Statement visitInferenceRule(SpannerlogParser.InferenceRuleContext ctx) {
            return new InferenceRule(
                    ctx.inferenceRuleHead().accept(new InferenceRuleHeadVisitor()),
                    ctx.conjunctiveQueryBody().accept(new ConjunctiveQueryBodyVisitor())
            );
        }
    }

    private class InferenceRuleHeadVisitor extends SpannerlogBaseVisitor<InferenceRuleHead> {
        @Override
        public InferenceRuleHead visitIsTrue(SpannerlogParser.IsTrueContext ctx) {
            return new InferenceRuleHead(
                    new IsTrue(),
                    Stream.of(ctx.dbAtom().accept(new DBAtomVisitor())).collect(Collectors.toList())
            );
        }

        @Override
        public InferenceRuleHead visitImply(SpannerlogParser.ImplyContext ctx) {
            DBAtomVisitor dbAtomVisitor = new DBAtomVisitor();
            return new InferenceRuleHead(
                    new Imply(),
                    ctx.dbAtom()
                            .stream()
                            .map(dbAtomContext -> dbAtomContext.accept(dbAtomVisitor))
                            .collect(Collectors.toList())
            );
        }

        @Override
        public InferenceRuleHead visitOr(SpannerlogParser.OrContext ctx) {
            DBAtomVisitor dbAtomVisitor = new DBAtomVisitor();
            return new InferenceRuleHead(
                    new Or(),
                    ctx.dbAtom()
                            .stream()
                            .map(dbAtomContext -> dbAtomContext.accept(dbAtomVisitor))
                            .collect(Collectors.toList())
            );
        }

        @Override
        public InferenceRuleHead visitAnd(SpannerlogParser.AndContext ctx) {
            DBAtomVisitor dbAtomVisitor = new DBAtomVisitor();
            return new InferenceRuleHead(
                    new And(),
                    ctx.dbAtom()
                            .stream()
                            .map(dbAtomContext -> dbAtomContext.accept(dbAtomVisitor))
                            .collect(Collectors.toList())
            );
        }
    }

    private class ConjunctiveQueryBodyVisitor extends SpannerlogBaseVisitor<ConjunctiveQueryBody> {
        @Override
        public ConjunctiveQueryBody visitConjunctiveQueryBody(SpannerlogParser.ConjunctiveQueryBodyContext ctx) {

            BodyElementVisitor bodyElementVisitor = new BodyElementVisitor();
            List<BodyElement> bodyElements = ctx.bodyElement()
                    .stream()
                    .map(elem -> elem.accept(bodyElementVisitor))
                    .collect(Collectors.toList());

            return new ConjunctiveQueryBody(bodyElements);
        }
    }

    private class BodyElementVisitor extends SpannerlogBaseVisitor<BodyElement> {
        @Override
        public BodyElement visitCondition(SpannerlogParser.ConditionContext ctx) {
            return ctx.accept(new ConditionVisitor());
        }

        @Override
        public BodyElement visitAtom(SpannerlogParser.AtomContext ctx) {
            return ctx.accept(new AtomVisitor());
        }
    }

    private class ConditionVisitor extends SpannerlogBaseVisitor<Condition> {
        @Override
        public Condition visitBinaryCondition(SpannerlogParser.BinaryConditionContext ctx) {
            ExprVisitor exprVisitor = new ExprVisitor();
            return new BinaryCondition(ctx.expr(0).accept(exprVisitor),
                    ctx.compareOperator().getText(),
                    ctx.expr(1).accept(exprVisitor)
            );
        }
    }

    private class AtomVisitor extends SpannerlogBaseVisitor<Atom> {
        @Override
        public Atom visitDbAtom(SpannerlogParser.DbAtomContext ctx) {
            return ctx.accept(new DBAtomVisitor());
        }

        @Override
        public Atom visitIEFunction(SpannerlogParser.IEFunctionContext ctx) {
            return ctx.accept(new IEAtomVisitor());
        }

        @Override
        public Atom visitRgx(SpannerlogParser.RgxContext ctx) {
            return ctx.accept(new RegexVisitor());
        }

    }

    private class IEAtomVisitor extends SpannerlogBaseVisitor<IEAtom> {
        @Override
        public IEAtom visitIEFunction(SpannerlogParser.IEFunctionContext ctx) {
            String relationSchemaName = ctx.relationSchemaName().getText();
            Term inputTerm = ctx.varExpr().accept(new VarExprVisitor());
            TermVisitor termVisitor = new TermVisitor();
            List<Term> terms = ctx.termClause().term()
                    .stream()
                    .map(term -> term.accept(termVisitor))
                    .collect(Collectors.toList());
            terms.add(0, inputTerm);
            return new IEAtom(relationSchemaName, terms, inputTerm);
        }

    }

    private class RegexVisitor extends SpannerlogBaseVisitor<Regex> {
        @Override
        public Regex visitRgx(SpannerlogParser.RgxContext ctx) {
            Term inputTerm = ctx.varExpr().accept(new VarExprVisitor());
            String regex = ctx.Regex().getText();
            List<Term> terms = new ArrayList<>();
            terms.add(inputTerm);
            return new Regex(null, terms, inputTerm, regex.substring(2, regex.length() - 2));
        }
    }

    private class DBAtomVisitor extends SpannerlogBaseVisitor<DBAtom> {
        @Override
        public DBAtom visitDbAtom(SpannerlogParser.DbAtomContext ctx) {
            String relationSchemaName = ctx.relationSchemaName().getText();
            TermVisitor termVisitor = new TermVisitor();
            List<Term> terms = ctx.termClause().term()
                    .stream()
                    .map(term -> term.accept(termVisitor))
                    .collect(Collectors.toList());
            return new DBAtom(relationSchemaName, terms);
        }
    }

    private class TermVisitor extends SpannerlogBaseVisitor<Term> {
        @Override
        public Term visitPlaceHolder(SpannerlogParser.PlaceHolderContext ctx) {
            return PlaceHolderTerm.getInstance();
        }

        @Override
        public Term visitExpr(SpannerlogParser.ExprContext ctx) {
            return ctx.accept(new ExprVisitor());
        }
    }

    private class VarExprVisitor extends SpannerlogBaseVisitor<VarTerm> {
        @Override
        public VarTerm visitVarExpr(SpannerlogParser.VarExprContext ctx) {
            VarTerm varTerm = new VarTerm(ctx.variable().getText());

            SpanTermVisitor spanTermVisitor = new SpanTermVisitor();
            List<SpanTerm> spans = ctx.span()
                    .stream()
                    .map(span -> span.accept(spanTermVisitor))
                    .collect(Collectors.toList());

            varTerm.setSpans(spans);

            return varTerm;
        }
    }

    private class ExprVisitor extends SpannerlogBaseVisitor<ExprTerm> {
        @Override
        public ExprTerm visitBinaryOpExpr(SpannerlogParser.BinaryOpExprContext ctx) {
            return new BinaryOpExpr(visit(ctx.lexpr()), visit(ctx.expr()), ctx.operator().getText());
        }

        @Override
        public ExprTerm visitExpr(SpannerlogParser.ExprContext ctx) {
            return visit(ctx.getChild(0));
        }

        @Override
        public ExprTerm visitVarExpr(SpannerlogParser.VarExprContext ctx) {
            return ctx.accept(new VarExprVisitor());
        }

        @Override
        public ExprTerm visitStringExpr(SpannerlogParser.StringExprContext ctx) {
            ExprTerm stringExpr = visit(ctx.stringLiteral());

            SpanTermVisitor spanTermVisitor = new SpanTermVisitor();
            List<SpanTerm> spans = ctx.span()
                    .stream()
                    .map(span -> span.accept(spanTermVisitor))
                    .collect(Collectors.toList());

            ((StringTerm) stringExpr).setSpans(spans);

            return stringExpr;
        }

        @Override
        public ExprTerm visitStringLiteral(SpannerlogParser.StringLiteralContext ctx) {
            String text = ctx.getText();
            return new StringConstExpr(text.substring(1, text.length() - 1));
        }

        @Override
        public ExprTerm visitIntegerLiteral(SpannerlogParser.IntegerLiteralContext ctx) {
            return new IntConstExpr(Integer.parseInt(ctx.getText()));
        }

        @Override
        public ExprTerm visitFloatingPointLiteral(SpannerlogParser.FloatingPointLiteralContext ctx) {
            return new FloatConstExpr(Float.parseFloat(ctx.getText()));
        }

        @Override
        public ExprTerm visitBooleanLiteral(SpannerlogParser.BooleanLiteralContext ctx) {
            return new BooleanConstExpr(Boolean.parseBoolean(ctx.getText()));
        }

        @Override
        public ExprTerm visitSpanLiteral(SpannerlogParser.SpanLiteralContext ctx) {
            return new SpanConstExpr(
                    Integer.parseInt(ctx.IntegerLiteral(0).getText()),
                    Integer.parseInt(ctx.IntegerLiteral(1).getText())
            );
        }

        @Override
        public ExprTerm visitNullExpr(SpannerlogParser.NullExprContext ctx) {
            return new NullExpr();
        }

        @Override
        public ExprTerm visitIfThenElseExpr(SpannerlogParser.IfThenElseExprContext ctx) {
            return new IfThenElseExpr(
                    ctx.condition().accept(new ConditionVisitor()),
                    ctx.expr(0).accept(this),
                    (ctx.expr().size() == 2) ? ctx.expr(1).accept(this) : null,
                    (ctx.elseIfExpr()
                            .stream()
                            .map(elif -> new ElseIfExpr(elif.condition().accept(new ConditionVisitor()), visit(elif.expr())))
                            .collect(Collectors.toList()))
            );
        }

        @Override
        public ExprTerm visitFuncExpr(SpannerlogParser.FuncExprContext ctx) {
            String funcName = ctx.functionName().getText();
            return new FuncExpr(
                    funcName,
                    ctx.expr().stream().map(e -> e.accept(this)).collect(Collectors.toList()),
                    Stream.of(AGGREGATION_FUNCTIONS).anyMatch(aggFunc -> aggFunc.equalsIgnoreCase(funcName))
            );
        }
    }

    private class SpanTermVisitor extends SpannerlogBaseVisitor<SpanTerm> {

        @Override
        public SpanTerm visitSpanVarClause(SpannerlogParser.SpanVarClauseContext ctx) {
            return new VarTerm(ctx.variable().getText());
        }

        @Override
        public SpanTerm visitSpanLiteral(SpannerlogParser.SpanLiteralContext ctx) {
            return new SpanConstExpr(
                    Integer.parseInt(ctx.IntegerLiteral(0).getText()),
                    Integer.parseInt(ctx.IntegerLiteral(1).getText())
            );
        }
    }
}

