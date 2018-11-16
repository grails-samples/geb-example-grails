package demo.domain

import demo.BookingExtra
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class BookingExtraSpec extends Specification implements DomainUnitTest<BookingExtra> {

    void 'test booking cannot be null'() {
        given:
        domain.booking = null

        expect:
        !domain.validate(['booking'])
        domain.errors['booking'].code == 'nullable'
    }

    void 'test extra cannot be null'() {
        given:
        domain.extra = null

        expect:
        !domain.validate(['extra'])
        domain.errors['extra'].code == 'nullable'
    }
}
