package com.lvonce.csql;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface QuatriFunction<S1, S2, S3, S4, R> {

    R apply(S1 s1, S2 s2, S3 s3, S4 s4);

    default <V> QuatriFunction<S1, S2, S3, S4, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (S1 s1, S2 s2, S3 s3, S4 s4) -> after.apply(apply(s1, s2, s3, s4));
    }
}
