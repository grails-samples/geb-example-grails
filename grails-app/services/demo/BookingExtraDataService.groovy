package demo

import grails.gorm.services.Query
import grails.gorm.services.Service
import groovy.transform.CompileStatic

@SuppressWarnings('ComparisonWithSelf')
@CompileStatic
@Service(BookingExtra)
interface BookingExtraDataService {
    BookingExtra save(Booking booking, Extra extra)

    void delete(Booking booking, Extra extra)

    Number delete(Serializable id)

    @Query("""
select $extra.name 
from ${BookingExtra bookingExtra}
inner join ${Extra extra = bookingExtra.extra}  
inner join ${Booking b = bookingExtra.booking}  
where $b = $booking""")
    List<String> findExtraNameByBooking(Booking booking)

    @Query("""
select $extra 
from ${BookingExtra bookingExtra}
inner join ${Extra extra = bookingExtra.booking}  
inner join ${Booking b = bookingExtra.booking}  
where $b = $booking""")
    List<Extra> findExtraByBooking(Booking booking)

    @Query("""
select $bookingExtra.id 
from ${BookingExtra bookingExtra} 
inner join ${Booking b = bookingExtra.booking}  
where $b.id = $bookingId""")
    List<Serializable> findBookingExtraIdByBookingId(Serializable bookingId)
}
