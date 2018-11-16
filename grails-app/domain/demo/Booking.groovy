package demo

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
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
        name nullable: false, blank: false, maxSize: 255
        email nullable: false, blank: false, email: true, maxSize: 255
        telephone nullable: true, maxSize: 255
        arrival nullable: false
        departure nullable: false
        adults min: 1
        children min: 0
        babys min: 0
    }
}
