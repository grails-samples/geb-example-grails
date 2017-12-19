package demo

import grails.validation.Validateable

class NameIdCommand implements Validateable {
    Long id
    String name

    static constraints = {
        id nullable: false
        name nullable: false, blank: false
    }
}