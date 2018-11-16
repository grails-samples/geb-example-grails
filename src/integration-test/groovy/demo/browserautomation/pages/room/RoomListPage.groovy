package demo.browserautomation.pages.room

import demo.browserautomation.modules.CreateModule
import demo.browserautomation.modules.TableModule
import demo.browserautomation.modules.Nav
import geb.Page

class RoomListPage extends Page {

    static url = '/room/index'

    static at = {
        title == 'Room List'
    }

    static content = {
        table { $('div.content', 0).module(TableModule) }
        buttons { $('fieldset.buttons').module(CreateModule) }
        nav { $('div.nav').module(Nav) }
        message { $('div.message').text() }
    }

    String roomAt(int row) {
        table.textAt(0, 0)
    }
}
