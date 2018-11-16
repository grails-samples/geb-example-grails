package demo.browserautomation

import demo.Booking
import demo.BookingDataService
import demo.BookingService
import demo.Extra
import demo.ExtraDataService
import demo.ExtraFixture
import demo.Fixture
import demo.LeakageDetector
import demo.Room
import demo.RoomDataService
import demo.RoomFixture
import demo.browserautomation.pages.booking.CreateBookingPage
import demo.browserautomation.pages.booking.BookingEditPage
import demo.browserautomation.pages.booking.BookingListPage
import demo.browserautomation.pages.booking.BookingShowPage
import geb.spock.GebReportingSpec
import grails.core.GrailsApplication
import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import groovy.util.logging.Slf4j
import spock.lang.Requires
import spock.lang.Shared
import java.time.LocalDate

@Integration
@Requires({ sys['geb.env'] })
@Slf4j
class BookingCRUDSpec extends GebReportingSpec implements LeakageDetector, Fixture, RoomFixture, ExtraFixture {

	@Shared
	BookingService bookingService

	@Shared
	BookingDataService bookingDataService

	@Shared
	ExtraDataService extraDataService

	@Shared
	RoomDataService roomDataService

	@Shared
	GrailsApplication grailsApplication

	def setup() {
		setupLeakage()
	}

	def cleanup() {
		verifyLeakage()
	}

	@Shared
	List<Room> rooms = []

	@Shared
	List<Extra> extras = []

	@OnceBefore
	def populateSampleData() {
		rooms = saveRooms(['Room 101', 'Room 102', 'Room 103'])
		extras = saveExtras(['Breakfast', 'Crib', 'Champagne'])
	}

	def cleanupSpec() {
		deleteRooms(rooms)
		deleteExtras(extras)
	}
	
	def 'there are no bookings'() {
		when:
		BookingListPage page = to BookingListPage

		then:
		page.table.numberOfRows() == 0
	}

	def 'add a booking'() {
		given:
		BookingListPage page = page BookingListPage

		when:
		page.buttons.create()

		then:
		waitFor {
			at CreateBookingPage
		}
	}
	
	def 'enter the details'() {
		given:
		CreateBookingPage page = to CreateBookingPage

		when:
		page.name = 'Tim'
		page.adults = 2
		page.email = 'tim@apple.com'
		page.arrival = LocalDate.of(2017, 12, 30)
		page.departure = LocalDate.of(2017, 12, 31)

		page.check('Room 101')
		page.check('Room 102')
		page.check('Breakfast')

		page.save()

		then:
		waitFor {
			at BookingShowPage
		}

		cleanup:
		deleteBookingByName('Tim')
	}

	def 'check the entered details'() {
		given:
		Booking booking = saveBooking()
		BookingShowPage page = to BookingShowPage, booking.id

		expect:
		page.name == 'Tim'
		page.adults == '2'
		page.email == 'tim@apple.com'
		page.arrival.startsWith('2017-12-30')
		page.departure.startsWith('2017-12-31')
		page.rooms == 'Room 101,Room 102'
		page.extras == 'Breakfast'

		cleanup:
		bookingService.delete(booking.id)
	}

	def 'edit the details'() {
		given:
		Booking booking = saveBooking()
		BookingShowPage page = to BookingShowPage, booking.id

		when:
		page.buttons.edit()

		then:
		waitFor {
			at BookingEditPage
		}

		when:
		BookingEditPage editPage = browser.page(BookingEditPage)
		editPage.name = 'Tim Cook'
		editPage.uncheck('Room 102')
		editPage.check('Room 103')
		editPage.check('Champagne')
		editPage.buttons.update()

		then:
		waitFor {
			at BookingShowPage
		}

		cleanup:
		bookingService.delete(booking.id)
	}
	
	def 'check in listing'() {
		given:
		Booking booking = saveBooking()

		when:
		BookingShowPage page = to BookingShowPage, booking.id
		page.nav.home()

		then:
		waitFor {
			at BookingListPage
		}

		when:
		BookingListPage listPage = browser.page BookingListPage

		then:
		listPage.table.numberOfRows() == 1

		when:
		String value = listPage.table.textAt(0, 0)

		then:
		value == 'Tim'

		cleanup:
		bookingService.delete(booking.id)
	}
	
	def 'show row'() {
		given:
		Booking booking = saveBooking()
		BookingListPage page = to BookingListPage

		when:
		page.table.select('Tim')

		then:
		waitFor {
			at BookingShowPage
		}

		and:
		BookingShowPage showPage = browser.page BookingShowPage
		showPage.rooms == 'Room 101,Room 102'
		showPage.extras == 'Breakfast'

		cleanup:
		bookingService.delete(booking.id)
	}

	def "delete booking"() {
		given:
		Booking booking = saveBooking()
		BookingShowPage page = to BookingShowPage, booking.id

		when:
		withConfirm { page.buttons.delete() }

		then:
		waitFor {
			at BookingListPage
		}

		when:
		BookingListPage listPage = browser.page BookingListPage

		then:
		listPage.message ==~ /Booking .+ deleted/
		listPage.table.numberOfRows() == 0
	}
}
