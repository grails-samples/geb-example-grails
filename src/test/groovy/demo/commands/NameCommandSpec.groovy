package demo.commands

import spock.lang.Specification

class NameCommandSpec extends Specification {

    void 'For NameCommand name cannot be null'() {
        expect:
        false
    }
}
