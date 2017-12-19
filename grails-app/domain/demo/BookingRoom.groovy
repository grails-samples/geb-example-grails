package demo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BookingRoom {
    Booking booking
    Room room

    static mapping = {
        booking nullable: false
        room nullable: false
    }
}