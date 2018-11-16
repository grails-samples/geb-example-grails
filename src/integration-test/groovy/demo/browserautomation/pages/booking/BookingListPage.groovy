package demo.browserautomation.pages.booking

import demo.browserautomation.modules.CreateModule
import demo.browserautomation.modules.Nav
import demo.browserautomation.modules.TableModule
import geb.Page

class BookingListPage extends Page {

	static url = '/booking/index'

	static at = {
		title == 'Booking List'
	}

	static content = {
		buttons { $('fieldset.buttons').module(CreateModule) }
		table { $('div.content', 0).module(TableModule) }
		nav { $('div.nav').module(Nav) }
		message { $('div.message').text() }
	}
}
