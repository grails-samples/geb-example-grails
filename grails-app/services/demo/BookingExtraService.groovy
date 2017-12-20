package demo

import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.CompileStatic

@CompileStatic
@Service(BookingExtra)
interface BookingExtraService {
    BookingExtra save(Booking booking, Extra extra)

    void delete(Booking booking, Extra extra)

    @Where({booking == booking})
    void delete(Booking booking)

    List<Extra> findBookingExtraExtra(Booking booking)
}