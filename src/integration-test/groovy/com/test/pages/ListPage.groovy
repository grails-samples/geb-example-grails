package com.test.pages

import geb.Module

class ListPage extends ScaffoldPage {
	static at = {
		title ==~ /.+ List/
	}
	
	static content = {
		newEntityButton(to: CreatePage) { $("a", text: contains('New')) }
		entityTable(required: false) { $("div.content table", 0) }
		entityRow { entityRows[it].module EntityRow }
		entityRows(required: false) { entityTable.find("tbody").find("tr") }
	}

	void select(String name, int columnIndex = 0) {
		for ( int i = 0; i < numberOfRows(); i++ ) {
			def entityRow = entityRow(i)
			if ( entityRow.cellText(columnIndex) == name ) {
				entityRow.showLink().click()
				break
			}
		}
	}

	int numberOfRows() {
		if ( entityTable.empty ) {
			return 0
		}
		entityRows.size()
	}

	void newEntity() {
		newEntityButton.click()
	}
}

class EntityRow extends Module {
	static content = {
		cell { $("td", it) }
		cellText { cell(it).text() }
		cellHrefText{ cell(it).find('a').text() }
		showLink(to: ShowPage) { cell(0).find("a") }
	}
}
