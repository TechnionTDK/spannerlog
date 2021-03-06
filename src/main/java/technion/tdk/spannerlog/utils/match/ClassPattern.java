package technion.tdk.spannerlog.utils.match;

// Source: https://kerflyn.wordpress.com/2012/05/09/towards-pattern-matching-in-java/

import java.util.function.Function;

public class ClassPattern<T> implements Pattern {

    private Class<T> clazz;

    private Function<T, Object> function;

    public ClassPattern(Class<T> clazz, Function<T, Object> function) {
        this.clazz = clazz;
        this.function = function;
    }

    public boolean matches(Object value) {
        return clazz.isInstance(value);
    }

    public Object apply(Object value) {
        return function.apply((T) value);
    }

    public static <T> Pattern inCaseOf(Class<T> clazz, Function<T, Object> function) {
        return new ClassPattern<T>(clazz, function);
    }
}
