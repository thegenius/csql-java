package com.lvonce.csql;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface PentaFunction<S1, S2, S3, S4, S5, R> {

    R apply(S1 s1, S2 s2, S3 s3, S4 s4, S5 s5);

    default <V> PentaFunction<S1, S2, S3, S4, S5, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (S1 s1, S2 s2, S3 s3, S4 s4, S5 s5) -> after.apply(apply(s1, s2, s3, s4, s5));
    }
}
