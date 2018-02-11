package demo.modules

import geb.Module

class CreateModule extends Module {
    static content = {
        newEntityButton { $('a', text: contains('New')) }
    }

    void create() {
        newEntityButton.click()
    }
}
