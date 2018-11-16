package demo.integration

import demo.Booking
import demo.BookingDataService
import demo.BookingFixture
import demo.LeakageDetector
import grails.core.GrailsApplication
import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import groovy.time.TimeCategory
import spock.lang.Shared
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class BookingDataServiceSpec extends Specification implements LeakageDetector, BookingFixture {

    BookingDataService bookingDataService
    SessionFactory sessionFactory
    GrailsApplication grailsApplication

    @Shared
    Date arrival

    @Shared
    Date departure

    def setup() {
        setupLeakage()
    }

    def cleanup() {
        verifyLeakage()
    }

    def setupSpec() {
        Date now = new Date()
        use(TimeCategory) {
            arrival = now + 1.day
            departure = now + 3.day
        }
    }

    def cleanupSpec() {
        arrival = null
        departure = null
    }

    void "test get"() {
        when:
        Booking book = saveBooking('Phil', 'phil@apple.com', arrival, departure)

        then:
        bookingDataService.get(book.id) != null
    }

    void "test list"() {
        saveBooking('Phil', 'phil@apple.com', arrival, departure)
        saveBooking('Steve', 'steve@apple.com', arrival, departure)
        saveBooking('Tim', 'tim@apple.com', arrival, departure)
        saveBooking('Johny', 'johny@apple.com', arrival, departure)

        when:
        List<Booking> bookingList = bookingDataService.list(max: 2, offset: 2)

        then:
        bookingList.size() == 2

        and:
        bookingList[0].name == 'Tim'
        bookingList[0].email == 'tim@apple.com'
        bookingList[0].arrival == arrival
        bookingList[0].departure == departure

        and:
        bookingList[1].name == 'Johny'
        bookingList[1].email == 'johny@apple.com'
        bookingList[1].arrival == arrival
        bookingList[1].departure == departure
    }

    void "test count"() {
        when:
        saveBooking('Phil', 'phil@apple.com', arrival, departure)
        saveBooking('Steve', 'steve@apple.com', arrival, departure)
        saveBooking('Tim', 'tim@apple.com', arrival, departure)
        saveBooking('Johny', 'johny@apple.com', arrival, departure)

        then:
        bookingDataService.count() == old(bookingDataService.count()) + 4
    }

    void "test delete"() {
        when:
        Booking booking = saveBooking('Phil', 'phil@apple.com', arrival, departure)
        saveBooking('Steve', 'steve@apple.com', arrival, departure)
        saveBooking('Tim', 'tim@apple.com', arrival, departure)
        saveBooking('Johny', 'johny@apple.com', arrival, departure)

        then:
        bookingDataService.count() == old(bookingDataService.count()) + 4

        when:
        bookingDataService.delete(booking.id)
        sessionFactory.currentSession.flush()

        then:
        bookingDataService.count() == old(bookingDataService.count()) - 1
    }

    void "test save"() {
        when:
        Booking booking = saveBooking('Phil', 'phil@apple.com', arrival, departure)

        then:
        booking
        booking.id != null
        booking.name == 'Phil'
        booking.email == 'phil@apple.com'
        booking.arrival == arrival
        booking.departure == departure
    }
}
