// Generated from /home/yoavn/Workspace/Projects/spannerlog/src/main/java/technion/tdk/spannerlog/antlr/main/Spannerlog.g4 by ANTLR 4.5.3
package technion.tdk.spannerlog.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SpannerlogLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, BooleanLiteral=12, Identifier=13, StringLiteral=14, 
		IntegerLiteral=15, FloatingPointLiteral=16, Separator=17, Regex=18, WS=19;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "BooleanLiteral", "Identifier", "StringLiteral", "IntegerLiteral", 
		"FloatingPointLiteral", "Separator", "Regex", "WS", "Letter", "LetterOrDigit", 
		"RegexElememt", "StringElement", "CharEscapeSeq", "DecimalNumeral", "Digit", 
		"NonZeroDigit"
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


	public SpannerlogLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Spannerlog.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\25\u00ad\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r[\n\r\3\16\3\16\7\16_\n\16\f\16\16\16"+
		"b\13\16\3\17\3\17\7\17f\n\17\f\17\16\17i\13\17\3\17\3\17\3\20\3\20\3\21"+
		"\6\21p\n\21\r\21\16\21q\3\21\3\21\6\21v\n\21\r\21\16\21w\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\23\7\23\u0081\n\23\f\23\16\23\u0084\13\23\3\23\3\23"+
		"\3\23\3\24\6\24\u008a\n\24\r\24\16\24\u008b\3\24\3\24\3\25\3\25\3\26\3"+
		"\26\3\27\5\27\u0095\n\27\3\30\3\30\5\30\u0099\n\30\3\31\3\31\3\31\3\32"+
		"\3\32\3\32\7\32\u00a1\n\32\f\32\16\32\u00a4\13\32\5\32\u00a6\n\32\3\33"+
		"\3\33\5\33\u00aa\n\33\3\34\3\34\2\2\35\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21"+
		"\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\2+\2-\2/"+
		"\2\61\2\63\2\65\2\67\2\3\2\b\5\2\13\f\17\17\"\"\4\2C\\c|\5\2\62;C\\c|"+
		"\n\2\13\f\17\17\"\",-\62;C\\c}\177\177\4\2\"#%\u0081\n\2$$))^^ddhhppt"+
		"tvv\u00af\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\39\3\2\2\2\5;\3\2\2\2\7=\3\2\2\2"+
		"\t?\3\2\2\2\13A\3\2\2\2\rE\3\2\2\2\17G\3\2\2\2\21I\3\2\2\2\23K\3\2\2\2"+
		"\25M\3\2\2\2\27O\3\2\2\2\31Z\3\2\2\2\33\\\3\2\2\2\35c\3\2\2\2\37l\3\2"+
		"\2\2!o\3\2\2\2#y\3\2\2\2%|\3\2\2\2\'\u0089\3\2\2\2)\u008f\3\2\2\2+\u0091"+
		"\3\2\2\2-\u0094\3\2\2\2/\u0098\3\2\2\2\61\u009a\3\2\2\2\63\u00a5\3\2\2"+
		"\2\65\u00a9\3\2\2\2\67\u00ab\3\2\2\29:\7\60\2\2:\4\3\2\2\2;<\7.\2\2<\6"+
		"\3\2\2\2=>\7>\2\2>\b\3\2\2\2?@\7@\2\2@\n\3\2\2\2AB\7T\2\2BC\7I\2\2CD\7"+
		"Z\2\2D\f\3\2\2\2EF\7*\2\2F\16\3\2\2\2GH\7+\2\2H\20\3\2\2\2IJ\7]\2\2J\22"+
		"\3\2\2\2KL\7_\2\2L\24\3\2\2\2MN\7a\2\2N\26\3\2\2\2OP\7/\2\2P\30\3\2\2"+
		"\2QR\7V\2\2RS\7t\2\2ST\7w\2\2T[\7g\2\2UV\7H\2\2VW\7c\2\2WX\7n\2\2XY\7"+
		"u\2\2Y[\7g\2\2ZQ\3\2\2\2ZU\3\2\2\2[\32\3\2\2\2\\`\5)\25\2]_\5+\26\2^]"+
		"\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2a\34\3\2\2\2b`\3\2\2\2cg\7$\2\2"+
		"df\5/\30\2ed\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2hj\3\2\2\2ig\3\2\2\2"+
		"jk\7$\2\2k\36\3\2\2\2lm\5\63\32\2m \3\2\2\2np\5\65\33\2on\3\2\2\2pq\3"+
		"\2\2\2qo\3\2\2\2qr\3\2\2\2rs\3\2\2\2su\7\60\2\2tv\5\65\33\2ut\3\2\2\2"+
		"vw\3\2\2\2wu\3\2\2\2wx\3\2\2\2x\"\3\2\2\2yz\7<\2\2z{\7/\2\2{$\3\2\2\2"+
		"|}\7^\2\2}~\7]\2\2~\u0082\3\2\2\2\177\u0081\5-\27\2\u0080\177\3\2\2\2"+
		"\u0081\u0084\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0085"+
		"\3\2\2\2\u0084\u0082\3\2\2\2\u0085\u0086\7_\2\2\u0086\u0087\7^\2\2\u0087"+
		"&\3\2\2\2\u0088\u008a\t\2\2\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2"+
		"\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e"+
		"\b\24\2\2\u008e(\3\2\2\2\u008f\u0090\t\3\2\2\u0090*\3\2\2\2\u0091\u0092"+
		"\t\4\2\2\u0092,\3\2\2\2\u0093\u0095\t\5\2\2\u0094\u0093\3\2\2\2\u0095"+
		".\3\2\2\2\u0096\u0099\t\6\2\2\u0097\u0099\5\61\31\2\u0098\u0096\3\2\2"+
		"\2\u0098\u0097\3\2\2\2\u0099\60\3\2\2\2\u009a\u009b\7^\2\2\u009b\u009c"+
		"\t\7\2\2\u009c\62\3\2\2\2\u009d\u00a6\7\62\2\2\u009e\u00a2\5\67\34\2\u009f"+
		"\u00a1\5\65\33\2\u00a0\u009f\3\2\2\2\u00a1\u00a4\3\2\2\2\u00a2\u00a0\3"+
		"\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5"+
		"\u009d\3\2\2\2\u00a5\u009e\3\2\2\2\u00a6\64\3\2\2\2\u00a7\u00aa\7\62\2"+
		"\2\u00a8\u00aa\5\67\34\2\u00a9\u00a7\3\2\2\2\u00a9\u00a8\3\2\2\2\u00aa"+
		"\66\3\2\2\2\u00ab\u00ac\4\63;\2\u00ac8\3\2\2\2\17\2Z`gqw\u0082\u008b\u0094"+
		"\u0098\u00a2\u00a5\u00a9\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}