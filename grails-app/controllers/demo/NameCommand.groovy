package demo

import grails.validation.Validateable
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class NameCommand implements Validateable {
    String name

    static constraints = {
        name nullable: false, blank: false
    }
}
