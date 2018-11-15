package demo

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
@GrailsCompileStatic
class Room {
    String name

    static constraints = {
        name nullable: false, blank: false, maxSize: 255
    }
}
