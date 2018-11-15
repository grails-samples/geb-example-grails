package demo

import demo.pages.room.CreateRoomPage
import demo.pages.room.EditRoomPage
import demo.pages.room.RoomListPage
import demo.pages.room.ShowRoomPage
import geb.spock.GebReportingSpec
import grails.testing.mixin.integration.Integration
import spock.lang.IgnoreIf
import spock.lang.Requires
import spock.lang.Stepwise

@Integration
@Stepwise
@Requires({ sys['geb.env'] })
class RoomCRUDSpec extends GebReportingSpec {

	def 'there are no rooms'() {
		when:
		RoomListPage page = to RoomListPage

		then:
		page.table.numberOfRows() == 0
	}

	def 'add a room'() {
		given:
		RoomListPage page = page RoomListPage

		when:
		page.buttons.create()

		then:
		waitFor {
			at CreateRoomPage
		}
	}

	def 'enter the details'() {
		given:
		CreateRoomPage page = page CreateRoomPage

		when:
		page.name = 'Room 101'
		page.save()

		then:
		waitFor {
			at ShowRoomPage
		}
	}

	def 'check the entered details'() {
		given:
		ShowRoomPage page = page ShowRoomPage

		expect:
		page.name == 'Room 101'
	}

	def 'edit the details'() {
		given:
		ShowRoomPage page = page ShowRoomPage

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
	}

	def 'check in listing'() {
		when:
		ShowRoomPage page = page ShowRoomPage
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
		name == 'Room101'
	}

	def 'show row'() {
		given:
		RoomListPage page = page RoomListPage

		when:
		page.table.select('Room101')

		then:
		waitFor {
			at ShowRoomPage
		}
	}

	def 'delete room'() {
		given:
		ShowRoomPage page = page ShowRoomPage

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
