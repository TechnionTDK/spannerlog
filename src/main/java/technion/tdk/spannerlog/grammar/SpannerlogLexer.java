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
		T__9=10, T__10=11, BooleanLiteral=12, Identifier=13, StringLiteral=14, 
		IntegerLiteral=15, FloatingPointLiteral=16, Separator=17, Regex=18, WS=19, 
		Comment=20;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "BooleanLiteral", "Identifier", "StringLiteral", "IntegerLiteral", 
		"FloatingPointLiteral", "Separator", "Regex", "WS", "Comment", "Letter", 
		"LetterOrDigit", "RegexElememt", "StringElement", "CharEscapeSeq", "DecimalNumeral", 
		"Digit", "NonZeroDigit"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'.'", "','", "'<'", "'>'", "'RGX'", "'('", "')'", "'['", "']'", 
		"'_'", "'-'", null, null, null, null, null, "':-'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"BooleanLiteral", "Identifier", "StringLiteral", "IntegerLiteral", "FloatingPointLiteral", 
		"Separator", "Regex", "WS", "Comment"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\26\u00b8\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r]\n\r\3\16\3\16\7\16a\n\16"+
		"\f\16\16\16d\13\16\3\17\3\17\7\17h\n\17\f\17\16\17k\13\17\3\17\3\17\3"+
		"\20\3\20\3\21\6\21r\n\21\r\21\16\21s\3\21\3\21\6\21x\n\21\r\21\16\21y"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\23\7\23\u0083\n\23\f\23\16\23\u0086\13"+
		"\23\3\23\3\23\3\23\3\24\6\24\u008c\n\24\r\24\16\24\u008d\3\24\3\24\3\25"+
		"\3\25\7\25\u0094\n\25\f\25\16\25\u0097\13\25\3\25\3\25\3\26\3\26\3\27"+
		"\3\27\3\30\5\30\u00a0\n\30\3\31\3\31\5\31\u00a4\n\31\3\32\3\32\3\32\3"+
		"\33\3\33\3\33\7\33\u00ac\n\33\f\33\16\33\u00af\13\33\5\33\u00b1\n\33\3"+
		"\34\3\34\5\34\u00b5\n\34\3\35\3\35\2\2\36\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"+
		"\2-\2/\2\61\2\63\2\65\2\67\29\2\3\2\t\5\2\13\f\17\17\"\"\4\2\f\f\17\17"+
		"\4\2C\\c|\7\2&&\62;C\\aac|\n\2\13\f\17\17\"\",-\62;C\\c}\177\177\4\2\""+
		"#%\u0081\n\2$$))^^ddhhppttvv\u00bb\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\3;\3\2\2\2\5=\3\2\2\2\7?\3\2\2\2\tA\3\2\2\2\13C\3\2\2\2\rG\3\2\2\2"+
		"\17I\3\2\2\2\21K\3\2\2\2\23M\3\2\2\2\25O\3\2\2\2\27Q\3\2\2\2\31\\\3\2"+
		"\2\2\33^\3\2\2\2\35e\3\2\2\2\37n\3\2\2\2!q\3\2\2\2#{\3\2\2\2%~\3\2\2\2"+
		"\'\u008b\3\2\2\2)\u0091\3\2\2\2+\u009a\3\2\2\2-\u009c\3\2\2\2/\u009f\3"+
		"\2\2\2\61\u00a3\3\2\2\2\63\u00a5\3\2\2\2\65\u00b0\3\2\2\2\67\u00b4\3\2"+
		"\2\29\u00b6\3\2\2\2;<\7\60\2\2<\4\3\2\2\2=>\7.\2\2>\6\3\2\2\2?@\7>\2\2"+
		"@\b\3\2\2\2AB\7@\2\2B\n\3\2\2\2CD\7T\2\2DE\7I\2\2EF\7Z\2\2F\f\3\2\2\2"+
		"GH\7*\2\2H\16\3\2\2\2IJ\7+\2\2J\20\3\2\2\2KL\7]\2\2L\22\3\2\2\2MN\7_\2"+
		"\2N\24\3\2\2\2OP\7a\2\2P\26\3\2\2\2QR\7/\2\2R\30\3\2\2\2ST\7V\2\2TU\7"+
		"t\2\2UV\7w\2\2V]\7g\2\2WX\7H\2\2XY\7c\2\2YZ\7n\2\2Z[\7u\2\2[]\7g\2\2\\"+
		"S\3\2\2\2\\W\3\2\2\2]\32\3\2\2\2^b\5+\26\2_a\5-\27\2`_\3\2\2\2ad\3\2\2"+
		"\2b`\3\2\2\2bc\3\2\2\2c\34\3\2\2\2db\3\2\2\2ei\7$\2\2fh\5\61\31\2gf\3"+
		"\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2jl\3\2\2\2ki\3\2\2\2lm\7$\2\2m\36"+
		"\3\2\2\2no\5\65\33\2o \3\2\2\2pr\5\67\34\2qp\3\2\2\2rs\3\2\2\2sq\3\2\2"+
		"\2st\3\2\2\2tu\3\2\2\2uw\7\60\2\2vx\5\67\34\2wv\3\2\2\2xy\3\2\2\2yw\3"+
		"\2\2\2yz\3\2\2\2z\"\3\2\2\2{|\7<\2\2|}\7/\2\2}$\3\2\2\2~\177\7^\2\2\177"+
		"\u0080\7]\2\2\u0080\u0084\3\2\2\2\u0081\u0083\5/\30\2\u0082\u0081\3\2"+
		"\2\2\u0083\u0086\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085"+
		"\u0087\3\2\2\2\u0086\u0084\3\2\2\2\u0087\u0088\7_\2\2\u0088\u0089\7^\2"+
		"\2\u0089&\3\2\2\2\u008a\u008c\t\2\2\2\u008b\u008a\3\2\2\2\u008c\u008d"+
		"\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\3\2\2\2\u008f"+
		"\u0090\b\24\2\2\u0090(\3\2\2\2\u0091\u0095\7%\2\2\u0092\u0094\n\3\2\2"+
		"\u0093\u0092\3\2\2\2\u0094\u0097\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096"+
		"\3\2\2\2\u0096\u0098\3\2\2\2\u0097\u0095\3\2\2\2\u0098\u0099\b\25\2\2"+
		"\u0099*\3\2\2\2\u009a\u009b\t\4\2\2\u009b,\3\2\2\2\u009c\u009d\t\5\2\2"+
		"\u009d.\3\2\2\2\u009e\u00a0\t\6\2\2\u009f\u009e\3\2\2\2\u00a0\60\3\2\2"+
		"\2\u00a1\u00a4\t\7\2\2\u00a2\u00a4\5\63\32\2\u00a3\u00a1\3\2\2\2\u00a3"+
		"\u00a2\3\2\2\2\u00a4\62\3\2\2\2\u00a5\u00a6\7^\2\2\u00a6\u00a7\t\b\2\2"+
		"\u00a7\64\3\2\2\2\u00a8\u00b1\7\62\2\2\u00a9\u00ad\59\35\2\u00aa\u00ac"+
		"\5\67\34\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2"+
		"\u00ad\u00ae\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00a8"+
		"\3\2\2\2\u00b0\u00a9\3\2\2\2\u00b1\66\3\2\2\2\u00b2\u00b5\7\62\2\2\u00b3"+
		"\u00b5\59\35\2\u00b4\u00b2\3\2\2\2\u00b4\u00b3\3\2\2\2\u00b58\3\2\2\2"+
		"\u00b6\u00b7\4\63;\2\u00b7:\3\2\2\2\20\2\\bisy\u0084\u008d\u0095\u009f"+
		"\u00a3\u00ad\u00b0\u00b4\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}