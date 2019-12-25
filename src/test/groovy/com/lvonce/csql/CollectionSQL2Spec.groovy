package com.lvonce.csql

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

import static com.lvonce.csql.CollectionSQL.from

class CollectionSQL2Spec extends Specification {
    private static Logger log = LoggerFactory.getLogger(CollectionSQL2Spec.class);


    def "basic 2 list select, 1 empty"() {
        given:
        List<String> list1 = new ArrayList<>();
        list1.add("name1");
        list1.add("name2");

        List<Tuple2<String, String>> result = from(list1)
                .on({ it -> it.equalsIgnoreCase(val1) })
                .join((List<String>) null)
                .select({ it1, it2 -> new Tuple2<String, String>(it1, it2) });

        expect:
        result == val2

        where:
        val1    | val2
        "name1" | [new Tuple2<>("name1", null)]
    }

    def "basic 2 list select"() {
        given:
        List<String> list1 = new ArrayList<>();
        list1.add("name1");
        list1.add("name2");

        List<String> list2 = new ArrayList<>();
        list2.add("word1");
        list2.add("word2");

        List<Tuple2<String, String>> result = from(list1)
                .on({ it -> it.equalsIgnoreCase(val1) })
                .join(list2)
                .select({ it1, it2 -> new Tuple2<String, String>(it1, it2) });

        expect:
        result == val2

        where:
        val1    | val2
        "name1" | [new Tuple2<>("name1", "word1"), new Tuple2<>("name1", "word2")]
    }

    def "basic 2 list join where"() {
        given:
        List<String> list1 = new ArrayList<>();
        list1.add("name1");
        list1.add("name2");

        List<String> list2 = new ArrayList<>();
        list2.add("word1");
        list2.add("word2name2");

        List<Tuple2<String, String>> result = from(list1)
                .join(list2)
                .on({it -> it.contains("rd2")})
                .where({it1, it2 -> it2.contains(it1)})
                .select({ it1, it2 -> new Tuple2<String, String>(it1, it2) });

        expect:
        result == val2

        where:
        val1    | val2
        "name2" | [new Tuple2<>("name2", "word2name2")]
    }

    def "basic 2 list on where"() {
        given:
        List<String> list1 = new ArrayList<>();
        list1.add("name1");
        list1.add("name2");

        List<String> list2 = new ArrayList<>();
        list2.add("word1");
        list2.add("word2name2");

        List<Tuple2<String, String>> result = from(list1)
                .on({ it -> it.equalsIgnoreCase(val1) })
                .join(list2)
                .where({it1, it2 -> it2.contains(it1)})
                .select({ it1, it2 -> new Tuple2<String, String>(it1, it2) });

        expect:
        result == val2

        where:
        val1    | val2
        "name2" | [new Tuple2<>("name2", "word2name2")]
    }

    def "basic 3 list select"() {
        given:
        List<String> list1 = new ArrayList<>();
        list1.add("name1");
        list1.add("name2");

        List<String> list2 = new ArrayList<>();
        list2.add("word1");
        list2.add("word2");

        List<Tuple2<String, String>> result = from(list1)
                .join(list2)
                .on({ it -> it.equalsIgnoreCase(val1) })
                .select({ it1, it2 -> new Tuple2<String, String>(it1, it2) });

        expect:
        result == val2

        where:
        val1    | val2
        "word1" | [new Tuple2<>("name1", "word1"), new Tuple2<>("name2", "word1")]
    }

    def "basic 4 list select"() {
        given:
        List<String> list1 = new ArrayList<>();
        list1.add("name1");
        list1.add("name2");

        List<String> list2 = new ArrayList<>();
        list2.add("word1");
        list2.add("word2");
        list2.add("word2name1");

        List<Tuple2<String, String>> result = from(list1)
                .on({ it -> !it.empty })
                .join(list2)
                .where({it1, it2 -> it2.contains(it1)})
                .select({ it1, it2 -> new Tuple2<String, String>(it1, it2) });

        expect:
        result == val2

        where:
        val1    | val2
        "name1" | [new Tuple2<>("name1", "word2name1")]
    }
}
