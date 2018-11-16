package demo

import demo.Booking
import demo.BookingDataService
import groovy.transform.CompileStatic

@CompileStatic
trait BookingFixture {

    abstract BookingDataService getBookingDataService()

    Booking saveBooking(String name,
                        String email,
                        Date arrival,
                        Date departure) {
        bookingDataService.save(new Booking(name: name,
                email: email,
                arrival: arrival,
                departure: departure))
    }
}
