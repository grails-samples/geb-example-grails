package demo.modules

import demo.pages.booking.CreateBookingPage
import geb.Module

class CreateModule extends Module {
    static content = {
        newEntityButton(to: CreateBookingPage) { $('a', text: contains('New')) }
    }

    void create() {
        newEntityButton.click()
    }
}
