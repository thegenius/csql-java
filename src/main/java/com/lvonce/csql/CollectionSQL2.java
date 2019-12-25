package com.lvonce.csql;

import lombok.Data;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.lvonce.csql.CollectionSQL3.SQLCollectionExecuteContextJoinStep3;

public class CollectionSQL2 {

    @Data
    public static class SQLCollectionExecuteContextJoinStep2<S1, S2> {
        Collection<S1> dataList1;
        Collection<S2> dataList2;
        Predicate<S1> onPredicate1;

        public <S3> SQLCollectionExecuteContextJoinStep3<S1, S2, S3> join(Collection<S3> dataList) {
            SQLCollectionExecuteContextJoinStep3<S1, S2, S3> result = new SQLCollectionExecuteContextJoinStep3<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = this.dataList2;
            result.dataList3 = dataList;
            result.onPredicate1 = this.onPredicate1;
            result.onPredicate2 = it -> true;
            return result;
        }

        public SQLCollectionExecuteContextOnStep2<S1, S2> on(Predicate<S2> predicate) {
            Objects.requireNonNull(predicate, "on predicate should not be null");
            SQLCollectionExecuteContextOnStep2<S1, S2> result = new SQLCollectionExecuteContextOnStep2<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = this.dataList2;
            result.onPredicate1 = this.onPredicate1;
            result.onPredicate2 = predicate;
            return result;
        }

        public SQLCollectionExecuteContextWhereStep2<S1, S2> where(BiPredicate<S1, S2> predicate) {
            Objects.requireNonNull(predicate, "where predicate should not be null");
            SQLCollectionExecuteContextWhereStep2<S1, S2> result = new SQLCollectionExecuteContextWhereStep2<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = this.dataList2;
            result.onPredicate1 = this.onPredicate1;
            result.onPredicate2 = it -> true;
            result.wherePredicate = predicate;
            return result;
        }

        private <R> List<R> selectWithDataOneEmpty(BiFunction<S1, S2, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S2 it2 : dataList2) {
                result.add(selectFunc.apply(null, it2));
            }
            return result;
        }

        private <R> List<R> selectWithDataTwoEmpty(BiFunction<S1, S2, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch = onPredicate1.test(it1);
                if (onMatch) {
                    result.add(selectFunc.apply(it1, null));
                }
            }
            return result;
        }

        private <R> List<R> selectWithNotEmpty(BiFunction<S1, S2, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch = onPredicate1.test(it1);
                if (onMatch) {
                    for (S2 it2 : dataList2) {
                        result.add(selectFunc.apply(it1, it2));
                    }
                }
            }
            return result;
        }

        public <R> List<R> select(BiFunction<S1, S2, R> selectFunc) {
            if (CollectionSQL.isEmpty(dataList1)) {
                if (CollectionSQL.isEmpty(dataList2)) {
                    return new ArrayList<>();
                }
                return selectWithDataOneEmpty(selectFunc);
            } else {
                if (CollectionSQL.isEmpty(dataList2)) {
                    return selectWithDataTwoEmpty(selectFunc);
                }
                return selectWithNotEmpty(selectFunc);
            }
        }
    }

    @Data
    public static class SQLCollectionExecuteContextOnStep2<S1, S2> {
        Collection<S1> dataList1;
        Collection<S2> dataList2;
        Predicate<S1> onPredicate1;
        Predicate<S2> onPredicate2;

        public <S3> SQLCollectionExecuteContextJoinStep3<S1, S2, S3> join(Collection<S3> dataList) {
            SQLCollectionExecuteContextJoinStep3<S1, S2, S3> result = new SQLCollectionExecuteContextJoinStep3<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = this.dataList2;
            result.dataList3 = dataList;
            result.onPredicate1 = this.onPredicate1;
            result.onPredicate2 = this.onPredicate2;
            return result;
        }

        public SQLCollectionExecuteContextWhereStep2<S1, S2> where(BiPredicate<S1, S2> func) {
            SQLCollectionExecuteContextWhereStep2<S1, S2> result = new SQLCollectionExecuteContextWhereStep2<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = this.dataList2;
            result.onPredicate1 = this.onPredicate1;
            result.onPredicate2 = this.onPredicate2;
            result.wherePredicate = CollectionSQL.nullToDefault(func);
            return result;
        }

        private <R> List<R> selectWithDataOneEmpty(BiFunction<S1, S2, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S2 it2 : dataList2) {
                boolean match = onPredicate2.test(it2);
                if (match) {
                    result.add(selectFunc.apply(null, it2));
                }
            }
            return result;
        }

        private <R> List<R> selectWithDataTwoEmpty(BiFunction<S1, S2, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean match = onPredicate1.test(it1);
                if (match) {
                    result.add(selectFunc.apply(it1, null));
                }
            }
            return result;
        }

        private <R> List<R> selectWithNotEmpty(BiFunction<S1, S2, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean match1 = onPredicate1.test(it1);
                if (match1) {
                    for (S2 it2 : dataList2) {
                        boolean match2 = onPredicate2.test(it2);
                        if (match2) {
                            result.add(selectFunc.apply(it1, it2));
                        }
                    }
                }
            }
            return result;
        }

        public <R> List<R> select(BiFunction<S1, S2, R> selectFunc) {
            if (CollectionSQL.isEmpty(dataList1)) {
                if (CollectionSQL.isEmpty(dataList2)) {
                    return new ArrayList<>();
                }
                return selectWithDataOneEmpty(selectFunc);
            } else {
                if (CollectionSQL.isEmpty(dataList2)) {
                    return selectWithDataTwoEmpty(selectFunc);
                }
                return selectWithNotEmpty(selectFunc);
            }
        }
    }

    @Data
    public static class SQLCollectionExecuteContextWhereStep2<S1, S2> {
        Collection<S1> dataList1;
        Collection<S2> dataList2;
        Predicate<S1> onPredicate1;
        Predicate<S2> onPredicate2;
        BiPredicate<S1, S2> wherePredicate;

        private <R> List<R> selectWithDataOneEmpty(BiFunction<S1, S2, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S2 it2 : dataList2) {
                boolean onMatch = onPredicate2.test(it2);
                if (onMatch) {
                    boolean whereMatch = wherePredicate.test(null, it2);
                    if (whereMatch) {
                        result.add(selectFunc.apply(null, it2));
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithDataTwoEmpty(BiFunction<S1, S2, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch = onPredicate1.test(it1);
                if (onMatch) {
                    boolean whereMatch = wherePredicate.test(it1, null);
                    if (whereMatch) {
                        result.add(selectFunc.apply(it1, null));
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithNotEmpty(BiFunction<S1, S2, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean match1 = onPredicate1.test(it1);
                if (match1) {
                    for (S2 it2 : dataList2) {
                        boolean match2 = onPredicate2.test(it2);
                        if (match2) {
                            boolean whereMatch = wherePredicate.test(it1, it2);
                            if (whereMatch) {
                                result.add(selectFunc.apply(it1, it2));
                            }
                        }
                    }
                }
            }
            return result;
        }

        public <R> List<R> select(BiFunction<S1, S2, R> selectFunc) {
            if (CollectionSQL.isEmpty(dataList1)) {
                if (CollectionSQL.isEmpty(dataList2)) {
                    return new ArrayList<>();
                }
                return selectWithDataOneEmpty(selectFunc);
            } else {
                if (CollectionSQL.isEmpty(dataList2)) {
                    return selectWithDataTwoEmpty(selectFunc);
                }
                return selectWithNotEmpty(selectFunc);
            }
        }
    }
}
