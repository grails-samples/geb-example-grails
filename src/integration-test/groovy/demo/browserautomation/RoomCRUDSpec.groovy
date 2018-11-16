package demo.browserautomation

import demo.LeakageDetector
import demo.Room
import demo.RoomDataService
import demo.RoomFixture
import demo.browserautomation.pages.room.CreateRoomPage
import demo.browserautomation.pages.room.EditRoomPage
import demo.browserautomation.pages.room.RoomListPage
import demo.browserautomation.pages.room.ShowRoomPage
import geb.spock.GebReportingSpec
import grails.core.GrailsApplication
import grails.testing.mixin.integration.Integration
import groovy.util.logging.Slf4j
import spock.lang.Requires

@Integration
@Requires({ sys['geb.env'] })
@Slf4j
class RoomCRUDSpec extends GebReportingSpec implements LeakageDetector, RoomFixture {

	GrailsApplication grailsApplication

	RoomDataService roomDataService

	def setup() {
		setupLeakage()
	}

	def cleanup() {
		verifyLeakage()
	}

	def 'there are no rooms'() {
		when:
		RoomListPage page = to RoomListPage

		then:
		page.table.numberOfRows() == 0
	}

	def 'add a room'() {
		given:
		RoomListPage page = to RoomListPage

		when:
		page.buttons.create()

		then:
		waitFor {
			at CreateRoomPage
		}
	}

	def 'enter the details'() {
		given:
		CreateRoomPage page = to CreateRoomPage

		when:
		page.name = 'Room 101'
		page.save()

		then:
		waitFor {
			at ShowRoomPage
		}

		cleanup:
		roomDataService.delete(roomDataService.list([:]).find { it.name == 'Room 101' }?.id)
	}

	def 'check the entered details'() {
		given:
		Room room = saveRoom('Room 101')

		ShowRoomPage page =  to ShowRoomPage, room.id

		expect:
		page.name == 'Room 101'

		cleanup:
		roomDataService.delete(room.id)
	}

	def 'edit the details'() {
		given:
		Room room = saveRoom('Room 101')
		ShowRoomPage page = to ShowRoomPage, room.id

		when:
		page.buttons.edit()

		then:
		waitFor {
			at EditRoomPage
		}

		when:
		EditRoomPage editPage = browser.page(EditRoomPage)
		editPage.name = 'Room101'
		editPage.buttons.update()

		then:
		waitFor {
			at ShowRoomPage
		}

		cleanup:
		roomDataService.delete(room.id)
	}

	def 'check in listing'() {
		given:
		Room room = saveRoom('Room 101')

		when:
		ShowRoomPage page = to ShowRoomPage, room.id
		page.nav.rooms()
		
		then:
		waitFor {
			at RoomListPage
		}

		when:
		RoomListPage listPage = browser.page RoomListPage

		then:
		listPage.table.numberOfRows() == 1

		when:
		String name = listPage.roomAt(0)

		then:
		name == 'Room 101'

		cleanup:
		roomDataService.delete(room.id)
	}

	def 'show row'() {
		given:
		Room room = saveRoom('Room 101')

		RoomListPage page = to RoomListPage

		when:
		page.table.select('Room 101')

		then:
		waitFor {
			at ShowRoomPage
		}

		cleanup:
		roomDataService.delete(room.id)
	}

	def 'delete room'() {
		given:
		Room room = saveRoom('Room 101')
		ShowRoomPage page = to ShowRoomPage, room.id

		when:
		withConfirm { page.buttons.delete() }

		then:
		waitFor {
			at RoomListPage
		}

		when:
		RoomListPage listPage = browser.page RoomListPage

		then:
		listPage.message ==~ /Room .+ deleted/
		listPage.table.numberOfRows() == 0
	}
}
