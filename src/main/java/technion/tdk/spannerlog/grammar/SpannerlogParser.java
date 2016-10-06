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
		T__9=10, T__10=11, BooleanLiteral=12, Identifier=13, StringLiteral=14, 
		IntegerLiteral=15, FloatingPointLiteral=16, RigidSeparator=17, SoftSeparator=18, 
		AnnotationSymbol=19, CompareOperator=20, Regex=21, WS=22, Comment=23;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_conjunctiveQuery = 2, RULE_rigidConjunctiveQuery = 3, 
		RULE_softConjunctiveQuery = 4, RULE_conjunctiveQueryHead = 5, RULE_conjunctiveQueryBody = 6, 
		RULE_bodyElement = 7, RULE_condition = 8, RULE_binaryCondition = 9, RULE_atom = 10, 
		RULE_dbAtom = 11, RULE_ieAtom = 12, RULE_termClause = 13, RULE_term = 14, 
		RULE_expr = 15, RULE_stringExpr = 16, RULE_varExpr = 17, RULE_span = 18, 
		RULE_spanVarClause = 19, RULE_relationSchemaName = 20, RULE_variable = 21, 
		RULE_placeHolder = 22, RULE_spanLiteral = 23, RULE_integerLiteral = 24, 
		RULE_floatingPointLiteral = 25, RULE_booleanLiteral = 26, RULE_stringLiteral = 27;
	public static final String[] ruleNames = {
		"program", "statement", "conjunctiveQuery", "rigidConjunctiveQuery", "softConjunctiveQuery", 
		"conjunctiveQueryHead", "conjunctiveQueryBody", "bodyElement", "condition", 
		"binaryCondition", "atom", "dbAtom", "ieAtom", "termClause", "term", "expr", 
		"stringExpr", "varExpr", "span", "spanVarClause", "relationSchemaName", 
		"variable", "placeHolder", "spanLiteral", "integerLiteral", "floatingPointLiteral", 
		"booleanLiteral", "stringLiteral"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'.'", "','", "'<'", "'>'", "'RGX'", "'('", "')'", "'['", "']'", 
		"'_'", "'-'", null, null, null, null, null, "':-'", "':~'", "'@'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"BooleanLiteral", "Identifier", "StringLiteral", "IntegerLiteral", "FloatingPointLiteral", 
		"RigidSeparator", "SoftSeparator", "AnnotationSymbol", "CompareOperator", 
		"Regex", "WS", "Comment"
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
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Identifier) {
				{
				{
				setState(56);
				statement();
				}
				}
				setState(61);
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
			setState(62);
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
			setState(66);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				rigidConjunctiveQuery();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
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
			setState(68);
			conjunctiveQueryHead();
			setState(69);
			match(RigidSeparator);
			setState(70);
			conjunctiveQueryBody();
			setState(71);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			conjunctiveQueryHead();
			setState(74);
			match(SoftSeparator);
			setState(75);
			conjunctiveQueryBody();
			setState(76);
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
		enterRule(_localctx, 10, RULE_conjunctiveQueryHead);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
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
		enterRule(_localctx, 12, RULE_conjunctiveQueryBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			bodyElement();
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(81);
				match(T__1);
				setState(82);
				bodyElement();
				}
				}
				setState(87);
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
		enterRule(_localctx, 14, RULE_bodyElement);
		try {
			setState(90);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(88);
				atom();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
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
		enterRule(_localctx, 16, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
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
		enterRule(_localctx, 18, RULE_binaryCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			expr();
			setState(95);
			match(CompareOperator);
			setState(96);
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
		enterRule(_localctx, 20, RULE_atom);
		try {
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(98);
				dbAtom();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(99);
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
		enterRule(_localctx, 22, RULE_dbAtom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			relationSchemaName();
			setState(103);
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
		enterRule(_localctx, 24, RULE_ieAtom);
		int _la;
		try {
			setState(119);
			switch (_input.LA(1)) {
			case Identifier:
				_localctx = new IEFunctionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(105);
				relationSchemaName();
				setState(106);
				match(T__2);
				setState(107);
				varExpr();
				setState(108);
				match(T__3);
				setState(109);
				termClause();
				}
				break;
			case T__2:
			case T__4:
				_localctx = new RgxContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(112);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(111);
					match(T__4);
					}
				}

				setState(114);
				match(T__2);
				setState(115);
				varExpr();
				setState(116);
				match(T__3);
				setState(117);
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
		enterRule(_localctx, 26, RULE_termClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(T__5);
			setState(130);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << BooleanLiteral) | (1L << Identifier) | (1L << StringLiteral) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral))) != 0)) {
				{
				setState(122);
				term();
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(123);
					match(T__1);
					setState(124);
					term();
					}
					}
					setState(129);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(132);
			match(T__6);
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
		enterRule(_localctx, 28, RULE_term);
		try {
			setState(136);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(134);
				placeHolder();
				}
				break;
			case T__7:
			case T__10:
			case BooleanLiteral:
			case Identifier:
			case StringLiteral:
			case IntegerLiteral:
			case FloatingPointLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(135);
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
		enterRule(_localctx, 30, RULE_expr);
		try {
			setState(144);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				integerLiteral();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				floatingPointLiteral();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(140);
				booleanLiteral();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(141);
				spanLiteral();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(142);
				stringExpr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(143);
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
		enterRule(_localctx, 32, RULE_stringExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			stringLiteral();
			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(147);
				span();
				}
				}
				setState(152);
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
		enterRule(_localctx, 34, RULE_varExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			variable();
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(154);
				span();
				}
				}
				setState(159);
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
		enterRule(_localctx, 36, RULE_span);
		try {
			setState(162);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				spanVarClause();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(161);
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
		enterRule(_localctx, 38, RULE_spanVarClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(T__7);
			setState(165);
			variable();
			setState(166);
			match(T__8);
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
		enterRule(_localctx, 40, RULE_relationSchemaName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
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
		enterRule(_localctx, 42, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
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
		enterRule(_localctx, 44, RULE_placeHolder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
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
		enterRule(_localctx, 46, RULE_spanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(T__7);
			setState(176); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(175);
				match(IntegerLiteral);
				}
				}
				setState(178); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IntegerLiteral );
			setState(180);
			match(T__1);
			setState(182); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(181);
				match(IntegerLiteral);
				}
				}
				setState(184); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IntegerLiteral );
			setState(186);
			match(T__8);
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
		enterRule(_localctx, 48, RULE_integerLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(188);
				match(T__10);
				}
			}

			setState(191);
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
		enterRule(_localctx, 50, RULE_floatingPointLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(193);
				match(T__10);
				}
			}

			setState(196);
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
		enterRule(_localctx, 52, RULE_booleanLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
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
		enterRule(_localctx, 54, RULE_stringLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\31\u00cd\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\7\2<\n\2\f\2\16\2?\13\2\3"+
		"\3\3\3\3\4\3\4\5\4E\n\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\b\7\bV\n\b\f\b\16\bY\13\b\3\t\3\t\5\t]\n\t\3\n\3\n\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\5\fg\n\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\5\16s\n\16\3\16\3\16\3\16\3\16\3\16\5\16z\n\16\3\17\3\17\3\17"+
		"\3\17\7\17\u0080\n\17\f\17\16\17\u0083\13\17\5\17\u0085\n\17\3\17\3\17"+
		"\3\20\3\20\5\20\u008b\n\20\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u0093\n"+
		"\21\3\22\3\22\7\22\u0097\n\22\f\22\16\22\u009a\13\22\3\23\3\23\7\23\u009e"+
		"\n\23\f\23\16\23\u00a1\13\23\3\24\3\24\5\24\u00a5\n\24\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\6\31\u00b3\n\31\r\31\16"+
		"\31\u00b4\3\31\3\31\6\31\u00b9\n\31\r\31\16\31\u00ba\3\31\3\31\3\32\5"+
		"\32\u00c0\n\32\3\32\3\32\3\33\5\33\u00c5\n\33\3\33\3\33\3\34\3\34\3\35"+
		"\3\35\3\35\2\2\36\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62"+
		"\64\668\2\2\u00c6\2=\3\2\2\2\4@\3\2\2\2\6D\3\2\2\2\bF\3\2\2\2\nK\3\2\2"+
		"\2\fP\3\2\2\2\16R\3\2\2\2\20\\\3\2\2\2\22^\3\2\2\2\24`\3\2\2\2\26f\3\2"+
		"\2\2\30h\3\2\2\2\32y\3\2\2\2\34{\3\2\2\2\36\u008a\3\2\2\2 \u0092\3\2\2"+
		"\2\"\u0094\3\2\2\2$\u009b\3\2\2\2&\u00a4\3\2\2\2(\u00a6\3\2\2\2*\u00aa"+
		"\3\2\2\2,\u00ac\3\2\2\2.\u00ae\3\2\2\2\60\u00b0\3\2\2\2\62\u00bf\3\2\2"+
		"\2\64\u00c4\3\2\2\2\66\u00c8\3\2\2\28\u00ca\3\2\2\2:<\5\4\3\2;:\3\2\2"+
		"\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>\3\3\2\2\2?=\3\2\2\2@A\5\6\4\2A\5\3\2"+
		"\2\2BE\5\b\5\2CE\5\n\6\2DB\3\2\2\2DC\3\2\2\2E\7\3\2\2\2FG\5\f\7\2GH\7"+
		"\23\2\2HI\5\16\b\2IJ\7\3\2\2J\t\3\2\2\2KL\5\f\7\2LM\7\24\2\2MN\5\16\b"+
		"\2NO\7\3\2\2O\13\3\2\2\2PQ\5\30\r\2Q\r\3\2\2\2RW\5\20\t\2ST\7\4\2\2TV"+
		"\5\20\t\2US\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2\2X\17\3\2\2\2YW\3\2\2"+
		"\2Z]\5\26\f\2[]\5\22\n\2\\Z\3\2\2\2\\[\3\2\2\2]\21\3\2\2\2^_\5\24\13\2"+
		"_\23\3\2\2\2`a\5 \21\2ab\7\26\2\2bc\5 \21\2c\25\3\2\2\2dg\5\30\r\2eg\5"+
		"\32\16\2fd\3\2\2\2fe\3\2\2\2g\27\3\2\2\2hi\5*\26\2ij\5\34\17\2j\31\3\2"+
		"\2\2kl\5*\26\2lm\7\5\2\2mn\5$\23\2no\7\6\2\2op\5\34\17\2pz\3\2\2\2qs\7"+
		"\7\2\2rq\3\2\2\2rs\3\2\2\2st\3\2\2\2tu\7\5\2\2uv\5$\23\2vw\7\6\2\2wx\7"+
		"\27\2\2xz\3\2\2\2yk\3\2\2\2yr\3\2\2\2z\33\3\2\2\2{\u0084\7\b\2\2|\u0081"+
		"\5\36\20\2}~\7\4\2\2~\u0080\5\36\20\2\177}\3\2\2\2\u0080\u0083\3\2\2\2"+
		"\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081"+
		"\3\2\2\2\u0084|\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0086\3\2\2\2\u0086"+
		"\u0087\7\t\2\2\u0087\35\3\2\2\2\u0088\u008b\5.\30\2\u0089\u008b\5 \21"+
		"\2\u008a\u0088\3\2\2\2\u008a\u0089\3\2\2\2\u008b\37\3\2\2\2\u008c\u0093"+
		"\5\62\32\2\u008d\u0093\5\64\33\2\u008e\u0093\5\66\34\2\u008f\u0093\5\60"+
		"\31\2\u0090\u0093\5\"\22\2\u0091\u0093\5$\23\2\u0092\u008c\3\2\2\2\u0092"+
		"\u008d\3\2\2\2\u0092\u008e\3\2\2\2\u0092\u008f\3\2\2\2\u0092\u0090\3\2"+
		"\2\2\u0092\u0091\3\2\2\2\u0093!\3\2\2\2\u0094\u0098\58\35\2\u0095\u0097"+
		"\5&\24\2\u0096\u0095\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098"+
		"\u0099\3\2\2\2\u0099#\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009f\5,\27\2"+
		"\u009c\u009e\5&\24\2\u009d\u009c\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d"+
		"\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0%\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2"+
		"\u00a5\5(\25\2\u00a3\u00a5\5\60\31\2\u00a4\u00a2\3\2\2\2\u00a4\u00a3\3"+
		"\2\2\2\u00a5\'\3\2\2\2\u00a6\u00a7\7\n\2\2\u00a7\u00a8\5,\27\2\u00a8\u00a9"+
		"\7\13\2\2\u00a9)\3\2\2\2\u00aa\u00ab\7\17\2\2\u00ab+\3\2\2\2\u00ac\u00ad"+
		"\7\17\2\2\u00ad-\3\2\2\2\u00ae\u00af\7\f\2\2\u00af/\3\2\2\2\u00b0\u00b2"+
		"\7\n\2\2\u00b1\u00b3\7\21\2\2\u00b2\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2"+
		"\u00b4\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b8"+
		"\7\4\2\2\u00b7\u00b9\7\21\2\2\u00b8\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2"+
		"\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd"+
		"\7\13\2\2\u00bd\61\3\2\2\2\u00be\u00c0\7\r\2\2\u00bf\u00be\3\2\2\2\u00bf"+
		"\u00c0\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2\7\21\2\2\u00c2\63\3\2\2"+
		"\2\u00c3\u00c5\7\r\2\2\u00c4\u00c3\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c6"+
		"\3\2\2\2\u00c6\u00c7\7\22\2\2\u00c7\65\3\2\2\2\u00c8\u00c9\7\16\2\2\u00c9"+
		"\67\3\2\2\2\u00ca\u00cb\7\20\2\2\u00cb9\3\2\2\2\24=DW\\fry\u0081\u0084"+
		"\u008a\u0092\u0098\u009f\u00a4\u00b4\u00ba\u00bf\u00c4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}