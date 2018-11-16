package demo.browserautomation.pages.room

import demo.browserautomation.modules.UpdateDeleteModule
import geb.Page
import geb.module.TextInput

class EditRoomPage extends Page {

    static at = { title == 'Edit Room' }

    static url = '/room/edit'

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
