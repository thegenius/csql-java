package com.lvonce.csql;

import java.util.Objects;

@FunctionalInterface
public interface TriPredicate<S1, S2, S3> {

    boolean test(S1 s1, S2 s2, S3 s3);

    default TriPredicate<S1, S2, S3> and(TriPredicate<? super S1, ? super S2, ? super S3> other) {
        Objects.requireNonNull(other);
        return (S1 s1, S2 s2, S3 s3) -> test(s1, s2, s3) && other.test(s1, s2, s3);
    }

    default TriPredicate<S1, S2, S3> negate() {
        return (S1 s1, S2 s2, S3 s3) -> !test(s1, s2, s3);
    }

    default TriPredicate<S1, S2, S3> or(TriPredicate<? super S1, ? super S2, ? super S3> other) {
        Objects.requireNonNull(other);
        return (S1 s1, S2 s2, S3 s3) -> test(s1, s2, s3) || other.test(s1, s2, s3);
    }
}