// Generated from /home/yoavn/Workspace/Projects/spannerlog/src/main/java/technion/tdk/spannerlog/grammar/Spannerlog.g4 by ANTLR 4.5.3
package technion.tdk.spannerlog.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SpannerlogParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, BooleanLiteral=13, Identifier=14, StringLiteral=15, 
		IntegerLiteral=16, FloatingPointLiteral=17, RigidSeparator=18, SoftSeparator=19, 
		AnnotationSymbol=20, CompareOperator=21, Regex=22, WS=23, Comment=24;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_conjunctiveQuery = 2, RULE_rigidConjunctiveQuery = 3, 
		RULE_softConjunctiveQuery = 4, RULE_annotation = 5, RULE_annotationName = 6, 
		RULE_annotationArguments = 7, RULE_annotationArgument = 8, RULE_conjunctiveQueryHead = 9, 
		RULE_conjunctiveQueryBody = 10, RULE_bodyElement = 11, RULE_condition = 12, 
		RULE_binaryCondition = 13, RULE_atom = 14, RULE_dbAtom = 15, RULE_ieAtom = 16, 
		RULE_termClause = 17, RULE_term = 18, RULE_expr = 19, RULE_stringExpr = 20, 
		RULE_varExpr = 21, RULE_span = 22, RULE_spanVarClause = 23, RULE_relationSchemaName = 24, 
		RULE_variable = 25, RULE_placeHolder = 26, RULE_spanLiteral = 27, RULE_integerLiteral = 28, 
		RULE_floatingPointLiteral = 29, RULE_booleanLiteral = 30, RULE_stringLiteral = 31;
	public static final String[] ruleNames = {
		"program", "statement", "conjunctiveQuery", "rigidConjunctiveQuery", "softConjunctiveQuery", 
		"annotation", "annotationName", "annotationArguments", "annotationArgument", 
		"conjunctiveQueryHead", "conjunctiveQueryBody", "bodyElement", "condition", 
		"binaryCondition", "atom", "dbAtom", "ieAtom", "termClause", "term", "expr", 
		"stringExpr", "varExpr", "span", "spanVarClause", "relationSchemaName", 
		"variable", "placeHolder", "spanLiteral", "integerLiteral", "floatingPointLiteral", 
		"booleanLiteral", "stringLiteral"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'.'", "'weight'", "'('", "','", "')'", "'<'", "'>'", "'RGX'", "'['", 
		"']'", "'_'", "'-'", null, null, null, null, null, "':-'", "':~'", "'@'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "BooleanLiteral", "Identifier", "StringLiteral", "IntegerLiteral", 
		"FloatingPointLiteral", "RigidSeparator", "SoftSeparator", "AnnotationSymbol", 
		"CompareOperator", "Regex", "WS", "Comment"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Spannerlog.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SpannerlogParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Identifier || _la==AnnotationSymbol) {
				{
				{
				setState(64);
				statement();
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public ConjunctiveQueryContext conjunctiveQuery() {
			return getRuleContext(ConjunctiveQueryContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			conjunctiveQuery();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctiveQueryContext extends ParserRuleContext {
		public RigidConjunctiveQueryContext rigidConjunctiveQuery() {
			return getRuleContext(RigidConjunctiveQueryContext.class,0);
		}
		public SoftConjunctiveQueryContext softConjunctiveQuery() {
			return getRuleContext(SoftConjunctiveQueryContext.class,0);
		}
		public ConjunctiveQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunctiveQuery; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitConjunctiveQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConjunctiveQueryContext conjunctiveQuery() throws RecognitionException {
		ConjunctiveQueryContext _localctx = new ConjunctiveQueryContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_conjunctiveQuery);
		try {
			setState(74);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				rigidConjunctiveQuery();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(73);
				softConjunctiveQuery();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RigidConjunctiveQueryContext extends ParserRuleContext {
		public ConjunctiveQueryHeadContext conjunctiveQueryHead() {
			return getRuleContext(ConjunctiveQueryHeadContext.class,0);
		}
		public TerminalNode RigidSeparator() { return getToken(SpannerlogParser.RigidSeparator, 0); }
		public ConjunctiveQueryBodyContext conjunctiveQueryBody() {
			return getRuleContext(ConjunctiveQueryBodyContext.class,0);
		}
		public RigidConjunctiveQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rigidConjunctiveQuery; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitRigidConjunctiveQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RigidConjunctiveQueryContext rigidConjunctiveQuery() throws RecognitionException {
		RigidConjunctiveQueryContext _localctx = new RigidConjunctiveQueryContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_rigidConjunctiveQuery);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			conjunctiveQueryHead();
			setState(77);
			match(RigidSeparator);
			setState(78);
			conjunctiveQueryBody();
			setState(79);
			match(T__0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SoftConjunctiveQueryContext extends ParserRuleContext {
		public ConjunctiveQueryHeadContext conjunctiveQueryHead() {
			return getRuleContext(ConjunctiveQueryHeadContext.class,0);
		}
		public TerminalNode SoftSeparator() { return getToken(SpannerlogParser.SoftSeparator, 0); }
		public ConjunctiveQueryBodyContext conjunctiveQueryBody() {
			return getRuleContext(ConjunctiveQueryBodyContext.class,0);
		}
		public AnnotationContext annotation() {
			return getRuleContext(AnnotationContext.class,0);
		}
		public SoftConjunctiveQueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_softConjunctiveQuery; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitSoftConjunctiveQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SoftConjunctiveQueryContext softConjunctiveQuery() throws RecognitionException {
		SoftConjunctiveQueryContext _localctx = new SoftConjunctiveQueryContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_softConjunctiveQuery);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_la = _input.LA(1);
			if (_la==AnnotationSymbol) {
				{
				setState(81);
				annotation();
				}
			}

			setState(84);
			conjunctiveQueryHead();
			setState(85);
			match(SoftSeparator);
			setState(86);
			conjunctiveQueryBody();
			setState(87);
			match(T__0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationContext extends ParserRuleContext {
		public TerminalNode AnnotationSymbol() { return getToken(SpannerlogParser.AnnotationSymbol, 0); }
		public AnnotationNameContext annotationName() {
			return getRuleContext(AnnotationNameContext.class,0);
		}
		public AnnotationArgumentsContext annotationArguments() {
			return getRuleContext(AnnotationArgumentsContext.class,0);
		}
		public AnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(AnnotationSymbol);
			setState(90);
			annotationName();
			setState(92);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(91);
				annotationArguments();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationNameContext extends ParserRuleContext {
		public AnnotationNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationName; }
	 
		public AnnotationNameContext() { }
		public void copyFrom(AnnotationNameContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WeightContext extends AnnotationNameContext {
		public WeightContext(AnnotationNameContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitWeight(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationNameContext annotationName() throws RecognitionException {
		AnnotationNameContext _localctx = new AnnotationNameContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_annotationName);
		try {
			_localctx = new WeightContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationArgumentsContext extends ParserRuleContext {
		public List<AnnotationArgumentContext> annotationArgument() {
			return getRuleContexts(AnnotationArgumentContext.class);
		}
		public AnnotationArgumentContext annotationArgument(int i) {
			return getRuleContext(AnnotationArgumentContext.class,i);
		}
		public AnnotationArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationArguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitAnnotationArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationArgumentsContext annotationArguments() throws RecognitionException {
		AnnotationArgumentsContext _localctx = new AnnotationArgumentsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_annotationArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(T__2);
			setState(97);
			annotationArgument();
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(98);
				match(T__3);
				setState(99);
				annotationArgument();
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(105);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationArgumentContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public FloatingPointLiteralContext floatingPointLiteral() {
			return getRuleContext(FloatingPointLiteralContext.class,0);
		}
		public AnnotationArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationArgument; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitAnnotationArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationArgumentContext annotationArgument() throws RecognitionException {
		AnnotationArgumentContext _localctx = new AnnotationArgumentContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_annotationArgument);
		try {
			setState(109);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(107);
				variable();
				}
				break;
			case T__11:
			case FloatingPointLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				floatingPointLiteral();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctiveQueryHeadContext extends ParserRuleContext {
		public DbAtomContext dbAtom() {
			return getRuleContext(DbAtomContext.class,0);
		}
		public ConjunctiveQueryHeadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunctiveQueryHead; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitConjunctiveQueryHead(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConjunctiveQueryHeadContext conjunctiveQueryHead() throws RecognitionException {
		ConjunctiveQueryHeadContext _localctx = new ConjunctiveQueryHeadContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_conjunctiveQueryHead);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			dbAtom();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctiveQueryBodyContext extends ParserRuleContext {
		public List<BodyElementContext> bodyElement() {
			return getRuleContexts(BodyElementContext.class);
		}
		public BodyElementContext bodyElement(int i) {
			return getRuleContext(BodyElementContext.class,i);
		}
		public ConjunctiveQueryBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunctiveQueryBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitConjunctiveQueryBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConjunctiveQueryBodyContext conjunctiveQueryBody() throws RecognitionException {
		ConjunctiveQueryBodyContext _localctx = new ConjunctiveQueryBodyContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_conjunctiveQueryBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			bodyElement();
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(114);
				match(T__3);
				setState(115);
				bodyElement();
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BodyElementContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public BodyElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bodyElement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitBodyElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyElementContext bodyElement() throws RecognitionException {
		BodyElementContext _localctx = new BodyElementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_bodyElement);
		try {
			setState(123);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				atom();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				condition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public BinaryConditionContext binaryCondition() {
			return getRuleContext(BinaryConditionContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			binaryCondition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryConditionContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode CompareOperator() { return getToken(SpannerlogParser.CompareOperator, 0); }
		public BinaryConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryCondition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitBinaryCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryConditionContext binaryCondition() throws RecognitionException {
		BinaryConditionContext _localctx = new BinaryConditionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_binaryCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			expr();
			setState(128);
			match(CompareOperator);
			setState(129);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public DbAtomContext dbAtom() {
			return getRuleContext(DbAtomContext.class,0);
		}
		public IeAtomContext ieAtom() {
			return getRuleContext(IeAtomContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_atom);
		try {
			setState(133);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(131);
				dbAtom();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				ieAtom();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DbAtomContext extends ParserRuleContext {
		public RelationSchemaNameContext relationSchemaName() {
			return getRuleContext(RelationSchemaNameContext.class,0);
		}
		public TermClauseContext termClause() {
			return getRuleContext(TermClauseContext.class,0);
		}
		public DbAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dbAtom; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitDbAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DbAtomContext dbAtom() throws RecognitionException {
		DbAtomContext _localctx = new DbAtomContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_dbAtom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			relationSchemaName();
			setState(136);
			termClause();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IeAtomContext extends ParserRuleContext {
		public IeAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ieAtom; }
	 
		public IeAtomContext() { }
		public void copyFrom(IeAtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RgxContext extends IeAtomContext {
		public VarExprContext varExpr() {
			return getRuleContext(VarExprContext.class,0);
		}
		public TerminalNode Regex() { return getToken(SpannerlogParser.Regex, 0); }
		public RgxContext(IeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitRgx(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IEFunctionContext extends IeAtomContext {
		public RelationSchemaNameContext relationSchemaName() {
			return getRuleContext(RelationSchemaNameContext.class,0);
		}
		public VarExprContext varExpr() {
			return getRuleContext(VarExprContext.class,0);
		}
		public TermClauseContext termClause() {
			return getRuleContext(TermClauseContext.class,0);
		}
		public IEFunctionContext(IeAtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitIEFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IeAtomContext ieAtom() throws RecognitionException {
		IeAtomContext _localctx = new IeAtomContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_ieAtom);
		int _la;
		try {
			setState(152);
			switch (_input.LA(1)) {
			case Identifier:
				_localctx = new IEFunctionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				relationSchemaName();
				setState(139);
				match(T__5);
				setState(140);
				varExpr();
				setState(141);
				match(T__6);
				setState(142);
				termClause();
				}
				break;
			case T__5:
			case T__7:
				_localctx = new RgxContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(145);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(144);
					match(T__7);
					}
				}

				setState(147);
				match(T__5);
				setState(148);
				varExpr();
				setState(149);
				match(T__6);
				setState(150);
				match(Regex);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermClauseContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TermClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitTermClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermClauseContext termClause() throws RecognitionException {
		TermClauseContext _localctx = new TermClauseContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_termClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			match(T__2);
			setState(163);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__10) | (1L << T__11) | (1L << BooleanLiteral) | (1L << Identifier) | (1L << StringLiteral) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral))) != 0)) {
				{
				setState(155);
				term();
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__3) {
					{
					{
					setState(156);
					match(T__3);
					setState(157);
					term();
					}
					}
					setState(162);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(165);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public PlaceHolderContext placeHolder() {
			return getRuleContext(PlaceHolderContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_term);
		try {
			setState(169);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(167);
				placeHolder();
				}
				break;
			case T__8:
			case T__11:
			case BooleanLiteral:
			case Identifier:
			case StringLiteral:
			case IntegerLiteral:
			case FloatingPointLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(168);
				expr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public IntegerLiteralContext integerLiteral() {
			return getRuleContext(IntegerLiteralContext.class,0);
		}
		public FloatingPointLiteralContext floatingPointLiteral() {
			return getRuleContext(FloatingPointLiteralContext.class,0);
		}
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public SpanLiteralContext spanLiteral() {
			return getRuleContext(SpanLiteralContext.class,0);
		}
		public StringExprContext stringExpr() {
			return getRuleContext(StringExprContext.class,0);
		}
		public VarExprContext varExpr() {
			return getRuleContext(VarExprContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_expr);
		try {
			setState(177);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(171);
				integerLiteral();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(172);
				floatingPointLiteral();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(173);
				booleanLiteral();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(174);
				spanLiteral();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(175);
				stringExpr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(176);
				varExpr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringExprContext extends ParserRuleContext {
		public StringLiteralContext stringLiteral() {
			return getRuleContext(StringLiteralContext.class,0);
		}
		public List<SpanContext> span() {
			return getRuleContexts(SpanContext.class);
		}
		public SpanContext span(int i) {
			return getRuleContext(SpanContext.class,i);
		}
		public StringExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitStringExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringExprContext stringExpr() throws RecognitionException {
		StringExprContext _localctx = new StringExprContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_stringExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			stringLiteral();
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(180);
				span();
				}
				}
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarExprContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public List<SpanContext> span() {
			return getRuleContexts(SpanContext.class);
		}
		public SpanContext span(int i) {
			return getRuleContext(SpanContext.class,i);
		}
		public VarExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitVarExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarExprContext varExpr() throws RecognitionException {
		VarExprContext _localctx = new VarExprContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_varExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			variable();
			setState(190);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(187);
				span();
				}
				}
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SpanContext extends ParserRuleContext {
		public SpanVarClauseContext spanVarClause() {
			return getRuleContext(SpanVarClauseContext.class,0);
		}
		public SpanLiteralContext spanLiteral() {
			return getRuleContext(SpanLiteralContext.class,0);
		}
		public SpanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_span; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitSpan(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpanContext span() throws RecognitionException {
		SpanContext _localctx = new SpanContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_span);
		try {
			setState(195);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(193);
				spanVarClause();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(194);
				spanLiteral();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SpanVarClauseContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public SpanVarClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_spanVarClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitSpanVarClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpanVarClauseContext spanVarClause() throws RecognitionException {
		SpanVarClauseContext _localctx = new SpanVarClauseContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_spanVarClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(T__8);
			setState(198);
			variable();
			setState(199);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelationSchemaNameContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(SpannerlogParser.Identifier, 0); }
		public RelationSchemaNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationSchemaName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitRelationSchemaName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationSchemaNameContext relationSchemaName() throws RecognitionException {
		RelationSchemaNameContext _localctx = new RelationSchemaNameContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_relationSchemaName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(SpannerlogParser.Identifier, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PlaceHolderContext extends ParserRuleContext {
		public PlaceHolderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_placeHolder; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitPlaceHolder(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PlaceHolderContext placeHolder() throws RecognitionException {
		PlaceHolderContext _localctx = new PlaceHolderContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_placeHolder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SpanLiteralContext extends ParserRuleContext {
		public List<TerminalNode> IntegerLiteral() { return getTokens(SpannerlogParser.IntegerLiteral); }
		public TerminalNode IntegerLiteral(int i) {
			return getToken(SpannerlogParser.IntegerLiteral, i);
		}
		public SpanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_spanLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitSpanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpanLiteralContext spanLiteral() throws RecognitionException {
		SpanLiteralContext _localctx = new SpanLiteralContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_spanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			match(T__8);
			setState(209); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(208);
				match(IntegerLiteral);
				}
				}
				setState(211); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IntegerLiteral );
			setState(213);
			match(T__3);
			setState(215); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(214);
				match(IntegerLiteral);
				}
				}
				setState(217); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IntegerLiteral );
			setState(219);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegerLiteralContext extends ParserRuleContext {
		public TerminalNode IntegerLiteral() { return getToken(SpannerlogParser.IntegerLiteral, 0); }
		public IntegerLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integerLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerLiteralContext integerLiteral() throws RecognitionException {
		IntegerLiteralContext _localctx = new IntegerLiteralContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_integerLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(221);
				match(T__11);
				}
			}

			setState(224);
			match(IntegerLiteral);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FloatingPointLiteralContext extends ParserRuleContext {
		public TerminalNode FloatingPointLiteral() { return getToken(SpannerlogParser.FloatingPointLiteral, 0); }
		public FloatingPointLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatingPointLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitFloatingPointLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FloatingPointLiteralContext floatingPointLiteral() throws RecognitionException {
		FloatingPointLiteralContext _localctx = new FloatingPointLiteralContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_floatingPointLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(226);
				match(T__11);
				}
			}

			setState(229);
			match(FloatingPointLiteral);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanLiteralContext extends ParserRuleContext {
		public TerminalNode BooleanLiteral() { return getToken(SpannerlogParser.BooleanLiteral, 0); }
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_booleanLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(BooleanLiteral);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringLiteralContext extends ParserRuleContext {
		public TerminalNode StringLiteral() { return getToken(SpannerlogParser.StringLiteral, 0); }
		public StringLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpannerlogVisitor ) return ((SpannerlogVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringLiteralContext stringLiteral() throws RecognitionException {
		StringLiteralContext _localctx = new StringLiteralContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_stringLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			match(StringLiteral);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\32\u00ee\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\7\2D\n\2\f\2\16\2G\13\2\3\3\3\3\3\4\3\4\5\4M\n\4\3\5\3\5\3\5\3"+
		"\5\3\5\3\6\5\6U\n\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\5\7_\n\7\3\b\3\b\3"+
		"\t\3\t\3\t\3\t\7\tg\n\t\f\t\16\tj\13\t\3\t\3\t\3\n\3\n\5\np\n\n\3\13\3"+
		"\13\3\f\3\f\3\f\7\fw\n\f\f\f\16\fz\13\f\3\r\3\r\5\r~\n\r\3\16\3\16\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\5\20\u0088\n\20\3\21\3\21\3\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\5\22\u0094\n\22\3\22\3\22\3\22\3\22\3\22\5\22\u009b"+
		"\n\22\3\23\3\23\3\23\3\23\7\23\u00a1\n\23\f\23\16\23\u00a4\13\23\5\23"+
		"\u00a6\n\23\3\23\3\23\3\24\3\24\5\24\u00ac\n\24\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\5\25\u00b4\n\25\3\26\3\26\7\26\u00b8\n\26\f\26\16\26\u00bb\13"+
		"\26\3\27\3\27\7\27\u00bf\n\27\f\27\16\27\u00c2\13\27\3\30\3\30\5\30\u00c6"+
		"\n\30\3\31\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\6\35"+
		"\u00d4\n\35\r\35\16\35\u00d5\3\35\3\35\6\35\u00da\n\35\r\35\16\35\u00db"+
		"\3\35\3\35\3\36\5\36\u00e1\n\36\3\36\3\36\3\37\5\37\u00e6\n\37\3\37\3"+
		"\37\3 \3 \3!\3!\3!\2\2\"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&("+
		"*,.\60\62\64\668:<>@\2\2\u00e7\2E\3\2\2\2\4H\3\2\2\2\6L\3\2\2\2\bN\3\2"+
		"\2\2\nT\3\2\2\2\f[\3\2\2\2\16`\3\2\2\2\20b\3\2\2\2\22o\3\2\2\2\24q\3\2"+
		"\2\2\26s\3\2\2\2\30}\3\2\2\2\32\177\3\2\2\2\34\u0081\3\2\2\2\36\u0087"+
		"\3\2\2\2 \u0089\3\2\2\2\"\u009a\3\2\2\2$\u009c\3\2\2\2&\u00ab\3\2\2\2"+
		"(\u00b3\3\2\2\2*\u00b5\3\2\2\2,\u00bc\3\2\2\2.\u00c5\3\2\2\2\60\u00c7"+
		"\3\2\2\2\62\u00cb\3\2\2\2\64\u00cd\3\2\2\2\66\u00cf\3\2\2\28\u00d1\3\2"+
		"\2\2:\u00e0\3\2\2\2<\u00e5\3\2\2\2>\u00e9\3\2\2\2@\u00eb\3\2\2\2BD\5\4"+
		"\3\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2F\3\3\2\2\2GE\3\2\2\2HI\5"+
		"\6\4\2I\5\3\2\2\2JM\5\b\5\2KM\5\n\6\2LJ\3\2\2\2LK\3\2\2\2M\7\3\2\2\2N"+
		"O\5\24\13\2OP\7\24\2\2PQ\5\26\f\2QR\7\3\2\2R\t\3\2\2\2SU\5\f\7\2TS\3\2"+
		"\2\2TU\3\2\2\2UV\3\2\2\2VW\5\24\13\2WX\7\25\2\2XY\5\26\f\2YZ\7\3\2\2Z"+
		"\13\3\2\2\2[\\\7\26\2\2\\^\5\16\b\2]_\5\20\t\2^]\3\2\2\2^_\3\2\2\2_\r"+
		"\3\2\2\2`a\7\4\2\2a\17\3\2\2\2bc\7\5\2\2ch\5\22\n\2de\7\6\2\2eg\5\22\n"+
		"\2fd\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2ik\3\2\2\2jh\3\2\2\2kl\7\7\2"+
		"\2l\21\3\2\2\2mp\5\64\33\2np\5<\37\2om\3\2\2\2on\3\2\2\2p\23\3\2\2\2q"+
		"r\5 \21\2r\25\3\2\2\2sx\5\30\r\2tu\7\6\2\2uw\5\30\r\2vt\3\2\2\2wz\3\2"+
		"\2\2xv\3\2\2\2xy\3\2\2\2y\27\3\2\2\2zx\3\2\2\2{~\5\36\20\2|~\5\32\16\2"+
		"}{\3\2\2\2}|\3\2\2\2~\31\3\2\2\2\177\u0080\5\34\17\2\u0080\33\3\2\2\2"+
		"\u0081\u0082\5(\25\2\u0082\u0083\7\27\2\2\u0083\u0084\5(\25\2\u0084\35"+
		"\3\2\2\2\u0085\u0088\5 \21\2\u0086\u0088\5\"\22\2\u0087\u0085\3\2\2\2"+
		"\u0087\u0086\3\2\2\2\u0088\37\3\2\2\2\u0089\u008a\5\62\32\2\u008a\u008b"+
		"\5$\23\2\u008b!\3\2\2\2\u008c\u008d\5\62\32\2\u008d\u008e\7\b\2\2\u008e"+
		"\u008f\5,\27\2\u008f\u0090\7\t\2\2\u0090\u0091\5$\23\2\u0091\u009b\3\2"+
		"\2\2\u0092\u0094\7\n\2\2\u0093\u0092\3\2\2\2\u0093\u0094\3\2\2\2\u0094"+
		"\u0095\3\2\2\2\u0095\u0096\7\b\2\2\u0096\u0097\5,\27\2\u0097\u0098\7\t"+
		"\2\2\u0098\u0099\7\30\2\2\u0099\u009b\3\2\2\2\u009a\u008c\3\2\2\2\u009a"+
		"\u0093\3\2\2\2\u009b#\3\2\2\2\u009c\u00a5\7\5\2\2\u009d\u00a2\5&\24\2"+
		"\u009e\u009f\7\6\2\2\u009f\u00a1\5&\24\2\u00a0\u009e\3\2\2\2\u00a1\u00a4"+
		"\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4"+
		"\u00a2\3\2\2\2\u00a5\u009d\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a7\3\2"+
		"\2\2\u00a7\u00a8\7\7\2\2\u00a8%\3\2\2\2\u00a9\u00ac\5\66\34\2\u00aa\u00ac"+
		"\5(\25\2\u00ab\u00a9\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\'\3\2\2\2\u00ad"+
		"\u00b4\5:\36\2\u00ae\u00b4\5<\37\2\u00af\u00b4\5> \2\u00b0\u00b4\58\35"+
		"\2\u00b1\u00b4\5*\26\2\u00b2\u00b4\5,\27\2\u00b3\u00ad\3\2\2\2\u00b3\u00ae"+
		"\3\2\2\2\u00b3\u00af\3\2\2\2\u00b3\u00b0\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3"+
		"\u00b2\3\2\2\2\u00b4)\3\2\2\2\u00b5\u00b9\5@!\2\u00b6\u00b8\5.\30\2\u00b7"+
		"\u00b6\3\2\2\2\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2"+
		"\2\2\u00ba+\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00c0\5\64\33\2\u00bd\u00bf"+
		"\5.\30\2\u00be\u00bd\3\2\2\2\u00bf\u00c2\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0"+
		"\u00c1\3\2\2\2\u00c1-\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c3\u00c6\5\60\31"+
		"\2\u00c4\u00c6\58\35\2\u00c5\u00c3\3\2\2\2\u00c5\u00c4\3\2\2\2\u00c6/"+
		"\3\2\2\2\u00c7\u00c8\7\13\2\2\u00c8\u00c9\5\64\33\2\u00c9\u00ca\7\f\2"+
		"\2\u00ca\61\3\2\2\2\u00cb\u00cc\7\20\2\2\u00cc\63\3\2\2\2\u00cd\u00ce"+
		"\7\20\2\2\u00ce\65\3\2\2\2\u00cf\u00d0\7\r\2\2\u00d0\67\3\2\2\2\u00d1"+
		"\u00d3\7\13\2\2\u00d2\u00d4\7\22\2\2\u00d3\u00d2\3\2\2\2\u00d4\u00d5\3"+
		"\2\2\2\u00d5\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7"+
		"\u00d9\7\6\2\2\u00d8\u00da\7\22\2\2\u00d9\u00d8\3\2\2\2\u00da\u00db\3"+
		"\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd"+
		"\u00de\7\f\2\2\u00de9\3\2\2\2\u00df\u00e1\7\16\2\2\u00e0\u00df\3\2\2\2"+
		"\u00e0\u00e1\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e3\7\22\2\2\u00e3;\3"+
		"\2\2\2\u00e4\u00e6\7\16\2\2\u00e5\u00e4\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6"+
		"\u00e7\3\2\2\2\u00e7\u00e8\7\23\2\2\u00e8=\3\2\2\2\u00e9\u00ea\7\17\2"+
		"\2\u00ea?\3\2\2\2\u00eb\u00ec\7\21\2\2\u00ecA\3\2\2\2\30ELT^hox}\u0087"+
		"\u0093\u009a\u00a2\u00a5\u00ab\u00b3\u00b9\u00c0\u00c5\u00d5\u00db\u00e0"+
		"\u00e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}