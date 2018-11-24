package demo.controllers.allowedMethods

import demo.ExtraController
import demo.ExtraDataService
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import spock.lang.Unroll

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_MOVED_TEMPORARILY
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import static javax.servlet.http.HttpServletResponse.SC_OK

class ExtraControllerAllowedMethodsSpec extends Specification implements ControllerUnitTest<ExtraController> {

    @Unroll
    def "test ExtraController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test ExtraController.index accepts GET requests"() {
        given:
        controller.extraDataService = Mock(ExtraDataService)

        when:
        request.method = 'GET'
        controller.index()

        then:
        response.status == SC_NOT_FOUND
    }

    @Unroll
    def "test ExtraController.create does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.create()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test ExtraController.create accepts GET requests"() {
        given:
        controller.extraDataService = Mock(ExtraDataService)

        when:
        request.method = 'GET'
        controller.create()

        then:
        response.status == SC_OK
    }

    @Unroll
    def "test ExtraController.show does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.show()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test ExtraController.show accepts GET requests"() {
        given:
        controller.extraDataService = Mock(ExtraDataService)

        when:
        request.method = 'GET'
        controller.show()

        then:
        response.status == SC_NOT_FOUND
    }

    @Unroll
    def "test ExtraController.edit does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.edit()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test ExtraController.edit accepts GET requests"() {
        given:
        controller.extraDataService = Mock(ExtraDataService)

        when:
        request.method = 'GET'
        controller.edit()

        then:
        response.status == SC_NOT_FOUND
    }

    @Unroll
    def "test ExtraController.save does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.save()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test ExtraController.save accepts POST requests"() {
        given:
        controller.extraDataService = Mock(ExtraDataService)

        when:
        request.method = 'POST'
        controller.save()

        then:
        response.status == SC_OK
    }

    @Unroll
    def "test ExtraController.update does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.update()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'POST']
    }

    def "test ExtraController.update accepts PUT requests"() {
        given:
        controller.extraDataService = Mock(ExtraDataService)

        when:
        request.method = 'PUT'
        controller.update()

        then:
        response.status == SC_OK
    }


    @Unroll
    def "test ExtraController.delete does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.delete()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'PUT', 'GET', 'POST']
    }

    def "test ExtraController.delete accepts DELETE requests"() {
        given:
        controller.extraDataService = Mock(ExtraDataService)

        when:
        request.method = 'DELETE'
        controller.delete()

        then:
        response.status == SC_MOVED_TEMPORARILY
    }

}
