package com.lvonce.csql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.BiPredicate;

import com.lvonce.csql.CollectionSQL1.SQLCollectionExecuteContextJoinStep1;

public class CollectionSQL {

    public static <T> Predicate<T> nullToDefault(Predicate<T> predicate) {
        if (predicate == null) {
            return it -> true;
        }
        return predicate;
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        if (predicate == null) {
            return it -> true;
        }
        return it -> !predicate.test(it);
    }

    public static <T> Predicate<T> and(Predicate<T> predicate1, Predicate<T> predicate2) {
        if (predicate1 == null) {
            if (predicate2 == null) {
                return it -> true;
            }
            return predicate2;
        } else {
            if (predicate2 == null) {
                return predicate1;
            }
        }
        return it -> predicate1.test(it) && predicate2.test(it);
    }

    public static <T> Predicate<T> or(Predicate<T> predicate1, Predicate<T> predicate2) {
        if (predicate1 == null) {
            if (predicate2 == null) {
                return it -> true;
            }
            return predicate2;
        } else {
            if (predicate2 == null) {
                return predicate1;
            }
        }
        return it -> predicate1.test(it) || predicate2.test(it);
    }





    public static <T, K> BiPredicate<T, K> nullToDefault(BiPredicate<T, K> predicate) {
        if (predicate == null) {
            return (it1, it2) -> true;
        }
        return predicate;
    }

    public static <T, K> BiPredicate<T,K> not(BiPredicate<T,K> predicate) {
        if (predicate == null) {
            return (it1, it2) -> true;
        }
        return (it1, it2) -> !predicate.test(it1, it2);
    }

    public static <T,K> BiPredicate<T,K> and(BiPredicate<T,K> predicate1, BiPredicate<T,K> predicate2) {
        if (predicate1 == null) {
            if (predicate2 == null) {
                return (it1, it2) -> true;
            }
            return predicate2;
        } else {
            if (predicate2 == null) {
                return predicate1;
            }
        }
        return (it1, it2) -> predicate1.test(it1, it2) && predicate2.test(it1, it2);
    }

    public static <T,K> BiPredicate<T,K> or(BiPredicate<T,K> predicate1, BiPredicate<T,K> predicate2) {
        if (predicate1 == null) {
            if (predicate2 == null) {
                return (it1, it2) -> true;
            }
            return predicate2;
        } else {
            if (predicate2 == null) {
                return predicate1;
            }
        }
        return (it1, it2) -> predicate1.test(it1, it2) || predicate2.test(it1, it2);
    }

    public static <T> Collection<T> nullToEmpty(Collection<T> data) {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public static <T> boolean isEmpty(Collection<T> data) {
        return data == null || data.size() == 0;
    }

    public static <S1> SQLCollectionExecuteContextJoinStep1<S1> from(Collection<S1> data1) {
        SQLCollectionExecuteContextJoinStep1<S1> result = new SQLCollectionExecuteContextJoinStep1<>();
        result.dataList1 = data1;
        return result;
    }
}
