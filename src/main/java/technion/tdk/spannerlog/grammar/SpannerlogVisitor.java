// Generated from /home/yoavn/Workspace/Projects/spannerlog/src/main/java/technion/tdk/spannerlog/grammar/Spannerlog.g4 by ANTLR 4.5.3
package technion.tdk.spannerlog.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SpannerlogParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SpannerlogVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SpannerlogParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(SpannerlogParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#conjunctiveQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunctiveQuery(SpannerlogParser.ConjunctiveQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#rigidConjunctiveQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRigidConjunctiveQuery(SpannerlogParser.RigidConjunctiveQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#softConjunctiveQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSoftConjunctiveQuery(SpannerlogParser.SoftConjunctiveQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(SpannerlogParser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Weight}
	 * labeled alternative in {@link SpannerlogParser#annotationName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeight(SpannerlogParser.WeightContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#annotationArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationArguments(SpannerlogParser.AnnotationArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#annotationArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationArgument(SpannerlogParser.AnnotationArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#conjunctiveQueryHead}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunctiveQueryHead(SpannerlogParser.ConjunctiveQueryHeadContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#conjunctiveQueryBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunctiveQueryBody(SpannerlogParser.ConjunctiveQueryBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#bodyElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBodyElement(SpannerlogParser.BodyElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(SpannerlogParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#binaryCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryCondition(SpannerlogParser.BinaryConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(SpannerlogParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#dbAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDbAtom(SpannerlogParser.DbAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IEFunction}
	 * labeled alternative in {@link SpannerlogParser#ieAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIEFunction(SpannerlogParser.IEFunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Rgx}
	 * labeled alternative in {@link SpannerlogParser#ieAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRgx(SpannerlogParser.RgxContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#termClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermClause(SpannerlogParser.TermClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(SpannerlogParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(SpannerlogParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#stringExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringExpr(SpannerlogParser.StringExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#varExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarExpr(SpannerlogParser.VarExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#span}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpan(SpannerlogParser.SpanContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#spanVarClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpanVarClause(SpannerlogParser.SpanVarClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#relationSchemaName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationSchemaName(SpannerlogParser.RelationSchemaNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(SpannerlogParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#placeHolder}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlaceHolder(SpannerlogParser.PlaceHolderContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#spanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpanLiteral(SpannerlogParser.SpanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(SpannerlogParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#floatingPointLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatingPointLiteral(SpannerlogParser.FloatingPointLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(SpannerlogParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpannerlogParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(SpannerlogParser.StringLiteralContext ctx);
}