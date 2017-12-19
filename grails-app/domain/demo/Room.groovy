package demo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Room {
    String name

    static constraints = {
        name nullable: false, blank: false
    }
}