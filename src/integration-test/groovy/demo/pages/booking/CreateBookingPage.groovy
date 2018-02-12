package demo.pages.booking

import geb.Page
import geb.module.Checkbox
import geb.module.TextInput

import java.time.LocalDate

class CreateBookingPage extends Page {

	static at = {
		title == 'Create Booking'
	}

	static url = "/booking/create"

	static content = {
		inputField { $('input', name: it).module(TextInput) }
		emailField { $('input', type: 'email', name: it) }
		numberField { $('input', type: 'number', name: it) }
		saveButton { $('input', type: 'submit') }
		selectDay { $('select', name: "${it}_day") }
		selectMonth { $('select', name: "${it}_month") }
		selectYear { $('select', name: "${it}_year") }
		field { $('li.fieldcontain .property-label', text: it).parent() }
		fieldCheckbox { field(it).find('input', type: 'checkbox', 0).module(Checkbox) }
	}

	void check(String name) {
		fieldCheckbox(name).check()
	}

	void uncheck(String name) {
		fieldCheckbox(name).uncheck()
	}

	void populateDate(String name, int day, int month, int year) {
		selectDay(name).value(day)
		selectMonth(name).value(month)
		selectYear(name).value(year)
	}

	void populate(String inputFieldName, String value) {
		inputField(inputFieldName).text = value
	}

	void populateNumber(String inputFieldName, int value) {
		numberField(inputFieldName).value(value)
	}

	void populateEmail(String inputFieldName, String value) {
		emailField(inputFieldName).value(value)
	}

	void save() {
		saveButton.click()
	}

	void setName(String name) {
		populate('name', name)
	}

	void setAdults(int adults) {
		populateNumber('adults', adults)
	}

	void setEmail(String email) {
		populateEmail('email', email)
	}

	void setArrival(LocalDate arrival) {
		populateDate('arrival', arrival.day, arrival.month.value, arrival.year)
	}

	void setDeparture(LocalDate departure) {
		populateDate('departure', departure.day, departure.month.value, departure.year)
	}
}
