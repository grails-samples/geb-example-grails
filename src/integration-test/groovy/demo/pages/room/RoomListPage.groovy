package demo.pages.room

import demo.modules.CreateModule
import demo.modules.TableModule
import demo.modules.Nav
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
