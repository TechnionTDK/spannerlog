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
        public Statement visitConjunctiveQuery(SpannerlogParser.ConjunctiveQueryContext ctx) {
            ConjunctiveQueryHead head = ctx.conjunctiveQueryHead().accept(new ConjunctiveQueryHeadVisitor());
            ConjunctiveQueryBody body = ctx.conjunctiveQueryBody().accept(new ConjunctiveQueryBodyVisitor());
            return new ConjunctiveQuery(head, body);
        }
    }

    private class ConjunctiveQueryHeadVisitor extends SpannerlogBaseVisitor<ConjunctiveQueryHead> {
        @Override
        public ConjunctiveQueryHead visitConjunctiveQueryHead(SpannerlogParser.ConjunctiveQueryHeadContext ctx) {
            DBAtomVisitor dbAtomVisitor = new DBAtomVisitor();
            DBAtom head = ctx.dbAtom().accept(dbAtomVisitor);
            return new ConjunctiveQueryHead(head);
        }
    }

    private class ConjunctiveQueryBodyVisitor extends SpannerlogBaseVisitor<ConjunctiveQueryBody> {
        @Override
        public ConjunctiveQueryBody visitConjunctiveQueryBody(SpannerlogParser.ConjunctiveQueryBodyContext ctx) {
            AtomVisitor atomVisitor = new AtomVisitor();
            List<Atom> atoms = ctx.atom()
                    .stream()
                    .map(atom -> atom.accept(atomVisitor))
                    .collect(Collectors.toList());
            return new ConjunctiveQueryBody(atoms);
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

