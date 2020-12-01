package demo.browserautomation

import geb.module.TextInput

trait TextInputUtils {

    void populate(String elName, String value) {
        def el = $('input', name: elName).module(TextInput)
        for (char c : value.toCharArray()) {
            el << '' + c
        }
    }

    void clearTextInput(String elName) {
        def el = $('input', name: elName).module(TextInput)
        el.setText('')
        sleep(500)
    }
}
