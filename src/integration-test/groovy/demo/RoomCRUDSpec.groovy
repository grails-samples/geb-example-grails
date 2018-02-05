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
class RoomCRUDSpec extends GebReportingSpec {

	def setup() {
		browser.baseUrl = "http://localhost:${serverPort}/"
	}

	def 'there are no rooms'() {
		when:
		ListPage page = to ListPage, 'room'

		then:
		page.numberOfRows() == 0
	}

	def 'add a room'() {
		given:
		ListPage page = page ListPage

		when:
		page.newEntity()

		then:
		at CreatePage
	}

	def 'enter the details'() {
		given:
		CreatePage page = page CreatePage

		when:
		page.populate('name', 'Room 101')
		page.save()

		then:
		at ShowPage
	}

	def 'check the entered details'() {
		given:
		ShowPage page = page ShowPage

		expect:
		page.value('Name') == 'Room 101'
	}

	def 'edit the details'() {
		given:
		ShowPage page = page ShowPage

		when:
		page.edit()

		then:
		EditPage editPage = at EditPage
		editPage.populate('name', 'Room101')

		when:
		editPage.update()

		then:
		at ShowPage
	}

	def 'check in listing'() {
		when:
		ShowPage page = page ShowPage
		page.nav.select('Room List')

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage

		then:
		listPage.entityRows.size() == 1

		when:
		def row = listPage.entityRow(0)

		then:
		row.cellText(0) == 'Room101'
	}

	def 'show row'() {
		given:
		ListPage page = page ListPage

		when:
		page.select('Room101')

		then:
		at ShowPage
	}

	def 'delete room'() {
		given:
		ShowPage page = page ShowPage

		when:
		withConfirm { page.delete() }

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage

		then:
		listPage.message ==~ /Room .+ deleted/
		listPage.numberOfRows() == 0
	}
}
