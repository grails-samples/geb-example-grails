package demo

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.CompileStatic

@SuppressWarnings('ComparisonWithSelf')
@CompileStatic
@Service(BookingRoom)
interface BookingRoomDataService {

    BookingRoom save(Booking booking, Room room)

    void delete(Booking booking, Room room)

    Number delete(Serializable id)

    @Where({ booking == b })
    Number delete(Booking b)

    List<Room> findBookingRoomRoom(Booking booking)
    @Query("""
select $bookingRoom.id
from ${BookingRoom bookingRoom}
inner join ${Booking b = bookingRoom.booking}  
where $b.id = $bookingId""")
    List<Serializable> findBookingRoomIdByBookingId(Serializable bookingId)
}
