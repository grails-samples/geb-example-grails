package demo.pages.booking

import demo.modules.UpdateDeleteModule
import geb.Page
import geb.module.Checkbox
import geb.module.TextInput

class BookingEditPage extends Page {

	static at = {
		title == 'Edit Booking'
	}

	static url = '/booking/edit'

	String convertToPath(Object[] args) {
		args ? "/${args[0]}" : ''
	}

	static content = {
		inputField { $('input', name: it).module(TextInput) }
		buttons { $('fieldset.buttons').module(UpdateDeleteModule) }
		field { $('li.fieldcontain .property-label', text: it).parent() }
		fieldCheckbox { field(it).find('input', type: 'checkbox', 0).module(Checkbox) }
	}

	void check(String name) {
		fieldCheckbox(name).check()
	}

	void uncheck(String name) {
		fieldCheckbox(name).uncheck()
	}

	void populate(String inputFieldName, String value) {
		inputField(inputFieldName).text = value
	}

	void setName(String name) {
		populate('name', name)
	}
}
