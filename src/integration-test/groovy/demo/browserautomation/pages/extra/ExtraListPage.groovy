package demo.browserautomation.pages.extra

import demo.browserautomation.modules.CreateModule
import demo.browserautomation.modules.Nav
import demo.browserautomation.modules.TableModule
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
