package demo.browserautomation

import demo.Extra
import demo.ExtraDataService
import demo.ExtraFixture
import demo.LeakageDetector
import demo.browserautomation.pages.extra.CreateExtraPage
import demo.browserautomation.pages.extra.EditExtraPage
import demo.browserautomation.pages.extra.ExtraListPage
import demo.browserautomation.pages.extra.ShowExtraPage
import geb.spock.GebReportingSpec
import grails.core.GrailsApplication
import grails.testing.mixin.integration.Integration
import groovy.util.logging.Slf4j
import spock.lang.Requires

@Integration
@Requires({ sys['geb.env'] })
@Slf4j
class ExtraCRUDSpec extends GebReportingSpec implements LeakageDetector, ExtraFixture {

	GrailsApplication grailsApplication

	ExtraDataService extraDataService

	def setup() {
		setupLeakage()
	}

	def cleanup() {
		verifyLeakage()
	}

	def 'there are no extras'() {
		when:
		ExtraListPage page = to ExtraListPage

		then:
		page.table.numberOfRows() == 0
	}

	def 'add an extra'() {
		given:
		ExtraListPage page = to ExtraListPage

		when:
		page.buttons.create()

		then:
		waitFor {
			at CreateExtraPage
		}
	}

	def 'enter the extra details'() {
		given:
		CreateExtraPage page = to CreateExtraPage

		when:
		page.name = 'Breakfast'
		page.save()

		then:
		waitFor {
			at ShowExtraPage
		}

		extraDataService.delete(extraDataService.list([:]).find { it.name == 'Breakfast' }?.id)
	}

	def 'check the entered details for the extra'() {
		given:
		Extra extra = saveExtra('Breakfast')
		ShowExtraPage page = to ShowExtraPage, extra.id

		expect:
		page.name == 'Breakfast'

		cleanup:
		extraDataService.delete(extra.id)
	}

	def 'edit the details'() {
		given:
		Extra extra = saveExtra('Breakfast')
		ShowExtraPage page = to ShowExtraPage, extra.id

		when:
		page.buttons.edit()

		then:
		waitFor {
			at EditExtraPage
		}

		when:
		EditExtraPage editPage = browser.page(EditExtraPage)
		editPage.name = 'English Breakfast'
		editPage.buttons.update()

		then:
		waitFor {
			at ShowExtraPage
		}

		and:
		browser.page(ShowExtraPage).name == 'English Breakfast'

		cleanup:
		extraDataService.delete(extra.id)
	}

	def 'check extra in listing'() {
		given:
		Extra extra = saveExtra('Breakfast')

		when:
		ShowExtraPage page = to ShowExtraPage, extra.id
		page.nav.extras()

		then:
		waitFor {
			at ExtraListPage
		}


		when:
		ExtraListPage listPage = browser.page ExtraListPage

		then:
		listPage.table.numberOfRows() == 1

		when:
		String name = listPage.extraAt(0)

		then:
		name == 'Breakfast'

		cleanup:
		extraDataService.delete(extra.id)
	}

	def 'show extra'() {
		given:
		Extra extra = saveExtra('Breakfast')
		ExtraListPage page = to ExtraListPage

		when:
		page.table.select('Breakfast')

		then:
		waitFor {
			at ShowExtraPage
		}

		cleanup:
		extraDataService.delete(extra.id)
	}

	def 'delete extra'() {
		given:
		Extra extra = saveExtra('Breakfast')
		ShowExtraPage page = to ShowExtraPage, extra.id

		when:
		withConfirm { page.buttons.delete() }

		then:
		waitFor {
			at ExtraListPage
		}

		when:
		ExtraListPage listPage = browser.page ExtraListPage

		then:
		listPage.message ==~ /Extra .+ deleted/
		listPage.table.numberOfRows() == 0
	}
}
