package demo.pages.room

import demo.modules.CreateEditDeleteModule
import demo.modules.Nav
import geb.Page

class ShowRoomPage extends Page {
    static at = {
        title == 'Show Room'
    }

    static url = '/room/show'

    String convertToPath(Object[] args) {
        args ? "/${args[0]}" : ''
    }

    static content = {
        row { $('li.fieldcontain .property-label', text: it).parent() }
        value { row(it).find('.property-value').text() }
        buttons { $('fieldset.buttons').module(CreateEditDeleteModule) }
        nav { $('div.nav').module(Nav) }
    }

    String getName() {
        value('Name')
    }
}
