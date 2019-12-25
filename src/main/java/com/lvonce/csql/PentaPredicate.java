package com.lvonce.csql;

import java.util.Objects;

@FunctionalInterface
public interface PentaPredicate<S1, S2, S3, S4, S5> {

    boolean test(S1 s1, S2 s2, S3 s3, S4 s4, S5 s5);

    default PentaPredicate<S1, S2, S3, S4, S5> and(
        PentaPredicate<? super S1, ? super S2, ? super S3, ? super S4, ? super S5> other) {
        Objects.requireNonNull(other);
        return (S1 s1, S2 s2, S3 s3, S4 s4, S5 s5) -> test(s1, s2, s3, s4, s5) && other.test(s1, s2, s3, s4, s5);
    }

    default PentaPredicate<S1, S2, S3, S4, S5> negate() {
        return (S1 s1, S2 s2, S3 s3, S4 s4, S5 s5) -> !test(s1, s2, s3, s4, s5);
    }

    default PentaPredicate<S1, S2, S3, S4, S5> or(
        PentaPredicate<? super S1, ? super S2, ? super S3, ? super S4, ? super S5> other) {
        Objects.requireNonNull(other);
        return (S1 s1, S2 s2, S3 s3, S4 s4, S5 s5) -> test(s1, s2, s3, s4, s5) || other.test(s1, s2, s3, s4, s5);
    }
}