package com.lvonce.csql

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

import static com.lvonce.csql.CollectionSQL.from

class CollectionSQL3Spec extends Specification {
    private static Logger log = LoggerFactory.getLogger(CollectionSQL3Spec.class);

    def "basic 3 list select"() {
        given:
        def list1 = ["name1", "name2", "name3"]
        def list2 = ["name1name1"]
        def list3 = ["name1name1name1", "xname2yxx"]

        def result = from(list1).join(list2).join(list3)
                .select({ it1, it2, it3 -> new Tuple3<String, String, String>(it1, it2, it3) });

        expect:
        result == [new Tuple3<>("name1", "name1name1", "name1name1name1"),
                   new Tuple3<>("name1", "name1name1", "xname2yxx"),
                   new Tuple3<>("name2", "name1name1", "name1name1name1"),
                   new Tuple3<>("name2", "name1name1", "xname2yxx"),
                   new Tuple3<>("name3", "name1name1", "name1name1name1"),
                   new Tuple3<>("name3", "name1name1", "xname2yxx")
        ]
    }


    def "basic 3 list where select"() {
        given:
        def list1 = ["name1", "name2", "name3"]
        def list2 = ["name1name1", "name2xx", "naxme3x"]
        def list3 = ["name1name1name1", "xname2yxx", "name3"]

        def result = from(list1).join(list2).join(list3)
                .where({ it1, it2, it3 -> it3.contains(it2) && it2.contains(it1) })
                .select({ it1, it2, it3 -> new Tuple3<String, String, String>(it1, it2, it3) });

        expect:
        result == [new Tuple3<>("name1", "name1name1", "name1name1name1")]
    }

    def "basic 3 list on select"() {
        given:
        def list1 = ["name1", "name2", "tame3"]
        def list2 = [1, 2, 11, 25]
        def list3 = ["name1name1name1", "xname2yxx", "name3"]

        def result = from(list1)
                .on({ it -> it.startsWith("n") })
                .join(list2)
                .on({ it -> it < 10 })
                .join(list3)
                .on({it -> it.contains("me3")})
                .select({ it1, it2, it3 -> new Tuple3<String, Integer, String>(it1, it2, it3) });

        expect:
        result == [
                new Tuple3<>("name1", 1, "name3"),
                new Tuple3<>("name1", 2, "name3"),
                new Tuple3<>("name2", 1, "name3"),
                new Tuple3<>("name2", 2, "name3")
        ]
    }

    def "basic 3 list on where select"() {
        given:
        def list1 = ["name1", "name2", "tame3"]
        def list2 = [1, 2, 11, 25]
        def list3 = ["name1name1name1", "xname2yxx", "name3"]

        def result = from(list1)
                .on({ it -> it.startsWith("n") })
                .join(list2)
                .on({ it -> it < 10 })
                .join(list3)
                .on({it -> it.contains("me")})
                .where({ it1, it2, it3 -> it3.contains(it1) })
                .select({ it1, it2, it3 -> new Tuple3<String, Integer, String>(it1, it2, it3) });

        expect:
        result == [
                new Tuple3<>("name1", 1, "name1name1name1"),
                new Tuple3<>("name1", 2, "name1name1name1"),
                new Tuple3<>("name2", 1, "xname2yxx"),
                new Tuple3<>("name2", 2, "xname2yxx")
        ]
    }
}
