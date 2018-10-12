package demo

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
@Transactional
class BookingService {

    BookingDataService bookingDataService
    RoomDataService roomDataService
    ExtraDataService extraDataService
    BookingRoomDataService bookingRoomDataService
    BookingExtraDataService bookingExtraDataService

    @Transactional
    Booking save(Booking bookingInstance, List<Long> roomIds, List<Long> extraIds) {
        Booking booking = bookingDataService.save(bookingInstance)
        if ( roomIds ) {
            List<Room> rooms = roomDataService.find(roomIds)
            rooms?.each { Room room ->
                bookingRoomDataService.save(booking, room)
            }
        }

        if ( extraIds ) {
            List<Extra> extras = extraDataService.find(extraIds)

            extras?.each { Extra extra ->
                bookingExtraDataService.save(booking, extra)
            }
        }

        booking
    }

    @Transactional
    Booking delete(Long bookingId) {
        Booking booking = bookingDataService.get(bookingId)
        List<Serializable> bookingExtraIds = bookingExtraDataService.findBookingExtraIdByBookingId(bookingId)
        int numberOfBookingExtraDeleted = 0
        for (Serializable bookingExtraId : bookingExtraIds) {
            numberOfBookingExtraDeleted += bookingExtraDataService.delete(bookingExtraId)
        }
        List<Serializable> bookingRoomIds = bookingRoomDataService.findBookingRoomIdByBookingId(bookingId)
        int numberOfBookingRoomDeleted = 0
        for (Serializable bookingRoomId : bookingRoomIds) {
            numberOfBookingRoomDeleted += bookingRoomDataService.delete(bookingRoomId)
        }
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
        List<Extra> previousExtraList = bookingExtraDataService.findBookingExtraExtra(booking)
        List<Long> previousExtraIds = previousExtraList*.id

        List<Long> extraIdsToBeInserted = extraIds - previousExtraIds

        if ( extraIdsToBeInserted ) {
            List<Extra> extras = extraDataService.find(extraIdsToBeInserted)
            extras?.each { Extra extra ->
                bookingExtraDataService.save(booking, extra)
            }
        }

        List<Long> extraIdsToBeDeleted = previousExtraIds - extraIds
        if ( extraIdsToBeDeleted ) {
            List<Extra> extras = extraDataService.find(extraIdsToBeDeleted)
            extras?.each { Extra extra ->
                bookingExtraDataService.delete(booking, extra)
            }
        }
    }

    @Transactional
    protected void updateRooms(Booking booking, List<Long> roomIds) {
        List<Room> previousRoomList = bookingRoomDataService.findBookingRoomRoom(booking)
        List<Long> previousRoomIds = previousRoomList*.id

        List<Long> roomIdsToBeInserted = roomIds - previousRoomIds

        if ( roomIdsToBeInserted ) {
            List<Room> rooms = roomDataService.find(roomIdsToBeInserted)
            rooms?.each { Room room ->
                bookingRoomDataService.save(booking, room)
            }
        }

        List<Long> roomIdsToBeDeleted = previousRoomIds - roomIds
        if ( roomIdsToBeDeleted ) {
            List<Room> rooms = roomDataService.find(roomIdsToBeDeleted)
            rooms?.each { Room room ->
                bookingRoomDataService.delete(booking, room)
            }
        }
    }
}
