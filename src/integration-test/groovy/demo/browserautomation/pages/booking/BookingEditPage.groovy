package demo.browserautomation.pages.booking

import demo.browserautomation.TextInputUtils
import demo.browserautomation.modules.UpdateDeleteModule
import geb.Page
import geb.module.Checkbox
import geb.module.TextInput

class BookingEditPage extends Page implements TextInputUtils {

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

	void setName(String name) {
		clearTextInput('name')
		populate('name', name)
	}
}
