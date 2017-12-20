package com.test.pages

class ShowPage extends ScaffoldPage {

	static at = {
		heading.text() ==~ /Show .+/
	}
	
	static content = {
		newEntityButton(to: CreatePage) { $("a", text: contains('New')) }
		editButton(to: EditPage) { $("a", text: "Edit") }
		deleteButton { $("input", value: "Delete") }
		row { $("li.fieldcontain .property-label", text: it).parent() }
		value { row(it).find(".property-value").text() }
		nav { $('div.nav').module(ScaffoldNav) }
	}

	void create() {
		newEntityButton.click()
	}

	void edit() {
		editButton.click()
	}

	void delete() {
		deleteButton.click()
	}
}
