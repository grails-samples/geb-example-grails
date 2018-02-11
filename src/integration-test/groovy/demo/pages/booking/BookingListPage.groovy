package demo.pages.booking

import demo.modules.CreateModule
import demo.modules.Nav
import demo.modules.TableModule
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
