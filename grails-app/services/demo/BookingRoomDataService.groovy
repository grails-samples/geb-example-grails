package demo

import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.CompileStatic

@SuppressWarnings('ComparisonWithSelf')
@CompileStatic
@Service(BookingRoom)
interface BookingRoomDataService {

    BookingRoom save(Booking booking, Room room)

    void delete(Booking booking, Room room)

    @Where({ booking == booking })
    void delete(Booking booking)

    List<Room> findBookingRoomRoom(Booking booking)
}
