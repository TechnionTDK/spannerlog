// Generated from /home/yoavn/Workspace/Projects/spannerlog/src/main/java/technion/tdk/spannerlog/grammar/Spannerlog.g4 by ANTLR 4.5.3
package technion.tdk.spannerlog.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SpannerlogLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, BooleanLiteral=13, Identifier=14, StringLiteral=15, 
		IntegerLiteral=16, FloatingPointLiteral=17, RigidSeparator=18, SoftSeparator=19, 
		AnnotationSymbol=20, CompareOperator=21, Regex=22, WS=23, Comment=24;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "BooleanLiteral", "Identifier", "StringLiteral", 
		"IntegerLiteral", "FloatingPointLiteral", "RigidSeparator", "SoftSeparator", 
		"AnnotationSymbol", "CompareOperator", "Regex", "WS", "Comment", "Letter", 
		"LetterOrDigit", "RegexElememt", "StringElement", "CharEscapeSeq", "DecimalNumeral", 
		"Digit", "NonZeroDigit"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\32\u00d1\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3"+
		"\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16l\n\16\3\17\3\17\7\17p\n"+
		"\17\f\17\16\17s\13\17\3\20\3\20\7\20w\n\20\f\20\16\20z\13\20\3\20\3\20"+
		"\3\21\3\21\3\22\6\22\u0081\n\22\r\22\16\22\u0082\3\22\3\22\6\22\u0087"+
		"\n\22\r\22\16\22\u0088\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26\3"+
		"\26\3\26\5\26\u0096\n\26\3\27\3\27\3\27\3\27\7\27\u009c\n\27\f\27\16\27"+
		"\u009f\13\27\3\27\3\27\3\27\3\30\6\30\u00a5\n\30\r\30\16\30\u00a6\3\30"+
		"\3\30\3\31\3\31\7\31\u00ad\n\31\f\31\16\31\u00b0\13\31\3\31\3\31\3\32"+
		"\3\32\3\33\3\33\3\34\5\34\u00b9\n\34\3\35\3\35\5\35\u00bd\n\35\3\36\3"+
		"\36\3\36\3\37\3\37\3\37\7\37\u00c5\n\37\f\37\16\37\u00c8\13\37\5\37\u00ca"+
		"\n\37\3 \3 \5 \u00ce\n \3!\3!\2\2\"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30"+
		"/\31\61\32\63\2\65\2\67\29\2;\2=\2?\2A\2\3\2\t\5\2\13\f\17\17\"\"\4\2"+
		"\f\f\17\17\4\2C\\c|\7\2&&\62;C\\aac|\n\2\13\f\17\17\"\",-\62;C\\c}\177"+
		"\177\4\2\"#%\u0081\n\2$$))^^ddhhppttvv\u00d5\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\3C\3\2\2\2"+
		"\5E\3\2\2\2\7L\3\2\2\2\tN\3\2\2\2\13P\3\2\2\2\rR\3\2\2\2\17T\3\2\2\2\21"+
		"V\3\2\2\2\23Z\3\2\2\2\25\\\3\2\2\2\27^\3\2\2\2\31`\3\2\2\2\33k\3\2\2\2"+
		"\35m\3\2\2\2\37t\3\2\2\2!}\3\2\2\2#\u0080\3\2\2\2%\u008a\3\2\2\2\'\u008d"+
		"\3\2\2\2)\u0090\3\2\2\2+\u0095\3\2\2\2-\u0097\3\2\2\2/\u00a4\3\2\2\2\61"+
		"\u00aa\3\2\2\2\63\u00b3\3\2\2\2\65\u00b5\3\2\2\2\67\u00b8\3\2\2\29\u00bc"+
		"\3\2\2\2;\u00be\3\2\2\2=\u00c9\3\2\2\2?\u00cd\3\2\2\2A\u00cf\3\2\2\2C"+
		"D\7\60\2\2D\4\3\2\2\2EF\7y\2\2FG\7g\2\2GH\7k\2\2HI\7i\2\2IJ\7j\2\2JK\7"+
		"v\2\2K\6\3\2\2\2LM\7*\2\2M\b\3\2\2\2NO\7.\2\2O\n\3\2\2\2PQ\7+\2\2Q\f\3"+
		"\2\2\2RS\7>\2\2S\16\3\2\2\2TU\7@\2\2U\20\3\2\2\2VW\7T\2\2WX\7I\2\2XY\7"+
		"Z\2\2Y\22\3\2\2\2Z[\7]\2\2[\24\3\2\2\2\\]\7_\2\2]\26\3\2\2\2^_\7a\2\2"+
		"_\30\3\2\2\2`a\7/\2\2a\32\3\2\2\2bc\7V\2\2cd\7t\2\2de\7w\2\2el\7g\2\2"+
		"fg\7H\2\2gh\7c\2\2hi\7n\2\2ij\7u\2\2jl\7g\2\2kb\3\2\2\2kf\3\2\2\2l\34"+
		"\3\2\2\2mq\5\63\32\2np\5\65\33\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2\2"+
		"\2r\36\3\2\2\2sq\3\2\2\2tx\7$\2\2uw\59\35\2vu\3\2\2\2wz\3\2\2\2xv\3\2"+
		"\2\2xy\3\2\2\2y{\3\2\2\2zx\3\2\2\2{|\7$\2\2| \3\2\2\2}~\5=\37\2~\"\3\2"+
		"\2\2\177\u0081\5? \2\u0080\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0080"+
		"\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0086\7\60\2\2"+
		"\u0085\u0087\5? \2\u0086\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0086"+
		"\3\2\2\2\u0088\u0089\3\2\2\2\u0089$\3\2\2\2\u008a\u008b\7<\2\2\u008b\u008c"+
		"\7/\2\2\u008c&\3\2\2\2\u008d\u008e\7<\2\2\u008e\u008f\7\u0080\2\2\u008f"+
		"(\3\2\2\2\u0090\u0091\7B\2\2\u0091*\3\2\2\2\u0092\u0096\7?\2\2\u0093\u0094"+
		"\7#\2\2\u0094\u0096\7?\2\2\u0095\u0092\3\2\2\2\u0095\u0093\3\2\2\2\u0096"+
		",\3\2\2\2\u0097\u0098\7^\2\2\u0098\u0099\7]\2\2\u0099\u009d\3\2\2\2\u009a"+
		"\u009c\5\67\34\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3"+
		"\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0"+
		"\u00a1\7_\2\2\u00a1\u00a2\7^\2\2\u00a2.\3\2\2\2\u00a3\u00a5\t\2\2\2\u00a4"+
		"\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2"+
		"\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\b\30\2\2\u00a9\60\3\2\2\2\u00aa\u00ae"+
		"\7%\2\2\u00ab\u00ad\n\3\2\2\u00ac\u00ab\3\2\2\2\u00ad\u00b0\3\2\2\2\u00ae"+
		"\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b1\3\2\2\2\u00b0\u00ae\3\2"+
		"\2\2\u00b1\u00b2\b\31\2\2\u00b2\62\3\2\2\2\u00b3\u00b4\t\4\2\2\u00b4\64"+
		"\3\2\2\2\u00b5\u00b6\t\5\2\2\u00b6\66\3\2\2\2\u00b7\u00b9\t\6\2\2\u00b8"+
		"\u00b7\3\2\2\2\u00b98\3\2\2\2\u00ba\u00bd\t\7\2\2\u00bb\u00bd\5;\36\2"+
		"\u00bc\u00ba\3\2\2\2\u00bc\u00bb\3\2\2\2\u00bd:\3\2\2\2\u00be\u00bf\7"+
		"^\2\2\u00bf\u00c0\t\b\2\2\u00c0<\3\2\2\2\u00c1\u00ca\7\62\2\2\u00c2\u00c6"+
		"\5A!\2\u00c3\u00c5\5? \2\u00c4\u00c3\3\2\2\2\u00c5\u00c8\3\2\2\2\u00c6"+
		"\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00ca\3\2\2\2\u00c8\u00c6\3\2"+
		"\2\2\u00c9\u00c1\3\2\2\2\u00c9\u00c2\3\2\2\2\u00ca>\3\2\2\2\u00cb\u00ce"+
		"\7\62\2\2\u00cc\u00ce\5A!\2\u00cd\u00cb\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ce"+
		"@\3\2\2\2\u00cf\u00d0\4\63;\2\u00d0B\3\2\2\2\21\2kqx\u0082\u0088\u0095"+
		"\u009d\u00a6\u00ae\u00b8\u00bc\u00c6\u00c9\u00cd\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}