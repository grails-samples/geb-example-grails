package demo.mappings

import spock.lang.Specification

class BookingUrlMappingsSpec extends Specification {

    void setup() {

    }

    void "/ is handled by BookController::index"() {
        expect:
        false
    }
}
