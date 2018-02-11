package demo.pages.extra

import demo.modules.UpdateDeleteModule
import geb.Page
import geb.module.TextInput

class EditExtraPage extends Page {

    static at = {
        title == 'Edit Extra'
    }

    static url = '/extra/edit'

    String convertToPath(Object[] args) {
        args ? "/${args[0]}" : ""
    }

    static content = {
        inputField { $('input', name: it).module(TextInput) }
        buttons { $('fieldset.buttons').module(UpdateDeleteModule) }
    }

    void populate(String inputFieldName, String value) {
        inputField(inputFieldName).text = value
    }

    void setName(String name) {
        populate('name', name)
    }

}
