package demo.mappings

import demo.RoomController
import demo.UrlMappings
import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification

class RoomUrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(RoomController)
    }

    void "room controller mappings"() {
        expect:
        verifyForwardUrlMapping("/room/index", controller: 'room', action: 'index')

        and:
        verifyForwardUrlMapping("/room/create", controller: 'room', action: 'create')

        and:
        verifyForwardUrlMapping("/room/save", controller: 'room', action: 'save')

        and:
        verifyForwardUrlMapping("/room/update", controller: 'room', action: 'update')

        and:
        verifyForwardUrlMapping("/room/delete", controller: 'room', action: 'delete')

        and:
        verifyForwardUrlMapping("/room/show/123", controller: 'room', action: 'show') {
            id = '123'
        }

        and:
        verifyForwardUrlMapping("/room/edit/123", controller: 'room', action: 'edit') {
            id = '123'
        }
    }
}
