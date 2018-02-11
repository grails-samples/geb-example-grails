package demo.modules

import geb.Module

class CreateEditDeleteModule extends Module {
    static content = {
        newEntityButton { $('a', text: contains('New')) }
        editButton { $('a', text: 'Edit') }
        deleteButton { $('input', value: 'Delete') }
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
