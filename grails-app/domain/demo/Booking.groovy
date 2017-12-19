package demo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Booking {
    String name
    String email
    String telephone
    Date arrival
    Date departure
    int adults = 1
    int children = 0
    int babys = 0

    static constraints = {
        name nullable: false, blank: false
        email nullable: false, blank: false, email: true
        telephone nullable: true
        arrival nullable: false
        departure nullable: false
        adults min: 1
        children min: 0
        babys min: 0
    }
}