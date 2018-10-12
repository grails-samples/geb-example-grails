package demo

import grails.gorm.services.Query
import grails.gorm.services.Service
import groovy.transform.CompileStatic

@SuppressWarnings('ComparisonWithSelf')
@CompileStatic
@Service(BookingRoom)
interface BookingRoomDataService {

    BookingRoom save(Booking booking, Room room)

    void delete(Booking booking, Room room)

    Number delete(Serializable id)

    @Query("""
select $room.name 
from ${BookingRoom bookingRoom}
inner join ${Room room = bookingRoom.room}
inner join ${Booking b = bookingRoom.booking}  
where $b = $booking""")
    List<String> findBookingRoomNameByBooking(Booking booking)

    @Query("""
select $room
from ${BookingRoom bookingRoom}
inner join ${Room room = bookingRoom.room}
inner join ${Booking b = bookingRoom.booking}  
where $b = $booking""")
    List<Room> findRoomByBooking(Booking booking)

    @Query("""
select $bookingRoom.id
from ${BookingRoom bookingRoom}
inner join ${Booking b = bookingRoom.booking}  
where $b.id = $bookingId""")
    List<Serializable> findBookingRoomIdByBookingId(Serializable bookingId)
}
