package technion.tdk.spannerlog.utils.antlr;

/**
 * Created by yoavn on 05/09/16.
 */
public class SyntaxErrorException extends RuntimeException {
    public SyntaxErrorException(String message) {
        super(message);
    }
}
