package technion.tdk.spannerlog.utils.antlr;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;


public class ExceptionThrowerListener extends BaseErrorListener {
    private static ExceptionThrowerListener ourInstance = new ExceptionThrowerListener();

    public static ExceptionThrowerListener getInstance() {
        return ourInstance;
    }

    private ExceptionThrowerListener() {
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e)  {
        throw new RuntimeException("Syntax error");
    }
}
