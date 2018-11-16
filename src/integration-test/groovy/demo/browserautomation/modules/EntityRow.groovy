package demo.browserautomation.modules

import geb.Module

class EntityRow  extends Module {
    static content = {
        cell { $('td', it) }
        cellText { cell(it).text() }
        cellHrefText { cell(it).find('a').text() }
        showLink { cell(0).find('a') }
    }
}
