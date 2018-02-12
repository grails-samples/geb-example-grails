package demo

import demo.pages.booking.CreateBookingPage
import demo.pages.booking.BookingEditPage
import demo.pages.booking.BookingListPage
import demo.pages.booking.BookingShowPage
import geb.spock.GebReportingSpec
import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise

import java.time.LocalDate

@Integration
@Stepwise
@IgnoreIf({ !System.getProperty('geb.env') })
class BookingCRUDSpec extends GebReportingSpec {

	@Shared
	RoomDataService roomDataService

	@Shared
	ExtraDataService extraDataService

	@Shared
	List<Room> rooms = []

	@Shared
	List<Extra> extras = []

	@OnceBefore
	def populateSampleData() {
		rooms << roomDataService.save('Room 101')
		rooms << roomDataService.save('Room 102')
		rooms << roomDataService.save('Room 103')
		extras << extraDataService.save('Breakfast')
		extras << extraDataService.save('Crib')
		extras << extraDataService.save('Champagne')
	}

	def cleanupSpec() {
		rooms.each { roomDataService.delete(it.id) }
		extras.each { extraDataService.delete(it.id) }
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
		at CreateBookingPage
	}
	
	def 'enter the details'() {
		given:
		CreateBookingPage page = page CreateBookingPage

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
		at BookingShowPage
	}
	
	def 'check the entered details'() {
		given:
		BookingShowPage page = page BookingShowPage

		expect:
		page.name == 'Tim'
		page.adults == '2'
		page.email == 'tim@apple.com'
		page.arrival.startsWith('2017-12-30')
		page.departure.startsWith('2017-12-31')
		page.rooms == 'Room 101,Room 102'
		page.extras == 'Breakfast'
	}

	def 'edit the details'() {
		given:
		BookingShowPage page = page BookingShowPage

		when:
		page.buttons.edit()

		then:
		BookingEditPage editPage = at BookingEditPage

		when:
		editPage.name = 'Tim Cook'
		editPage.uncheck('Room 102')
		editPage.check('Room 103')
		editPage.check('Champagne')
		editPage.buttons.update()

		then:
		at BookingShowPage
	}
	
	def 'check in listing'() {
		when:
		BookingShowPage page = page BookingShowPage
		page.nav.home()

		then:
		at BookingListPage

		when:
		BookingListPage listPage = browser.page BookingListPage

		then:
		listPage.table.numberOfRows() == 1

		when:
		String value = listPage.table.textAt(0, 0)

		then:
		value == 'Tim Cook'
	}
	
	def 'show row'() {
		given:
		BookingListPage page = page BookingListPage

		when:
		page.table.select('Tim Cook')

		then:
		at BookingShowPage

		and:
		BookingShowPage showPage = browser.page BookingShowPage
		showPage.rooms == 'Room 101,Room 103'
		showPage.extras == 'Breakfast,Champagne'
	}

	def "delete booking"() {
		given:
		BookingShowPage page = page BookingShowPage

		when:
		withConfirm { page.buttons.delete() }

		then:
		at BookingListPage

		when:
		BookingListPage listPage = browser.page BookingListPage

		then:
		listPage.message ==~ /Booking .+ deleted/
		listPage.table.numberOfRows() == 0
	}
}
