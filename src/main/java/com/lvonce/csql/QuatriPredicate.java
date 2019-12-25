package com.lvonce.csql;

import java.util.Objects;

@FunctionalInterface
public interface QuatriPredicate<S1, S2, S3, S4> {

    boolean test(S1 s1, S2 s2, S3 s3, S4 s4);

    default QuatriPredicate<S1, S2, S3, S4> and(QuatriPredicate<? super S1, ? super S2, ? super S3, ? super S4> other) {
        Objects.requireNonNull(other);
        return (S1 s1, S2 s2, S3 s3, S4 s4) -> test(s1, s2, s3, s4) && other.test(s1, s2, s3, s4);
    }

    default QuatriPredicate<S1, S2, S3, S4> negate() {
        return (S1 s1, S2 s2, S3 s3, S4 s4) -> !test(s1, s2, s3, s4);
    }

    default QuatriPredicate<S1, S2, S3, S4> or(QuatriPredicate<? super S1, ? super S2, ? super S3,? super  S4> other) {
        Objects.requireNonNull(other);
        return (S1 s1, S2 s2, S3 s3, S4 s4) -> test(s1, s2, s3, s4) || other.test(s1, s2, s3, s4);
    }
}