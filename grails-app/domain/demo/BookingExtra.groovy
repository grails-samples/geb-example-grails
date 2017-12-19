package demo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BookingExtra {
    Booking booking
    Extra extra

    static mapping = {
        booking nullable: false
        extra nullable: false
    }
}