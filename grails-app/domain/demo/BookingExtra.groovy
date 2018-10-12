package demo

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
@GrailsCompileStatic
class BookingExtra {
    Booking booking
    Extra extra

    static constraints = {
        booking nullable: false
        extra nullable: false
    }
}
