package demo.browserautomation.pages

import demo.browserautomation.TextInputUtils
import demo.browserautomation.modules.UpdateDeleteModule
import geb.Page
import geb.module.TextInput

class EditPage extends Page implements TextInputUtils {
    static content = {
        inputField { $('input', name: it).module(TextInput) }
        buttons { $('fieldset.buttons').module(UpdateDeleteModule) }
    }

    void setName(String name) {
        clearTextInput('name')
        populate('name', name)
    }

    String getName() {
        inputField('name').text
    }
}
