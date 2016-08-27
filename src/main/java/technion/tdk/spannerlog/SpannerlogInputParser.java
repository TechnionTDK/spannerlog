package technion.tdk.spannerlog;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import technion.tdk.spannerlog.antlr.ExceptionThrowerListener;
import technion.tdk.spannerlog.antlr.main.SpannerlogBaseVisitor;
import technion.tdk.spannerlog.antlr.main.SpannerlogLexer;
import technion.tdk.spannerlog.antlr.main.SpannerlogParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


class SpannerlogInputParser {

    Program parseProgram(InputStream is) throws IOException {
        return new TreeVisitor().parseProgram(is);

    }

    Program parseProgram(String filename) throws IOException {
        return parseProgram(new FileInputStream(filename));
    }
}

class TreeVisitor {

    Program parseProgram(InputStream is) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(is);
        SpannerlogLexer lexer = new SpannerlogLexer(input);
        lexer.addErrorListener(ExceptionThrowerListener.getInstance());

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SpannerlogParser parser = new SpannerlogParser(tokens);
        parser.addErrorListener(ExceptionThrowerListener.getInstance());
        ParseTree tree = parser.program();

        ProgramVisitor programVisitor = new ProgramVisitor();
        Program program = programVisitor.visit(tree);
        return program;
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
        public Atom visitRegexAbbr(SpannerlogParser.RegexAbbrContext ctx) {
            return ctx.accept(new RegexVisitor());
        }

    }

    private class IEAtomVisitor extends SpannerlogBaseVisitor<IEAtom> {
        @Override
        public IEAtom visitIEFunction(SpannerlogParser.IEFunctionContext ctx) {
            String relationSchemaName = ctx.relationSchemaName().getText();
            Term inputTerm = ctx.term().accept(new TermVisitor());
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
        public Regex visitRegexAbbr(SpannerlogParser.RegexAbbrContext ctx) {
            Term inputTerm = ctx.term().accept(new TermVisitor());
            String regex = ctx.Regex().getText();
            return new Regex(null, null, inputTerm, regex.substring(2, regex.length() - 2));
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

    private class ExprVisitor extends SpannerlogBaseVisitor<ExprTerm> {
        @Override
        public ExprTerm visitExpr(SpannerlogParser.ExprContext ctx) {
            List<SpanTerm> spans = null;
            if (ctx.getChildCount() > 1) {
                SpanTermVisitor spanTermVisitor = new SpanTermVisitor();
                spans = ctx.span()
                        .stream()
                        .map(span -> span.accept(spanTermVisitor))
                        .collect(Collectors.toList());
            }

            ExprTerm expr = visit(ctx.getChild(0));

            if (expr instanceof StringTerm && spans != null) {
                ((StringTerm) expr).setSpans(spans);
            }

            return expr;
        }

        @Override
        public ExprTerm visitVariable(SpannerlogParser.VariableContext ctx) {
            return new VarTerm(ctx.getText());
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


