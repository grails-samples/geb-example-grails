package demo.pages.booking

import demo.modules.CreateEditDeleteModule
import demo.modules.Nav
import geb.Page

class BookingShowPage extends Page {
	static at = {
		title == 'Show Booking'
	}

	static url = '/booking/show'

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

	String getAdults() {
		value('Adults')
	}

	String getEmail() {
		value('Email')
	}

	String getArrival() {
		value('Arrival')
	}

	String getDeparture() {
		value('Departure')
	}

	String getRooms() {
		value('Rooms')
	}

	String getExtras() {
		value('Extras')
	}
}
