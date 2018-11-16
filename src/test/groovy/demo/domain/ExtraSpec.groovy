package demo.domain

import spock.lang.Specification

class ExtraSpec extends Specification {

    void 'test name cannot be null for Extra domain class'() {
        expect:
        false
    }

    void 'test name can have a maximum of 255 characters for Extra domain class'() {
        expect:
        false
    }
}
