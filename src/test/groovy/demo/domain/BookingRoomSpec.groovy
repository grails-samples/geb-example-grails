package demo.domain

import demo.BookingRoom
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class BookingRoomSpec extends Specification implements DomainUnitTest<BookingRoom> {

    void 'test booking cannot be null'() {
        given:
        domain.booking = null

        expect:
        !domain.validate(['booking'])
        domain.errors['booking'].code == 'nullable'
    }

    void 'test room cannot be null'() {
        given:
        domain.room = null

        expect:
        !domain.validate(['room'])
        domain.errors['room'].code == 'nullable'
    }
}
