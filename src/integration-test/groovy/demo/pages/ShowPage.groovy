package demo.pages

class ShowPage extends ScaffoldPage {

	static at = {
		heading.text() ==~ /Show .+/
	}

	static url = "/"

	String convertToPath(Object[] args) {
		(args && args.size() >= 2) ? "${args[0]}/${args[1]}" : ""
	}
	
	static content = {
		newEntityButton(to: CreatePage) { $('a', text: contains('New')) }
		editButton(to: EditPage) { $('a', text: 'Edit') }
		deleteButton { $('input', value: 'Delete') }
		row { $('li.fieldcontain .property-label', text: it).parent() }
		value { row(it).find('.property-value').text() }
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
