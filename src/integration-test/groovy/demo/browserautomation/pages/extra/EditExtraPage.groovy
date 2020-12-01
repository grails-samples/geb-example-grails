package demo.browserautomation.pages.extra

import demo.browserautomation.pages.EditPage

class EditExtraPage extends EditPage {

    static at = {
        title == 'Edit Extra'
    }

    static url = '/extra/edit'
}
