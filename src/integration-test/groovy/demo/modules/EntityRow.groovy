package demo.modules

import demo.pages.booking.BookingShowPage
import geb.Module

class EntityRow  extends Module {
    static content = {
        cell { $('td', it) }
        cellText { cell(it).text() }
        cellHrefText { cell(it).find('a').text() }
        showLink(to: BookingShowPage) { cell(0).find('a') }
    }
}
