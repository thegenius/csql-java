package com.lvonce.csql;

import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class CollectionSQL3 {

    @Data
    public static class SQLCollectionExecuteContextJoinStep3<S1, S2, S3> {
        Collection<S1> dataList1;
        Collection<S2> dataList2;
        Collection<S3> dataList3;
        Predicate<S1> onPredicate1;
        Predicate<S2> onPredicate2;

        public SQLCollectionExecuteContextOnStep3<S1, S2, S3> on(Predicate<S3> predicate) {
            Objects.requireNonNull(predicate, "on predicate should not be null");
            SQLCollectionExecuteContextOnStep3<S1, S2, S3> result = new SQLCollectionExecuteContextOnStep3<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = this.dataList2;
            result.dataList3 = this.dataList3;
            result.onPredicate1 = this.onPredicate1;
            result.onPredicate2 = this.onPredicate2;
            result.onPredicate3 = predicate;
            return result;
        }

        public SQLCollectionExecuteContextWhereStep3<S1, S2, S3> where(TriPredicate<S1, S2, S3> predicate) {
            Objects.requireNonNull(predicate, "where predicate should not be null");
            SQLCollectionExecuteContextWhereStep3<S1, S2, S3> result = new SQLCollectionExecuteContextWhereStep3<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = this.dataList2;
            result.dataList3 = this.dataList3;
            result.onPredicate1 = this.onPredicate1;
            result.onPredicate2 = this.onPredicate2;
            result.onPredicate3 = it -> true;
            result.wherePredicate = predicate;
            return result;
        }

        private <R> List<R> selectWithDataOneEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S2 it2 : dataList2) {
                boolean onMatch2 = onPredicate2.test(it2);
                if (onMatch2) {
                    for (S3 it3 : dataList3) {
                        result.add(selectFunc.apply(null, it2, it3));
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithDataTwoEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch1 = onPredicate1.test(it1);
                if (onMatch1) {
                    for (S3 it3 : dataList3) {
                        result.add(selectFunc.apply(it1, null, it3));
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithDataThreeEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch1 = onPredicate1.test(it1);
                if (onMatch1) {
                    for (S2 it2 : dataList2) {
                        boolean onMatch2 = onPredicate2.test(it2);
                        if (onMatch2) {
                            result.add(selectFunc.apply(it1, it2, null));
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithNotEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch1 = onPredicate1.test(it1);
                if (onMatch1) {
                    for (S2 it2 : dataList2) {
                        boolean onMatch2 = onPredicate2.test(it2);
                        if (onMatch2) {
                            for (S3 it3 : dataList3) {
                                result.add(selectFunc.apply(it1, it2, it3));
                            }
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithOnlyDataOne(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean match1 = onPredicate1.test(it1);
                if (match1) {
                    result.add(selectFunc.apply(it1, null, null));
                }
            }
            return result;
        }

        private <R> List<R> selectWithOnlyDataTwo(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S2 it2 : dataList2) {
                boolean match1 = onPredicate2.test(it2);
                if (match1) {
                    result.add(selectFunc.apply(null, it2, null));
                }
            }
            return result;
        }

        private <R> List<R> selectWithOnlyDataThree(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S3 it3 : dataList3) {
                result.add(selectFunc.apply(null, null, it3));
            }
            return result;
        }

        public <R> List<R> select(TriFunction<S1, S2, S3, R> selectFunc) {
            if (CollectionSQL.isEmpty(dataList1)) {
                if (CollectionSQL.isEmpty(dataList2)) {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return new ArrayList<>();
                    } else {
                        return selectWithOnlyDataThree(selectFunc);
                    }
                } else {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return selectWithOnlyDataTwo(selectFunc);
                    } else {
                        return selectWithDataOneEmpty(selectFunc);
                    }
                }
            } else {
                if (CollectionSQL.isEmpty(dataList2)) {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return selectWithOnlyDataOne(selectFunc);
                    } else {
                        return selectWithDataTwoEmpty(selectFunc);
                    }
                } else {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return selectWithDataThreeEmpty(selectFunc);
                    } else {
                        return selectWithNotEmpty(selectFunc);
                    }
                }
            }
        }
    }

    @Data
    public static class SQLCollectionExecuteContextOnStep3<S1, S2, S3> {
        Collection<S1> dataList1;
        Collection<S2> dataList2;
        Collection<S3> dataList3;
        Predicate<S1> onPredicate1;
        Predicate<S2> onPredicate2;
        Predicate<S3> onPredicate3;

        public SQLCollectionExecuteContextWhereStep3<S1, S2, S3> where(TriPredicate<S1, S2, S3> predicate) {
            Objects.requireNonNull(predicate, "where predicate should not be null");
            SQLCollectionExecuteContextWhereStep3<S1, S2, S3> result = new SQLCollectionExecuteContextWhereStep3<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = this.dataList2;
            result.dataList3 = this.dataList3;
            result.onPredicate1 = this.onPredicate1;
            result.onPredicate2 = this.onPredicate2;
            result.onPredicate3 = this.onPredicate3;
            result.wherePredicate = predicate;
            return result;
        }

        private <R> List<R> selectWithDataOneEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S2 it2 : dataList2) {
                boolean onMatch2 = onPredicate2.test(it2);
                if (onMatch2) {
                    for (S3 it3 : dataList3) {
                        boolean onMatch3 = onPredicate3.test(it3);
                        if (onMatch3) {
                            result.add(selectFunc.apply(null, it2, it3));
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithDataTwoEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch1 = onPredicate1.test(it1);
                if (onMatch1) {
                    for (S3 it3 : dataList3) {
                        boolean onMatch3 = onPredicate3.test(it3);
                        if (onMatch3) {
                            result.add(selectFunc.apply(it1, null, it3));
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithDataThreeEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch1 = onPredicate1.test(it1);
                if (onMatch1) {
                    for (S2 it2 : dataList2) {
                        boolean onMatch2 = onPredicate2.test(it2);
                        if (onMatch2) {
                            result.add(selectFunc.apply(it1, it2, null));
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithNotEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch1 = onPredicate1.test(it1);
                if (onMatch1) {
                    for (S2 it2 : dataList2) {
                        boolean onMatch2 = onPredicate2.test(it2);
                        if (onMatch2) {
                            for (S3 it3 : dataList3) {
                                boolean onMatch3 = onPredicate3.test(it3);
                                if (onMatch3) {
                                    result.add(selectFunc.apply(it1, it2, it3));
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithOnlyDataOne(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean match1 = onPredicate1.test(it1);
                if (match1) {
                    result.add(selectFunc.apply(it1, null, null));
                }
            }
            return result;
        }

        private <R> List<R> selectWithOnlyDataTwo(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S2 it2 : dataList2) {
                boolean match1 = onPredicate2.test(it2);
                if (match1) {
                    result.add(selectFunc.apply(null, it2, null));
                }
            }
            return result;
        }

        private <R> List<R> selectWithOnlyDataThree(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S3 it3 : dataList3) {
                boolean match1 = onPredicate3.test(it3);
                if (match1) {
                    result.add(selectFunc.apply(null, null, it3));
                }
            }
            return result;
        }

        public <R> List<R> select(TriFunction<S1, S2, S3, R> selectFunc) {
            if (CollectionSQL.isEmpty(dataList1)) {
                if (CollectionSQL.isEmpty(dataList2)) {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return new ArrayList<>();
                    } else {
                        return selectWithOnlyDataThree(selectFunc);
                    }
                } else {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return selectWithOnlyDataTwo(selectFunc);
                    } else {
                        return selectWithDataOneEmpty(selectFunc);
                    }
                }
            } else {
                if (CollectionSQL.isEmpty(dataList2)) {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return selectWithOnlyDataOne(selectFunc);
                    } else {
                        return selectWithDataTwoEmpty(selectFunc);
                    }
                } else {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return selectWithDataThreeEmpty(selectFunc);
                    } else {
                        return selectWithNotEmpty(selectFunc);
                    }
                }
            }
        }
    }

    @Data
    public static class SQLCollectionExecuteContextWhereStep3<S1, S2, S3> {
        Collection<S1> dataList1;
        Collection<S2> dataList2;
        Collection<S3> dataList3;
        Predicate<S1> onPredicate1;
        Predicate<S2> onPredicate2;
        Predicate<S3> onPredicate3;
        TriPredicate<S1, S2, S3> wherePredicate;

        private <R> List<R> selectWithDataOneEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S2 it2 : dataList2) {
                boolean onMatch2 = onPredicate2.test(it2);
                if (onMatch2) {
                    for (S3 it3 : dataList3) {
                        boolean onMatch3 = onPredicate3.test(it3);
                        if (onMatch3) {
                            boolean whereMatch = wherePredicate.test(null, it2, it3);
                            if (whereMatch) {
                                result.add(selectFunc.apply(null, it2, it3));
                            }
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithDataTwoEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch1 = onPredicate1.test(it1);
                if (onMatch1) {
                    for (S3 it3 : dataList3) {
                        boolean onMatch3 = onPredicate3.test(it3);
                        if (onMatch3) {
                            boolean whereMatch = wherePredicate.test(it1, null, it3);
                            if (whereMatch) {
                                result.add(selectFunc.apply(it1, null, it3));
                            }
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithDataThreeEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch1 = onPredicate1.test(it1);
                if (onMatch1) {
                    for (S2 it2 : dataList2) {
                        boolean onMatch2 = onPredicate2.test(it2);
                        if (onMatch2) {
                            boolean whereMatch = wherePredicate.test(it1, it2, null);
                            if (whereMatch) {
                                result.add(selectFunc.apply(it1, it2, null));
                            }
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithNotEmpty(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean onMatch1 = onPredicate1.test(it1);
                if (onMatch1) {
                    for (S2 it2 : dataList2) {
                        boolean onMatch2 = onPredicate2.test(it2);
                        if (onMatch2) {
                            for (S3 it3 : dataList3) {
                                boolean onMatch3 = onPredicate3.test(it3);
                                if (onMatch3) {
                                    boolean whereMatch = wherePredicate.test(it1, it2, it3);
                                    if (whereMatch) {
                                        result.add(selectFunc.apply(it1, it2, it3));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithOnlyDataOne(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S1 it1 : dataList1) {
                boolean match1 = onPredicate1.test(it1);
                if (match1) {
                    boolean whereMatch = wherePredicate.test(it1, null, null);
                    if (whereMatch) {
                        result.add(selectFunc.apply(it1, null, null));
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithOnlyDataTwo(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S2 it2 : dataList2) {
                boolean match1 = onPredicate2.test(it2);
                if (match1) {
                    boolean whereMatch = wherePredicate.test(null, it2, null);
                    if (whereMatch) {
                        result.add(selectFunc.apply(null, it2, null));
                    }
                }
            }
            return result;
        }

        private <R> List<R> selectWithOnlyDataThree(TriFunction<S1, S2, S3, R> selectFunc) {
            List<R> result = new ArrayList<>();
            for (S3 it3 : dataList3) {
                boolean match1 = onPredicate3.test(it3);
                if (match1) {
                    boolean whereMatch = wherePredicate.test(null, null, it3);
                    if (whereMatch) {
                        result.add(selectFunc.apply(null, null, it3));
                    }
                }
            }
            return result;
        }

        public <R> List<R> select(TriFunction<S1, S2, S3, R> selectFunc) {
            if (CollectionSQL.isEmpty(dataList1)) {
                if (CollectionSQL.isEmpty(dataList2)) {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return new ArrayList<>();
                    } else {
                        return selectWithOnlyDataThree(selectFunc);
                    }
                } else {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return selectWithOnlyDataTwo(selectFunc);
                    } else {
                        return selectWithDataOneEmpty(selectFunc);
                    }
                }
            } else {
                if (CollectionSQL.isEmpty(dataList2)) {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return selectWithOnlyDataOne(selectFunc);
                    } else {
                        return selectWithDataTwoEmpty(selectFunc);
                    }
                } else {
                    if (CollectionSQL.isEmpty(dataList3)) {
                        return selectWithDataThreeEmpty(selectFunc);
                    } else {
                        return selectWithNotEmpty(selectFunc);
                    }
                }
            }
        }
    }
}
