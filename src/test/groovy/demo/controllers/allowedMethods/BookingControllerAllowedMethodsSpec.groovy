package demo.controllers.allowedMethods

import demo.BookingController
import demo.BookingDataService
import demo.ExtraDataService
import demo.RoomDataService
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import spock.lang.Unroll

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_MOVED_TEMPORARILY
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND
import static javax.servlet.http.HttpServletResponse.SC_OK
import static javax.servlet.http.HttpServletResponse.SC_TEMPORARY_REDIRECT

class BookingControllerAllowedMethodsSpec extends Specification implements ControllerUnitTest<BookingController> {

    @Unroll
    def "test BookingController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test BookingController.index accepts GET requests"() {
        given:
        controller.bookingDataService = Mock(BookingDataService)

        when:
        request.method = 'GET'
        controller.index()

        then:
        response.status == SC_NOT_FOUND
    }

    @Unroll
    def "test BookingController.create does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.create()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test BookingController.create accepts GET requests"() {
        given:
        controller.roomDataService = Mock(RoomDataService)
        controller.extraDataService = Mock(ExtraDataService)

        when:
        request.method = 'GET'
        controller.create()

        then:
        response.status == SC_MOVED_TEMPORARILY
    }

    @Unroll
    def "test BookingController.show does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.show()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test BookingController.show accepts GET requests"() {
        given:
        controller.bookingDataService = Mock(BookingDataService)

        when:
        request.method = 'GET'
        controller.show()

        then:
        response.status == SC_MOVED_TEMPORARILY
    }

    @Unroll
    def "test BookingController.edit does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.edit()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test BookingController.edit accepts GET requests"() {
        given:
        controller.bookingDataService = Mock(BookingDataService)

        when:
        request.method = 'GET'
        controller.edit()

        then:
        response.status == SC_MOVED_TEMPORARILY
    }

    @Unroll
    def "test BookingController.save does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.save()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test BookingController.save accepts POST requests"() {
        given:
        controller.bookingDataService = Mock(BookingDataService)

        when:
        request.method = 'POST'
        controller.save()

        then:
        response.status == SC_OK
    }

    @Unroll
    def "test BookingController.update does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.update()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'POST']
    }

    def "test BookingController.update accepts PUT requests"() {
        given:
        controller.bookingDataService = Mock(BookingDataService)

        when:
        request.method = 'PUT'
        controller.update()

        then:
        response.status == SC_OK
    }


    @Unroll
    def "test BookingController.delete does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.delete()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'PUT', 'GET', 'POST']
    }

    def "test BookingController.delete accepts DELETE requests"() {
        given:
        controller.bookingDataService = Mock(BookingDataService)

        when:
        request.method = 'DELETE'
        controller.delete()

        then:
        response.status == SC_MOVED_TEMPORARILY
    }

}
