package demo.controllers.allowedMethods

import demo.RoomController
import demo.RoomDataService
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import spock.lang.Unroll
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK
import static javax.servlet.http.HttpServletResponse.SC_MOVED_TEMPORARILY

class RoomControllerAllowedMethodsSpec extends Specification implements ControllerUnitTest<RoomController> {

    @Unroll
    def "test RoomController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test RoomController.index accepts GET requests"() {
        given:
        controller.roomDataService = Mock(RoomDataService)

        when:
        request.method = 'GET'
        controller.index()

        then:
        response.status == SC_NOT_FOUND
    }

    @Unroll
    def "test RoomController.create does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.create()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test RoomController.create accepts GET requests"() {
        given:
        controller.roomDataService = Mock(RoomDataService)

        when:
        request.method = 'GET'
        controller.create()

        then:
        response.status == SC_OK
    }

    @Unroll
    def "test RoomController.show does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.show()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test RoomController.show accepts GET requests"() {
        given:
        controller.roomDataService = Mock(RoomDataService)

        when:
        request.method = 'GET'
        controller.show()

        then:
        response.status == SC_NOT_FOUND
    }

    @Unroll
    def "test RoomController.edit does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.edit()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test RoomController.edit accepts GET requests"() {
        given:
        controller.roomDataService = Mock(RoomDataService)

        when:
        request.method = 'GET'
        controller.edit()

        then:
        response.status == SC_NOT_FOUND
    }

    @Unroll
    def "test RoomController.save does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.save()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test RoomController.save accepts POST requests"() {
        given:
        controller.roomDataService = Mock(RoomDataService)

        when:
        request.method = 'POST'
        controller.save()

        then:
        response.status == SC_OK
    }

    @Unroll
    def "test RoomController.update does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.update()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'POST']
    }

    def "test RoomController.update accepts PUT requests"() {
        given:
        controller.roomDataService = Mock(RoomDataService)

        when:
        request.method = 'PUT'
        controller.update()

        then:
        response.status == SC_OK
    }


    @Unroll
    def "test RoomController.delete does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.delete()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'PUT', 'GET', 'POST']
    }

    def "test RoomController.delete accepts DELETE requests"() {
        given:
        controller.roomDataService = Mock(RoomDataService)

        when:
        request.method = 'DELETE'
        controller.delete()

        then:
        response.status == SC_MOVED_TEMPORARILY
    }

}
