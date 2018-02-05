package demo.pages

import geb.module.Checkbox
import geb.module.TextInput

class EditPage extends ScaffoldPage {

	static at = {
		heading.text() ==~ /Edit.+/
	}

	static url = "/"

	String convertToPath(Object[] args) {
		args ? "${args[0]}/edit" : ""
	}
	
	static content = {
		inputField { $('input', name: it).module(TextInput) }
		updateButton(to: ShowPage) { $('input', value: 'Update') }
		deleteButton(to: ListPage) { $('input', value: 'Delete') }
		field { $('li.fieldcontain .property-label', text: it).parent() }
		fieldCheckbox { field(it).find('input', type: 'checkbox', 0).module(Checkbox) }
	}

	void check(String name) {
		fieldCheckbox(name).check()
	}

	void uncheck(String name) {
		fieldCheckbox(name).uncheck()
	}

    void update() {
		updateButton.click()
	}

	void populate(String inputFieldName, String value) {
		inputField(inputFieldName).text = value
	}
}
