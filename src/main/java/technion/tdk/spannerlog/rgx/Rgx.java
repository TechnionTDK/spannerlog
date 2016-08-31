package technion.tdk.spannerlog.rgx;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import technion.tdk.spannerlog.rgx.antlr.RgxBaseVisitor;
import technion.tdk.spannerlog.rgx.antlr.RgxLexer;
import technion.tdk.spannerlog.rgx.antlr.RgxParser;
import technion.tdk.spannerlog.utils.antlr.ExceptionThrowerListener;

import java.util.stream.Collectors;

public class Rgx {

    public String compileRegex(String regex) {
        RgxLexer lexer = new RgxLexer(new ANTLRInputStream(regex));
        lexer.addErrorListener(ExceptionThrowerListener.getInstance());

        RgxParser parser = new RgxParser(new CommonTokenStream(lexer));
        parser.addErrorListener(ExceptionThrowerListener.getInstance());
        ParseTree tree = parser.regex();

        return new RegexVisitor().visit(tree);
    }

    private class RegexVisitor extends RgxBaseVisitor<String> {

        @Override
        public String visitCaptureClause(RgxParser.CaptureClauseContext ctx) {
            String varName = ctx.identifier().getText();

            return "(?P<" + varName + ">" + this.visit(ctx.regex()) + ")";
        }

        @Override
        public String visitRegexBasic(RgxParser.RegexBasicContext ctx) {
            return ctx.children
                    .stream()
                    .map(this::visit)
                    .collect(Collectors.joining());
        }

        @Override
        public String visitRegexSimple(RgxParser.RegexSimpleContext ctx) {
            return ctx.children
                    .stream()
                    .map(this::visit)
                    .collect(Collectors.joining());
        }

        @Override
        public String visitTerminal(TerminalNode node) {
            return node.getText();
        }
    }
}
