// Generated from /home/yoavn/Workspace/Projects/spannerlog/src/main/java/technion/tdk/spannerlog/parse/grammar/rgx/Regex.g4 by ANTLR 4.5.3
package technion.tdk.spannerlog.antlr.rgx;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RegexParser}.
 */
public interface RegexListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RegexParser#regex}.
	 * @param ctx the parse tree
	 */
	void enterRegex(RegexParser.RegexContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#regex}.
	 * @param ctx the parse tree
	 */
	void exitRegex(RegexParser.RegexContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#regexSimple}.
	 * @param ctx the parse tree
	 */
	void enterRegexSimple(RegexParser.RegexSimpleContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#regexSimple}.
	 * @param ctx the parse tree
	 */
	void exitRegexSimple(RegexParser.RegexSimpleContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#regexBasic}.
	 * @param ctx the parse tree
	 */
	void enterRegexBasic(RegexParser.RegexBasicContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#regexBasic}.
	 * @param ctx the parse tree
	 */
	void exitRegexBasic(RegexParser.RegexBasicContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#regexElementry}.
	 * @param ctx the parse tree
	 */
	void enterRegexElementry(RegexParser.RegexElementryContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#regexElementry}.
	 * @param ctx the parse tree
	 */
	void exitRegexElementry(RegexParser.RegexElementryContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(RegexParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(RegexParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#anyChar}.
	 * @param ctx the parse tree
	 */
	void enterAnyChar(RegexParser.AnyCharContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#anyChar}.
	 * @param ctx the parse tree
	 */
	void exitAnyChar(RegexParser.AnyCharContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#captureClause}.
	 * @param ctx the parse tree
	 */
	void enterCaptureClause(RegexParser.CaptureClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#captureClause}.
	 * @param ctx the parse tree
	 */
	void exitCaptureClause(RegexParser.CaptureClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(RegexParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(RegexParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegexParser#chars}.
	 * @param ctx the parse tree
	 */
	void enterChars(RegexParser.CharsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegexParser#chars}.
	 * @param ctx the parse tree
	 */
	void exitChars(RegexParser.CharsContext ctx);
}