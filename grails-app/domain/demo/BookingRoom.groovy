package demo

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
@GrailsCompileStatic
class BookingRoom {
    Booking booking
    Room room

    static constraints = {
        booking nullable: false
        room nullable: false
    }
}
