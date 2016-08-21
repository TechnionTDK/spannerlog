package technion.tdk.spannerlog.utils.match;

// Source: https://kerflyn.wordpress.com/2012/05/09/towards-pattern-matching-in-java/


import java.util.function.Function;

public class OtherwisePattern implements Pattern {
    private final Function<Object, Object> function;

    public OtherwisePattern(Function<Object, Object> function) {
        this.function = function;
    }

    @Override
    public boolean matches(Object value) {
        return true;
    }

    @Override
    public Object apply(Object value) {
        return function.apply(value);
    }

    public static Pattern otherwise(Function<Object, Object> function) {
        return new OtherwisePattern(function);
    }
}
