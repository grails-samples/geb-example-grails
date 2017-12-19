package demo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Extra {
    String name

    static constraints = {
        name nullable: false, blank: false
    }
}