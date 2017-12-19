package com.test

import com.test.pages.CreatePage
import com.test.pages.EditPage
import com.test.pages.ListPage
import com.test.pages.ShowPage
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
	
	def "there are no bookings"() {
		when:
		go('booking/index')
		ListPage page = browser.page ListPage

		then:
		page.numberOfRows() == 0
	}

	def "add a booking"() {
		given:
		ListPage page = browser.page ListPage

		when:
		page.newEntity()

		then:
		at CreatePage
	}
	
	def "enter the details"() {
		given:
		CreatePage page = browser.page CreatePage

		when:
		page.populate('name', 'Tim')
		page.populateNumber('adults', 2)
		page.populateEmail('email', 'tim@apple.com')
		page.populateDate('arrival', 30, 12, 2017)
		page.populateDate('departure', 31, 12, 2017)

		page.save()

		then:
		at ShowPage
	}
	
	def "check the entered details"() {
		given:
		ShowPage page = browser.page ShowPage

		expect:
		page.value('Name') == "Tim"
		page.value('Adults') == "2"
		page.value('Email') == "tim@apple.com"
		page.value('Arrival').startsWith('2017-12-30')
		page.value('Departure').startsWith('2017-12-31')
	}

	def "edit the details"() {
		given:
		ShowPage page = browser.page ShowPage

		when:
		page.edit()

		then:
		EditPage editPage = at EditPage
		editPage.populate('name', 'Tim Cook')


		when:
		editPage.update()

		then:
		at ShowPage
	}
	
	def "check in listing"() {
		when:
		ShowPage page = browser.page ShowPage
		page.nav.select('Booking List')

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage

		then:
		listPage.entityRows.size() == 1

		when:
		def row = listPage.entityRow(0)

		then:
		row.cellText(0) == "Tim Cook"
	}
	
	def "show row"() {
		given:
		ListPage page = browser.page ListPage

		when:
		page.select('Tim Cook')

		then:
		at ShowPage
	}

	@IgnoreIf({ System.getProperty('geb.env') == 'htmlUnit' })
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
}
