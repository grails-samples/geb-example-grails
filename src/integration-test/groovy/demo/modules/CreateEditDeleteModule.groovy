package demo.modules

import demo.pages.booking.CreateBookingPage
import demo.pages.booking.BookingEditPage
import geb.Module

class CreateEditDeleteModule extends Module {
    static content = {
        newEntityButton(to: CreateBookingPage) { $('a', text: contains('New')) }
        editButton(to: BookingEditPage) { $('a', text: 'Edit') }
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
