package com.lvonce.csql;

import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.function.Predicate;

import com.lvonce.csql.CollectionSQL2.SQLCollectionExecuteContextJoinStep2;

import static com.lvonce.csql.CollectionSQL.and;
import static com.lvonce.csql.CollectionSQL.nullToEmpty;

public class CollectionSQL1 {

    @Data
    public static class SQLCollectionExecuteContextJoinStep1<S1> {
        Collection<S1> dataList1;

        public <S2> SQLCollectionExecuteContextJoinStep2<S1, S2> join(Collection<S2> dataList) {
            SQLCollectionExecuteContextJoinStep2<S1, S2> result = new SQLCollectionExecuteContextJoinStep2<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = nullToEmpty(dataList);
            result.onPredicate1 = it -> true;
            return result;
        }

        public SQLCollectionExecuteContextOnStep1<S1> on(Predicate<S1> predicate) {
            Objects.requireNonNull(predicate, "on predicate should not be null");
            SQLCollectionExecuteContextOnStep1<S1> result = new SQLCollectionExecuteContextOnStep1<>();
            result.dataList1 = this.dataList1;
            result.onPredicate1 = predicate;
            return result;
        }

        public SQLCollectionExecuteContextWhereStep1<S1> where(Predicate<S1> predicate) {
            Objects.requireNonNull(predicate, "where predicate should not be null");
            SQLCollectionExecuteContextWhereStep1<S1> result = new SQLCollectionExecuteContextWhereStep1<>();
            result.dataList1 = this.dataList1;
            result.wherePredicate1 = predicate;
            return result;
        }

        public <R> List<R> select(Function<S1, R> selectFunc) {
            Objects.requireNonNull(selectFunc, "select function should not be null");
            return dataList1.stream().map(selectFunc).collect(Collectors.toList());
        }
    }

    @Data
    public static class SQLCollectionExecuteContextOnStep1<S1> {
        Collection<S1> dataList1;
        Predicate<S1> onPredicate1;

        public <S2> SQLCollectionExecuteContextJoinStep2<S1, S2> join(Collection<S2> dataList) {
            SQLCollectionExecuteContextJoinStep2<S1, S2> result = new SQLCollectionExecuteContextJoinStep2<>();
            result.dataList1 = this.dataList1;
            result.dataList2 = dataList;
            result.onPredicate1 = this.onPredicate1;
            return result;
        }

        public SQLCollectionExecuteContextWhereStep1<S1> where(Predicate<S1> predicate) {
            Objects.requireNonNull(predicate, "where predicate should not be null");
            SQLCollectionExecuteContextWhereStep1<S1> result = new SQLCollectionExecuteContextWhereStep1<>();
            result.dataList1 = this.dataList1;
            result.onPredicate1 = this.onPredicate1;
            result.wherePredicate1 = predicate;
            return result;
        }

        public <R> List<R> select(Function<S1, R> selectFunc) {
            Objects.requireNonNull(selectFunc, "select function should not be null");
            return dataList1.stream().filter(onPredicate1).map(selectFunc).collect(Collectors.toList());
        }
    }

    @Data
    public static class SQLCollectionExecuteContextWhereStep1<S1> {
        Collection<S1> dataList1;
        Predicate<S1> onPredicate1;
        Predicate<S1> wherePredicate1;

        public <R> List<R> select(Function<S1, R> selectFunc) {
            Objects.requireNonNull(selectFunc, "select function should not be null");
            Predicate<S1> predicate = and(onPredicate1, wherePredicate1);
            return dataList1.stream().filter(predicate).map(selectFunc).collect(Collectors.toList());
        }
    }
}
