package com.lvonce.csql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Tuple2<S1, S2> {
    private S1 first;
    private S2 second;
}
