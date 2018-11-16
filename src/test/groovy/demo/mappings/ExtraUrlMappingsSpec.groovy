package demo.mappings

import demo.ExtraController
import demo.UrlMappings
import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification

class ExtraUrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(ExtraController)
    }

    void "extra controller mappings"() {
        expect:
        verifyForwardUrlMapping("/extra/index", controller: 'extra', action: 'index')

        and:
        verifyForwardUrlMapping("/extra/create", controller: 'extra', action: 'create')

        and:
        verifyForwardUrlMapping("/extra/save", controller: 'extra', action: 'save')

        and:
        verifyForwardUrlMapping("/extra/update", controller: 'extra', action: 'update')

        and:
        verifyForwardUrlMapping("/extra/delete", controller: 'extra', action: 'delete')

        and:
        verifyForwardUrlMapping("/extra/show/123", controller: 'extra', action: 'show') {
            id = '123'
        }

        and:
        verifyForwardUrlMapping("/extra/edit/123", controller: 'extra', action: 'edit') {
            id = '123'
        }
    }
}
