package com.lvonce.csql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Tuple3<S1, S2, S3> {
    S1 first;
    S2 second;
    S3 third;
}
