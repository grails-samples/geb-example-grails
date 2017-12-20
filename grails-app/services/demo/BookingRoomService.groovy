package demo

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.CompileStatic

@CompileStatic
@Service(BookingRoom)
interface BookingRoomService {

    BookingRoom save(Booking booking, Room room)

    void delete(Booking booking, Room room)

    @Where({booking == booking})
    void delete(Booking booking)

    List<Room> findBookingRoomRoom(Booking booking)

}