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

    List<Extra> findBookingExtraExtra(Booking booking)

    @Query("""
select $bookingExtra.id 
from ${BookingExtra bookingExtra} 
inner join ${Booking b = bookingExtra.booking}  
where $b.id = $bookingId""")
    List<Serializable> findBookingExtraIdByBookingId(Serializable bookingId)
}
