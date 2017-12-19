package com.test.pages

import geb.module.TextInput

class EditPage extends ScaffoldPage {

	static at = {
		heading.text() ==~ /Edit.+/
	}
	
	static content = {
		inputField { $("input", name: it).module(TextInput) }
		updateButton(to: ShowPage) { $("input", value: "Update") }
		deleteButton(to: ListPage) { $("input", value: "Delete") }
	}

    void update() {
		updateButton.click()
	}

	void populate(String inputFieldName, String value) {
		inputField(inputFieldName).text = value
	}
}