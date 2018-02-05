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
class ExtraCRUDSpec extends GebReportingSpec {

	def setup() {
		browser.baseUrl = "http://localhost:${serverPort}/"
	}

	def 'there are no extras'() {
		when:
		ListPage page = to ListPage, 'extra'

		then:
		page.numberOfRows() == 0
	}

	def 'add an extra'() {
		given:
		ListPage page = page ListPage

		when:
		page.newEntity()

		then:
		at CreatePage
	}

	def 'enter the extra details'() {
		given:
		CreatePage page = page CreatePage

		when:
		page.populate('name', 'Breakfast')
		page.save()

		then:
		at ShowPage
	}

	def 'check the entered details for the extra'() {
		given:
		ShowPage page = page ShowPage

		expect:
		page.value('Name') == 'Breakfast'
	}

	def 'edit the details'() {
		given:
		ShowPage page = page ShowPage

		when:
		page.edit()

		then:
		EditPage editPage = at EditPage
		editPage.populate('name', 'English Breakfast')

		when:
		editPage.update()

		then:
		at ShowPage
	}

	def 'check extra in listing'() {
		when:
		ShowPage page = page ShowPage
		page.nav.select('Extra List')

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage

		then:
		listPage.entityRows.size() == 1

		when:
		def row = listPage.entityRow(0)

		then:
		row.cellText(0) == 'English Breakfast'
	}

	def 'show extra'() {
		given:
		ListPage page = page ListPage

		when:
		page.select('English Breakfast')

		then:
		at ShowPage
	}

	def 'delete extra'() {
		given:
		ShowPage page = page ShowPage

		when:
		withConfirm { page.delete() }

		then:
		at ListPage

		when:
		ListPage listPage = browser.page ListPage

		then:
		listPage.message ==~ /Extra .+ deleted/
		listPage.numberOfRows() == 0
	}
}
