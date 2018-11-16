package demo.mappings

import demo.BookingController
import demo.ExtraController
import demo.UrlMappings
import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification

class BookingUrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(BookingController)
    }

    void "/ is handled by BookController::index"() {
        expect:
        verifyForwardUrlMapping("/", controller: 'booking', action: 'index')
    }

    void "booking controller mappings"() {
        expect:
        verifyForwardUrlMapping("/booking/index", controller: 'booking', action: 'index')

        and:
        verifyForwardUrlMapping("/booking/create", controller: 'booking', action: 'create')

        and:
        verifyForwardUrlMapping("/booking/save", controller: 'booking', action: 'save')

        and:
        verifyForwardUrlMapping("/booking/update", controller: 'booking', action: 'update')

        and:
        verifyForwardUrlMapping("/booking/delete", controller: 'booking', action: 'delete')

        and:
        verifyForwardUrlMapping("/booking/show/123", controller: 'booking', action: 'show') {
            id = '123'
        }

        and:
        verifyForwardUrlMapping("/booking/edit/123", controller: 'booking', action: 'edit') {
            id = '123'
        }
    }
}
