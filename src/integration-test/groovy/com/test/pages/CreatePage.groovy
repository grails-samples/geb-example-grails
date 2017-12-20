package com.test.pages

import geb.module.Checkbox
import geb.module.TextInput
import geb.navigator.Navigator

class CreatePage extends ScaffoldPage {

	static at = {
		title ==~ /Create.+/
	}
	
	static content = {
		inputField { $("input", name: it).module(TextInput) }
		emailField { $("input", type: 'email', name: it) }
		numberField { $('input', type: 'number', name: it) }
		saveButton(to: ShowPage) { $("input", type: "submit") }
		selectDay { $("select", name: "${it}_day") }
		selectMonth { $("select", name: "${it}_month") }
		selectYear { $("select", name: "${it}_year") }
		field { $("li.fieldcontain .property-label", text: it).parent() }
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
}
