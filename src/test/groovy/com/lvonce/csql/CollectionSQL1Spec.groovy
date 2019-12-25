package com.lvonce.csql

import spock.lang.Specification
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static com.lvonce.csql.CollectionSQL.from


class CollectionSQL1Spec extends Specification {
    private static Logger log = LoggerFactory.getLogger(CollectionSQL1Spec.class);


    def "basic one list with only select"() {
        given:
        def list = ["name1", "name2", "name3"]
        def result = from(list).select({ it -> it })

        expect:
        result == expectResult

        where:
        equalVal | expectResult
        "naMe1"  | ["name1", "name2", "name3"]
        "name2"  | ["name1", "name2", "name3"]
        "NAME3"  | ["name1", "name2", "name3"]
    }

    def "basic one list select"() {
        given:
        def list = ["name1", "name2", "name3"]
        def result = from(list).on({ it -> it.equalsIgnoreCase(equalVal) }).select({ it -> it })

        expect:
        result == expectResult

        where:
        equalVal | expectResult
        "naMe1"  | ["name1"]
        "name2"  | ["name2"]
        "NAME3"  | ["name3"]
    }

    def "basic one list select with null val"() {
        given:
        def list = ["name1", null, "name3"]
        def result = from(list).on({ it -> it == null || it.equalsIgnoreCase(equalVal) }).select({ it -> it })

        expect:
        result == expectResult

        where:
        equalVal | expectResult
        "naMe1"  | ["name1", null]
        "naMe2"  | [null]
        "NaMe3"  | [null, "name3"]
    }

    def "basic one list select with where "() {
        given:
        def list = ["name1", null, "Name3"]
        def result = from(list)
                .where({ it -> it !=null && it.startsWith(whereVal) })
                .select({ it -> it })

        expect:
        result == expectResult

        where:
        whereVal | expectResult
        "N"      | ["Name3"]
        "n"      | ["name1"]
    }

    def "basic one list select with on and where"() {
        given:
        def list = ["name1", null, "Name3"]
        def result = from(list)
                .on({ it -> equalVal.equalsIgnoreCase(it) })
                .where({ it -> it.startsWith(whereVal) })
                .select({ it -> it })

        expect:
        result == expectResult

        where:
        equalVal | whereVal | expectResult
        "naMe3"  | "N"      | ["Name3"]
        "naMe1"  | "N"      | []
    }

}
