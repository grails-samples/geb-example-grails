package demo.pages.extra

import demo.modules.CreateModule
import demo.modules.Nav
import demo.modules.TableModule
import geb.Page

class ExtraListPage extends Page {

    static url = '/extra/index'

    static at = {
        title == 'Extra List'
    }

    static content = {
        table { $('div.content', 0).module(TableModule) }
        buttons { $('fieldset.buttons').module(CreateModule) }
        nav { $('div.nav').module(Nav) }
        message { $('div.message').text() }
    }

    String extraAt(int row) {
        table.textAt(0, 0)
    }

}
