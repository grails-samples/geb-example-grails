package demo

import grails.gorm.services.Service
import groovy.transform.CompileStatic

@CompileStatic
@Service(Booking)
interface BookingDataService {

    Booking get(Serializable id)

    List<Booking> list(Map args)

    Long count()

    void delete(Serializable id)

    Booking save(Booking booking)
}
