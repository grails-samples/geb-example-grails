package demo.browserautomation.pages.room

import demo.browserautomation.pages.EditPage

class EditRoomPage extends EditPage {

    static at = { title == 'Edit Room' }

    static url = '/room/edit'
}
