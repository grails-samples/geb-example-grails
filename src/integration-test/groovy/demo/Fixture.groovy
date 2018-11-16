package demo

import groovy.transform.CompileStatic

import java.time.LocalDate
import java.time.ZoneId

@CompileStatic
trait Fixture {
    abstract ExtraDataService getExtraDataService()

    abstract RoomDataService getRoomDataService()

    abstract BookingService getBookingService()

    abstract BookingDataService getBookingDataService()


    void deleteBookingById(Long id) {
        bookingService.delete(id)
    }

    void deleteBookingByName(String name) {
        deleteBookingById(bookingDataService.list([:]).find { it.name == 'Tim' }?.id)
    }

    Booking saveBooking(String name = 'Tim',
                                Integer adults = 2,
                                String email = 'tim@apple.com',
                                LocalDate arrival = LocalDate.of(2017, 12, 30),
                                LocalDate departure = LocalDate.of(2017, 12, 31),
                                List<String> roomNames = ['Room 101', 'Room 102'],
                                List<String> extraNames = ['Breakfast']
    ) {
        List<Long> roomIds = roomDataService.list([:]).findAll { roomNames.contains(it.name) }*.id
        List<Long> extraIds = extraDataService.list([:]).findAll { extraNames.contains(it.name) }*.id
        bookingService.save(new Booking(name: name,
                adults: adults,
                email: email,
                arrival: Date.from(arrival.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                departure: Date.from(departure.atStartOfDay(ZoneId.systemDefault()).toInstant())), roomIds, extraIds)
    }




}
