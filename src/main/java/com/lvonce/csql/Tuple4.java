package com.lvonce.csql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Tuple4<S1, S2, S3, S4> {
    S1 first;
    S2 second;
    S3 third;
    S4 fourth;
}