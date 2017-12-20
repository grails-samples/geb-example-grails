package demo

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
@Transactional
class BookingService {

    BookingDataService bookingDataService
    RoomService roomService
    ExtraService extraService
    BookingRoomService bookingRoomService
    BookingExtraService bookingExtraService

    @Transactional
    Booking save(Booking bookingInstance, List<Long> roomIds, List<Long> extraIds) {

        Booking booking = bookingDataService.save(bookingInstance)

        if ( roomIds ) {
            List<Room> rooms = roomService.find(roomIds)
            rooms?.each { Room room ->
                bookingRoomService.save(booking, room)
            }
        }

        if ( extraIds ) {
            List<Extra> extras = extraService.find(extraIds)

            extras?.each { Extra extra ->
                bookingExtraService.save(booking, extra)
            }
        }

        booking
    }

    @Transactional
    Booking delete(Long bookingId) {
        Booking booking = bookingDataService.get(bookingId)
        bookingExtraService.delete(booking)
        bookingRoomService.delete(booking)
        bookingDataService.delete(bookingId)
        booking
    }

    @Transactional
    Booking update(Booking bookingInstance, List<Long> roomIds, List<Long> extraIds) {
        Booking booking = update(bookingInstance)
        updateRooms(booking, roomIds)
        updateExtras(booking, extraIds)
        booking
    }

    @Transactional
    protected Booking update(Booking bookingInstance) {
        Booking booking = bookingDataService.get(bookingInstance.id)
        booking.with {
            name = bookingInstance.name
            email = bookingInstance.email
            telephone = bookingInstance.telephone
            arrival = bookingInstance.arrival
            departure = bookingInstance.departure
            adults = bookingInstance.adults
            children = bookingInstance.children
            babys = bookingInstance.babys
        }
        bookingDataService.save(booking)
    }

    @Transactional
    protected void updateExtras(Booking booking, List<Long> extraIds) {
        List<Extra> previousExtraList = bookingExtraService.findBookingExtraExtra(booking)
        List<Long> previousExtraIds = previousExtraList*.id

        List<Long> extraIdsToBeInserted = extraIds - previousExtraIds

        if ( extraIdsToBeInserted ) {
            List<Extra> extras = extraService.find(extraIdsToBeInserted)
            extras?.each { Extra extra ->
                bookingExtraService.save(booking, extra)
            }
        }

        List<Long> extraIdsToBeDeleted = previousExtraIds - extraIds
        if ( extraIdsToBeDeleted ) {
            List<Extra> extras = extraService.find(extraIdsToBeDeleted)
            extras?.each { Extra extra ->
                bookingExtraService.delete(booking, extra)
            }
        }
    }

    @Transactional
    protected void updateRooms(Booking booking, List<Long> roomIds) {
        List<Room> previousRoomList = bookingRoomService.findBookingRoomRoom(booking)
        List<Long> previousRoomIds = previousRoomList*.id

        List<Long> roomIdsToBeInserted = roomIds - previousRoomIds

        if ( roomIdsToBeInserted ) {
            List<Room> rooms = roomService.find(roomIdsToBeInserted)
            rooms?.each { Room room ->
                bookingRoomService.save(booking, room)
            }
        }

        List<Long> roomIdsToBeDeleted = previousRoomIds - roomIds
        if ( roomIdsToBeDeleted ) {
            List<Room> rooms = roomService.find(roomIdsToBeDeleted)
            rooms?.each { Room room ->
                bookingRoomService.delete(booking, room)
            }
        }

    }
}
