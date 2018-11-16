package demo.controllers

import demo.BookingController
import demo.ExtraController
import demo.RoomController
import demo.UrlMappings
import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class ControllerCheckSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(BookingController)
        mockController(ExtraController)
        mockController(RoomController)
    }

    @Unroll
    void "#controllerName exists"() {
        expect:
        verifyController(controllerName)

        where:
        controllerName << ['booking', 'extra', 'room']
    }

    @Unroll
    void "#controllerName has #actionName action"() {
        expect:
        verifyAction(controllerName, actionName)

        where:
        controllerName | actionName
        'booking'      | 'index'
        'booking'      | 'show'
        'booking'      | 'create'
        'booking'      | 'save'
        'booking'      | 'edit'
        'booking'      | 'update'
        'booking'      | 'delete'
        'extra'        | 'index'
        'extra'        | 'show'
        'extra'        | 'create'
        'extra'        | 'save'
        'extra'        | 'edit'
        'extra'        | 'update'
        'extra'        | 'delete'
        'room'         | 'index'
        'room'         | 'index'
        'room'         | 'show'
        'room'         | 'create'
        'room'         | 'save'
        'room'         | 'edit'
        'room'         | 'update'
        'room'         | 'delete'
    }
}
