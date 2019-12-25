package com.lvonce.csql;

import java.util.Objects;
import java.util.function.Function;


@FunctionalInterface
public interface TriFunction<S1, S2, S3, R> {

    R apply(S1 s1, S2 s2, S3 s3);

    default <V> TriFunction<S1, S2, S3, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (S1 s1, S2 s2, S3 s3) -> after.apply(apply(s1, s2, s3));
    }
}
