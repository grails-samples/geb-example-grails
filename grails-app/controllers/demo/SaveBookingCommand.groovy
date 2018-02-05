package demo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.CompileDynamic

@GrailsCompileStatic
class SaveBookingCommand implements Validateable {

    String name
    String email
    String telephone

    Date arrival
    Date departure

    int adults
    int children
    int babys

    List<Long> rooms
    List<Long> extras

    static constraints = {
        name nullable: false, blank: false
        email nullable: false, blank: false, email: true
        telephone nullable: true
        arrival nullable: false
        departure nullable: false
        adults min: 1
        children min: 0
        babys min: 0
        rooms nullable: false, minSize: 1
        extras nullable: true, minSize: 0
    }

    Object asType(Class clazz) {
        if (clazz == Booking) {
            Booking booking = new Booking()
            copyProperties(this, booking)
            return booking
        }
        super.asType(clazz)
    }

    @SuppressWarnings('MethodParameterTypeRequired')
    @CompileDynamic
    def copyProperties(source, target) {
        source.properties.each { key, value ->
            if (target.hasProperty(key) && !(key in ['class', 'metaClass', 'rooms', 'extras'])) {
                target[key] = value
            }
        }
    }
}
