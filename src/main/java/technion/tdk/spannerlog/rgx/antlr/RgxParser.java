// Generated from /home/yoavn/Workspace/Projects/spannerlog/src/main/java/technion/tdk/spannerlog/rgx/antlr/Rgx.g4 by ANTLR 4.5.3
package technion.tdk.spannerlog.rgx.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RgxParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		BeginsWithLetter=10, BeginsWithNonLetter=11, WS=12;
	public static final int
		RULE_regex = 0, RULE_regexSimple = 1, RULE_regexBasic = 2, RULE_regexElementry = 3, 
		RULE_group = 4, RULE_anyChar = 5, RULE_captureClause = 6, RULE_identifier = 7, 
		RULE_chars = 8;
	public static final String[] ruleNames = {
		"regex", "regexSimple", "regexBasic", "regexElementry", "group", "anyChar", 
		"captureClause", "identifier", "chars"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'|'", "'*'", "'+'", "'?'", "'('", "')'", "'.'", "'{'", "'}'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "BeginsWithLetter", 
		"BeginsWithNonLetter", "WS"
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
	public String getGrammarFileName() { return "Rgx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public RgxParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RegexContext extends ParserRuleContext {
		public RegexSimpleContext regexSimple() {
			return getRuleContext(RegexSimpleContext.class,0);
		}
		public RegexContext regex() {
			return getRuleContext(RegexContext.class,0);
		}
		public RegexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_regex; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RgxVisitor ) return ((RgxVisitor<? extends T>)visitor).visitRegex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegexContext regex() throws RecognitionException {
		return regex(0);
	}

	private RegexContext regex(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RegexContext _localctx = new RegexContext(_ctx, _parentState);
		RegexContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_regex, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(19);
				regexSimple(0);
				}
				break;
			case 2:
				{
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(28);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RegexContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_regex);
					setState(23);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(24);
					match(T__0);
					setState(25);
					regexSimple(0);
					}
					} 
				}
				setState(30);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class RegexSimpleContext extends ParserRuleContext {
		public RegexBasicContext regexBasic() {
			return getRuleContext(RegexBasicContext.class,0);
		}
		public RegexSimpleContext regexSimple() {
			return getRuleContext(RegexSimpleContext.class,0);
		}
		public RegexSimpleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_regexSimple; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RgxVisitor ) return ((RgxVisitor<? extends T>)visitor).visitRegexSimple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegexSimpleContext regexSimple() throws RecognitionException {
		return regexSimple(0);
	}

	private RegexSimpleContext regexSimple(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RegexSimpleContext _localctx = new RegexSimpleContext(_ctx, _parentState);
		RegexSimpleContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_regexSimple, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(32);
			regexBasic();
			}
			_ctx.stop = _input.LT(-1);
			setState(38);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RegexSimpleContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_regexSimple);
					setState(34);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(35);
					regexBasic();
					}
					} 
				}
				setState(40);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class RegexBasicContext extends ParserRuleContext {
		public RegexElementryContext regexElementry() {
			return getRuleContext(RegexElementryContext.class,0);
		}
		public RegexBasicContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_regexBasic; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RgxVisitor ) return ((RgxVisitor<? extends T>)visitor).visitRegexBasic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegexBasicContext regexBasic() throws RecognitionException {
		RegexBasicContext _localctx = new RegexBasicContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_regexBasic);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			regexElementry();
			setState(43);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(42);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__3))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
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

	public static class RegexElementryContext extends ParserRuleContext {
		public GroupContext group() {
			return getRuleContext(GroupContext.class,0);
		}
		public AnyCharContext anyChar() {
			return getRuleContext(AnyCharContext.class,0);
		}
		public CaptureClauseContext captureClause() {
			return getRuleContext(CaptureClauseContext.class,0);
		}
		public CharsContext chars() {
			return getRuleContext(CharsContext.class,0);
		}
		public RegexElementryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_regexElementry; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RgxVisitor ) return ((RgxVisitor<? extends T>)visitor).visitRegexElementry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegexElementryContext regexElementry() throws RecognitionException {
		RegexElementryContext _localctx = new RegexElementryContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_regexElementry);
		try {
			setState(49);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				group();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
				anyChar();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(47);
				captureClause();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(48);
				chars();
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

	public static class GroupContext extends ParserRuleContext {
		public RegexContext regex() {
			return getRuleContext(RegexContext.class,0);
		}
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RgxVisitor ) return ((RgxVisitor<? extends T>)visitor).visitGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_group);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(T__4);
			setState(52);
			regex(0);
			setState(53);
			match(T__5);
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

	public static class AnyCharContext extends ParserRuleContext {
		public AnyCharContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anyChar; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RgxVisitor ) return ((RgxVisitor<? extends T>)visitor).visitAnyChar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnyCharContext anyChar() throws RecognitionException {
		AnyCharContext _localctx = new AnyCharContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_anyChar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
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

	public static class CaptureClauseContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public RegexContext regex() {
			return getRuleContext(RegexContext.class,0);
		}
		public CaptureClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_captureClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RgxVisitor ) return ((RgxVisitor<? extends T>)visitor).visitCaptureClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaptureClauseContext captureClause() throws RecognitionException {
		CaptureClauseContext _localctx = new CaptureClauseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_captureClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			identifier();
			setState(58);
			match(T__7);
			setState(59);
			regex(0);
			setState(60);
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

	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode BeginsWithLetter() { return getToken(RgxParser.BeginsWithLetter, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RgxVisitor ) return ((RgxVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(BeginsWithLetter);
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

	public static class CharsContext extends ParserRuleContext {
		public TerminalNode BeginsWithNonLetter() { return getToken(RgxParser.BeginsWithNonLetter, 0); }
		public TerminalNode BeginsWithLetter() { return getToken(RgxParser.BeginsWithLetter, 0); }
		public CharsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chars; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RgxVisitor ) return ((RgxVisitor<? extends T>)visitor).visitChars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CharsContext chars() throws RecognitionException {
		CharsContext _localctx = new CharsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_chars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			_la = _input.LA(1);
			if ( !(_la==BeginsWithLetter || _la==BeginsWithNonLetter) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return regex_sempred((RegexContext)_localctx, predIndex);
		case 1:
			return regexSimple_sempred((RegexSimpleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean regex_sempred(RegexContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean regexSimple_sempred(RegexSimpleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\16E\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\2"+
		"\5\2\30\n\2\3\2\3\2\3\2\7\2\35\n\2\f\2\16\2 \13\2\3\3\3\3\3\3\3\3\3\3"+
		"\7\3\'\n\3\f\3\16\3*\13\3\3\4\3\4\5\4.\n\4\3\5\3\5\3\5\3\5\5\5\64\n\5"+
		"\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\2\4\2"+
		"\4\13\2\4\6\b\n\f\16\20\22\2\4\3\2\4\6\3\2\f\rB\2\27\3\2\2\2\4!\3\2\2"+
		"\2\6+\3\2\2\2\b\63\3\2\2\2\n\65\3\2\2\2\f9\3\2\2\2\16;\3\2\2\2\20@\3\2"+
		"\2\2\22B\3\2\2\2\24\25\b\2\1\2\25\30\5\4\3\2\26\30\3\2\2\2\27\24\3\2\2"+
		"\2\27\26\3\2\2\2\30\36\3\2\2\2\31\32\f\5\2\2\32\33\7\3\2\2\33\35\5\4\3"+
		"\2\34\31\3\2\2\2\35 \3\2\2\2\36\34\3\2\2\2\36\37\3\2\2\2\37\3\3\2\2\2"+
		" \36\3\2\2\2!\"\b\3\1\2\"#\5\6\4\2#(\3\2\2\2$%\f\4\2\2%\'\5\6\4\2&$\3"+
		"\2\2\2\'*\3\2\2\2(&\3\2\2\2()\3\2\2\2)\5\3\2\2\2*(\3\2\2\2+-\5\b\5\2,"+
		".\t\2\2\2-,\3\2\2\2-.\3\2\2\2.\7\3\2\2\2/\64\5\n\6\2\60\64\5\f\7\2\61"+
		"\64\5\16\b\2\62\64\5\22\n\2\63/\3\2\2\2\63\60\3\2\2\2\63\61\3\2\2\2\63"+
		"\62\3\2\2\2\64\t\3\2\2\2\65\66\7\7\2\2\66\67\5\2\2\2\678\7\b\2\28\13\3"+
		"\2\2\29:\7\t\2\2:\r\3\2\2\2;<\5\20\t\2<=\7\n\2\2=>\5\2\2\2>?\7\13\2\2"+
		"?\17\3\2\2\2@A\7\f\2\2A\21\3\2\2\2BC\t\3\2\2C\23\3\2\2\2\7\27\36(-\63";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}