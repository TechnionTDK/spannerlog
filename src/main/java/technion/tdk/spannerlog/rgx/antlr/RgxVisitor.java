// Generated from /home/yoavn/Workspace/Projects/spannerlog/src/main/java/technion/tdk/spannerlog/rgx/antlr/Rgx.g4 by ANTLR 4.5.3
package technion.tdk.spannerlog.rgx.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RgxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RgxVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RgxParser#regex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegex(RgxParser.RegexContext ctx);
	/**
	 * Visit a parse tree produced by {@link RgxParser#regexSimple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegexSimple(RgxParser.RegexSimpleContext ctx);
	/**
	 * Visit a parse tree produced by {@link RgxParser#regexBasic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegexBasic(RgxParser.RegexBasicContext ctx);
	/**
	 * Visit a parse tree produced by {@link RgxParser#regexElementry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegexElementry(RgxParser.RegexElementryContext ctx);
	/**
	 * Visit a parse tree produced by {@link RgxParser#group}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup(RgxParser.GroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link RgxParser#anyChar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnyChar(RgxParser.AnyCharContext ctx);
	/**
	 * Visit a parse tree produced by {@link RgxParser#captureClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaptureClause(RgxParser.CaptureClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link RgxParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(RgxParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link RgxParser#chars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChars(RgxParser.CharsContext ctx);
}