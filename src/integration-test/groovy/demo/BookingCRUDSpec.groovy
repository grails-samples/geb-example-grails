package demo

import demo.pages.CreatePage
import demo.pages.EditPage
import demo.pages.ListPage
import demo.pages.ShowPage
import geb.spock.GebReportingSpec
import grails.testing.mixin.integration.Integration
import spock.lang.IgnoreIf
import spock.lang.Stepwise

@Integration
@Stepwise
@IgnoreIf({ !System.getProperty('geb.env') })
class BookingCRUDSpec extends GebReportingSpec {

	def setup() {
		browser.baseUrl = "http://localhost:${serverPort}/"
	}

	def 'create rooms'() {
		when:
		go('room/create')

		then:
		at CreatePage

		when:
		CreatePage page = browser.page CreatePage
		page.populate('name', 'Room 101')
		page.save()

		then:
		at ShowPage

		when:
		ShowPage showPage = browser.page ShowPage
		showPage.create()

		then:
		at CreatePage

		when:
		page = browser.page CreatePage
		page.populate('name', 'Room 102')
		page.save()

		then:
		at ShowPage

		when:
		showPage = browser.page ShowPage
		showPage.create()

		then:
		at CreatePage

		when:
		page = browser.page CreatePage
		page.populate('name', 'Room 103')
		page.save()

		then:
		at ShowPage
	}

	def 'create extras'() {
		when:
		ShowPage showPage = browser.page ShowPage
		showPage.nav.select('Extra List')

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage
		listPage.newEntity()

		then:
		at CreatePage

		when:
		CreatePage page = browser.page CreatePage
		page.populate('name', 'Breakfast')
		page.save()

		then:
		at ShowPage

		when:
		showPage = browser.page ShowPage
		showPage.create()

		then:
		at CreatePage

		when:
		page = browser.page CreatePage
		page.populate('name', 'Crib')
		page.save()

		then:
		at ShowPage

		when:
		showPage = browser.page ShowPage
		showPage.create()

		then:
		at CreatePage

		when:
		page = browser.page CreatePage
		page.populate('name', 'Champagne')
		page.save()

		then:
		at ShowPage
	}
	
	def 'there are no bookings'() {
		when:
		ShowPage showPage = browser.page ShowPage
		showPage.nav.select('Home')
		ListPage page = browser.page ListPage

		then:
		page.numberOfRows() == 0
	}

	def 'add a booking'() {
		given:
		ListPage page = browser.page ListPage

		when:
		page.newEntity()

		then:
		at CreatePage
	}
	
	def 'enter the details'() {
		given:
		CreatePage page = browser.page CreatePage

		when:
		page.populate('name', 'Tim')
		page.populateNumber('adults', 2)
		page.populateEmail('email', 'tim@apple.com')
		page.populateDate('arrival', 30, 12, 2017)
		page.populateDate('departure', 31, 12, 2017)

		page.check('Room 101')
		page.check('Room 102')
		page.check('Breakfast')

		page.save()

		then:
		at ShowPage
	}
	
	def 'check the entered details'() {
		given:
		ShowPage page = browser.page ShowPage

		expect:
		page.value('Name') == 'Tim'
		page.value('Adults') == '2'
		page.value('Email') == 'tim@apple.com'
		page.value('Arrival').startsWith('2017-12-30')
		page.value('Departure').startsWith('2017-12-31')
		page.value('Rooms') == 'Room 101,Room 102'
		page.value('Extras') == 'Breakfast'
	}

	def 'edit the details'() {
		given:
		ShowPage page = browser.page ShowPage

		when:
		page.edit()

		then:
		EditPage editPage = at EditPage
		editPage.populate('name', 'Tim Cook')

		editPage.uncheck('Room 102')
		editPage.check('Room 103')

		editPage.check('Champagne')


		when:
		editPage.update()

		then:
		at ShowPage
	}
	
	def 'check in listing'() {
		when:
		ShowPage page = browser.page ShowPage
		page.nav.select('Home')

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage

		then:
		listPage.entityRows.size() == 1

		when:
		def row = listPage.entityRow(0)

		then:
		row.cellText(0) == 'Tim Cook'
	}
	
	def 'show row'() {
		given:
		ListPage page = browser.page ListPage

		when:
		page.select('Tim Cook')

		then:
		at ShowPage

		and:
		ShowPage showPage = browser.page ShowPage
		showPage.value('Rooms') == 'Room 101,Room 103'
		showPage.value('Extras') == 'Breakfast,Champagne'
	}

	def "delete booking"() {
		given:
		ShowPage page = browser.page ShowPage

		when:
		withConfirm { page.delete() }

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage

		then:
		listPage.message ==~ /Booking .+ deleted/
		listPage.numberOfRows() == 0
	}

	def "delete rooms"() {
		when:
		ListPage page = browser.page ListPage
		page.nav.select('Room List')

		then:
		at ListPage

		when:
		page.select('Room 101')

		then:
		at ShowPage

		when:
		ShowPage showPage = browser.page ShowPage
		withConfirm { showPage.delete() }

		then:
		at ListPage

		when:
		page = browser.page ListPage
		page.select('Room 102')

		then:
		at ShowPage

		when:
		showPage = browser.page ShowPage
		withConfirm { showPage.delete() }

		then:
		at ListPage

		when:
		page = browser.page ListPage
		page.select('Room 103')

		then:
		at ShowPage

		when:
		showPage = browser.page ShowPage
		withConfirm { showPage.delete() }

		then:
		at ListPage
	}

	def "delete extras"() {
		when:
		ListPage page = browser.page ListPage
		page.nav.select('Extra List')

		then:
		at ListPage

		when:
		page.select('Breakfast')

		then:
		at ShowPage

		when:
		ShowPage showPage = browser.page ShowPage
		withConfirm { showPage.delete() }

		then:
		at ListPage

		when:
		page = browser.page ListPage
		page.select('Crib')

		then:
		at ShowPage

		when:
		showPage = browser.page ShowPage
		withConfirm { showPage.delete() }

		then:
		at ListPage

		when:
		page = browser.page ListPage
		page.select('Champagne')

		then:
		at ShowPage

		when:
		showPage = browser.page ShowPage
		withConfirm { showPage.delete() }

		then:
		at ListPage
	}
}
