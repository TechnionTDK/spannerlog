// Generated from /home/yoavn/Workspace/Projects/spannerlog/src/main/java/technion/tdk/spannerlog/antlr/main/Spannerlog.g4 by ANTLR 4.5.3
package technion.tdk.spannerlog.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SpannerlogParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, BooleanLiteral=12, Identifier=13, StringLiteral=14, 
		IntegerLiteral=15, FloatingPointLiteral=16, Separator=17, Regex=18, WS=19;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_conjunctiveQuery = 2, RULE_conjunctiveQueryHead = 3, 
		RULE_conjunctiveQueryBody = 4, RULE_atom = 5, RULE_dbAtom = 6, RULE_ieAtom = 7, 
		RULE_termClause = 8, RULE_term = 9, RULE_expr = 10, RULE_span = 11, RULE_spanVarClause = 12, 
		RULE_relationSchemaName = 13, RULE_variable = 14, RULE_placeHolder = 15, 
		RULE_spanLiteral = 16, RULE_integerLiteral = 17, RULE_floatingPointLiteral = 18, 
		RULE_booleanLiteral = 19, RULE_stringLiteral = 20;
	public static final String[] ruleNames = {
		"program", "statement", "conjunctiveQuery", "conjunctiveQueryHead", "conjunctiveQueryBody", 
		"atom", "dbAtom", "ieAtom", "termClause", "term", "expr", "span", "spanVarClause", 
		"relationSchemaName", "variable", "placeHolder", "spanLiteral", "integerLiteral", 
		"floatingPointLiteral", "booleanLiteral", "stringLiteral"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'.'", "','", "'<'", "'>'", "'RGX'", "'('", "')'", "'['", "']'", 
		"'_'", "'-'", null, null, null, null, null, "':-'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"BooleanLiteral", "Identifier", "StringLiteral", "IntegerLiteral", "FloatingPointLiteral", 
		"Separator", "Regex", "WS"
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
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Identifier) {
				{
				{
				setState(42);
				statement();
				}
				}
				setState(47);
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
			setState(48);
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
		public ConjunctiveQueryHeadContext conjunctiveQueryHead() {
			return getRuleContext(ConjunctiveQueryHeadContext.class,0);
		}
		public TerminalNode Separator() { return getToken(SpannerlogParser.Separator, 0); }
		public ConjunctiveQueryBodyContext conjunctiveQueryBody() {
			return getRuleContext(ConjunctiveQueryBodyContext.class,0);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			conjunctiveQueryHead();
			setState(51);
			match(Separator);
			setState(52);
			conjunctiveQueryBody();
			setState(53);
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
		enterRule(_localctx, 6, RULE_conjunctiveQueryHead);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
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
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
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
		enterRule(_localctx, 8, RULE_conjunctiveQueryBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			atom();
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(58);
				match(T__1);
				setState(59);
				atom();
				}
				}
				setState(64);
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
		enterRule(_localctx, 10, RULE_atom);
		try {
			setState(67);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(65);
				dbAtom();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(66);
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
		enterRule(_localctx, 12, RULE_dbAtom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			relationSchemaName();
			setState(70);
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
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
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
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
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
		enterRule(_localctx, 14, RULE_ieAtom);
		int _la;
		try {
			setState(86);
			switch (_input.LA(1)) {
			case Identifier:
				_localctx = new IEFunctionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				relationSchemaName();
				setState(73);
				match(T__2);
				setState(74);
				term();
				setState(75);
				match(T__3);
				setState(76);
				termClause();
				}
				break;
			case T__2:
			case T__4:
				_localctx = new RgxContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(78);
					match(T__4);
					}
				}

				setState(81);
				match(T__2);
				setState(82);
				term();
				setState(83);
				match(T__3);
				setState(84);
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
		enterRule(_localctx, 16, RULE_termClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(T__5);
			setState(97);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << BooleanLiteral) | (1L << Identifier) | (1L << StringLiteral) | (1L << IntegerLiteral) | (1L << FloatingPointLiteral))) != 0)) {
				{
				setState(89);
				term();
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(90);
					match(T__1);
					setState(91);
					term();
					}
					}
					setState(96);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(99);
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
		enterRule(_localctx, 18, RULE_term);
		try {
			setState(103);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
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
				setState(102);
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
		public StringLiteralContext stringLiteral() {
			return getRuleContext(StringLiteralContext.class,0);
		}
		public List<SpanContext> span() {
			return getRuleContexts(SpanContext.class);
		}
		public SpanContext span(int i) {
			return getRuleContext(SpanContext.class,i);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
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
		enterRule(_localctx, 20, RULE_expr);
		int _la;
		try {
			setState(123);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(105);
				integerLiteral();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(106);
				floatingPointLiteral();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(107);
				booleanLiteral();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(108);
				spanLiteral();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(109);
				stringLiteral();
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(110);
					span();
					}
					}
					setState(115);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(116);
				variable();
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(117);
					span();
					}
					}
					setState(122);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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
		enterRule(_localctx, 22, RULE_span);
		try {
			setState(127);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(125);
				spanVarClause();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
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
		enterRule(_localctx, 24, RULE_spanVarClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(T__7);
			setState(130);
			variable();
			setState(131);
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
		enterRule(_localctx, 26, RULE_relationSchemaName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
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
		enterRule(_localctx, 28, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
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
		enterRule(_localctx, 30, RULE_placeHolder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
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
		enterRule(_localctx, 32, RULE_spanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(T__7);
			setState(141); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(140);
				match(IntegerLiteral);
				}
				}
				setState(143); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IntegerLiteral );
			setState(145);
			match(T__1);
			setState(147); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(146);
				match(IntegerLiteral);
				}
				}
				setState(149); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IntegerLiteral );
			setState(151);
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
		enterRule(_localctx, 34, RULE_integerLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(153);
				match(T__10);
				}
			}

			setState(156);
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
		enterRule(_localctx, 36, RULE_floatingPointLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(158);
				match(T__10);
				}
			}

			setState(161);
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
		enterRule(_localctx, 38, RULE_booleanLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
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
		enterRule(_localctx, 40, RULE_stringLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\25\u00aa\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\7\2.\n\2\f\2\16\2\61\13\2"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\7\6?\n\6\f\6\16\6B\13"+
		"\6\3\7\3\7\5\7F\n\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tR\n\t\3"+
		"\t\3\t\3\t\3\t\3\t\5\tY\n\t\3\n\3\n\3\n\3\n\7\n_\n\n\f\n\16\nb\13\n\5"+
		"\nd\n\n\3\n\3\n\3\13\3\13\5\13j\n\13\3\f\3\f\3\f\3\f\3\f\3\f\7\fr\n\f"+
		"\f\f\16\fu\13\f\3\f\3\f\7\fy\n\f\f\f\16\f|\13\f\5\f~\n\f\3\r\3\r\5\r\u0082"+
		"\n\r\3\16\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\6\22"+
		"\u0090\n\22\r\22\16\22\u0091\3\22\3\22\6\22\u0096\n\22\r\22\16\22\u0097"+
		"\3\22\3\22\3\23\5\23\u009d\n\23\3\23\3\23\3\24\5\24\u00a2\n\24\3\24\3"+
		"\24\3\25\3\25\3\26\3\26\3\26\2\2\27\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$&(*\2\2\u00a8\2/\3\2\2\2\4\62\3\2\2\2\6\64\3\2\2\2\b9\3\2\2\2\n"+
		";\3\2\2\2\fE\3\2\2\2\16G\3\2\2\2\20X\3\2\2\2\22Z\3\2\2\2\24i\3\2\2\2\26"+
		"}\3\2\2\2\30\u0081\3\2\2\2\32\u0083\3\2\2\2\34\u0087\3\2\2\2\36\u0089"+
		"\3\2\2\2 \u008b\3\2\2\2\"\u008d\3\2\2\2$\u009c\3\2\2\2&\u00a1\3\2\2\2"+
		"(\u00a5\3\2\2\2*\u00a7\3\2\2\2,.\5\4\3\2-,\3\2\2\2.\61\3\2\2\2/-\3\2\2"+
		"\2/\60\3\2\2\2\60\3\3\2\2\2\61/\3\2\2\2\62\63\5\6\4\2\63\5\3\2\2\2\64"+
		"\65\5\b\5\2\65\66\7\23\2\2\66\67\5\n\6\2\678\7\3\2\28\7\3\2\2\29:\5\16"+
		"\b\2:\t\3\2\2\2;@\5\f\7\2<=\7\4\2\2=?\5\f\7\2><\3\2\2\2?B\3\2\2\2@>\3"+
		"\2\2\2@A\3\2\2\2A\13\3\2\2\2B@\3\2\2\2CF\5\16\b\2DF\5\20\t\2EC\3\2\2\2"+
		"ED\3\2\2\2F\r\3\2\2\2GH\5\34\17\2HI\5\22\n\2I\17\3\2\2\2JK\5\34\17\2K"+
		"L\7\5\2\2LM\5\24\13\2MN\7\6\2\2NO\5\22\n\2OY\3\2\2\2PR\7\7\2\2QP\3\2\2"+
		"\2QR\3\2\2\2RS\3\2\2\2ST\7\5\2\2TU\5\24\13\2UV\7\6\2\2VW\7\24\2\2WY\3"+
		"\2\2\2XJ\3\2\2\2XQ\3\2\2\2Y\21\3\2\2\2Zc\7\b\2\2[`\5\24\13\2\\]\7\4\2"+
		"\2]_\5\24\13\2^\\\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2ad\3\2\2\2b`\3"+
		"\2\2\2c[\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\7\t\2\2f\23\3\2\2\2gj\5 \21\2h"+
		"j\5\26\f\2ig\3\2\2\2ih\3\2\2\2j\25\3\2\2\2k~\5$\23\2l~\5&\24\2m~\5(\25"+
		"\2n~\5\"\22\2os\5*\26\2pr\5\30\r\2qp\3\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2"+
		"\2\2t~\3\2\2\2us\3\2\2\2vz\5\36\20\2wy\5\30\r\2xw\3\2\2\2y|\3\2\2\2zx"+
		"\3\2\2\2z{\3\2\2\2{~\3\2\2\2|z\3\2\2\2}k\3\2\2\2}l\3\2\2\2}m\3\2\2\2}"+
		"n\3\2\2\2}o\3\2\2\2}v\3\2\2\2~\27\3\2\2\2\177\u0082\5\32\16\2\u0080\u0082"+
		"\5\"\22\2\u0081\177\3\2\2\2\u0081\u0080\3\2\2\2\u0082\31\3\2\2\2\u0083"+
		"\u0084\7\n\2\2\u0084\u0085\5\36\20\2\u0085\u0086\7\13\2\2\u0086\33\3\2"+
		"\2\2\u0087\u0088\7\17\2\2\u0088\35\3\2\2\2\u0089\u008a\7\17\2\2\u008a"+
		"\37\3\2\2\2\u008b\u008c\7\f\2\2\u008c!\3\2\2\2\u008d\u008f\7\n\2\2\u008e"+
		"\u0090\7\21\2\2\u008f\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u008f\3"+
		"\2\2\2\u0091\u0092\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0095\7\4\2\2\u0094"+
		"\u0096\7\21\2\2\u0095\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0095\3"+
		"\2\2\2\u0097\u0098\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a\7\13\2\2\u009a"+
		"#\3\2\2\2\u009b\u009d\7\r\2\2\u009c\u009b\3\2\2\2\u009c\u009d\3\2\2\2"+
		"\u009d\u009e\3\2\2\2\u009e\u009f\7\21\2\2\u009f%\3\2\2\2\u00a0\u00a2\7"+
		"\r\2\2\u00a1\u00a0\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3"+
		"\u00a4\7\22\2\2\u00a4\'\3\2\2\2\u00a5\u00a6\7\16\2\2\u00a6)\3\2\2\2\u00a7"+
		"\u00a8\7\20\2\2\u00a8+\3\2\2\2\22/@EQX`cisz}\u0081\u0091\u0097\u009c\u00a1";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}