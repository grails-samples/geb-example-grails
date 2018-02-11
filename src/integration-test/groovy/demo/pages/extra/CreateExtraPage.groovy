package demo.pages.extra

import geb.Page
import geb.module.TextInput

class CreateExtraPage extends Page {

    static at = {
        title == 'Create Extra'
    }

    static url = '/extra/create'

    static content = {
        inputField { $('input', name: it).module(TextInput) }
        saveButton { $('input', type: 'submit') }
    }

    void setName(String name) {
        populate('name', name)
    }

    void save() {
        saveButton.click()
    }

    protected void populate(String inputFieldName, String value) {
        inputField(inputFieldName).text = value
    }
}
