package technion.tdk.spannerlog.utils.match;

// Source: https://kerflyn.wordpress.com/2012/05/09/towards-pattern-matching-in-java/

public interface Pattern {
    boolean matches(Object value);

    Object apply(Object value);
}
