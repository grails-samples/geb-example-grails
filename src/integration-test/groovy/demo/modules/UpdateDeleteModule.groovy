package demo.modules

import demo.pages.booking.BookingListPage
import demo.pages.booking.BookingShowPage
import geb.Module

class UpdateDeleteModule extends Module {
    static content = {
        updateButton(to: BookingShowPage) { $('input', value: 'Update') }
        deleteButton(to: BookingListPage) { $('input', value: 'Delete') }
    }

    void update() {
        updateButton.click()
    }

    void delete() {
        deleteButton.click()
    }
}
