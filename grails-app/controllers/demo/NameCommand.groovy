package demo

import grails.validation.Validateable

class NameCommand implements Validateable {
    String name

    static constraints = {
        name nullable: false, blank: false
    }
}