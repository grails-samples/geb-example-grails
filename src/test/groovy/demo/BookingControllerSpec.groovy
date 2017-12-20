package demo

import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import groovy.time.TimeCategory
import spock.lang.*

class BookingControllerSpec extends Specification implements ControllerUnitTest<BookingController>, DomainUnitTest<Booking> {

    @Shared
    Date arrival

    @Shared
    Date departure

    def setupSpec() {
        Date now = new Date()
        use(TimeCategory) {
            arrival = now + 1.day
            departure = now + 3.day
        }
    }

    void "Test the index action returns the correct model"() {
        given:
        controller.bookingDataService = Mock(BookingDataService) {
            1 * list(_) >> []
            1 * count() >> 0
        }

        when:"The index action is executed"
        controller.index()

        then:"The model is correct"
        !model.bookingList
        model.bookingCount == 0
    }

    void "Test the create action returns the correct model"() {
        given:
        controller.roomService = Mock(RoomService)
        controller.extraService = Mock(ExtraService)

        when:"The create action is executed"
        Map m = controller.create()

        then:"The model is correctly created"
        m.booking != null
        m.keySet().contains('roomList')
        m.keySet().contains('extraList')
    }

    void "Test the save action with a null instance"() {
        when:
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        controller.save()

        then:
        view == 'edit'
    }

    void "Test the save action correctly persists"() {
        given:
        controller.bookingService = Stub(BookingService) {
            save(_, _, _) >> {
                new Booking(name: 'Tim',
                        email: 'sergio.delamo@softamo.com',
                        arrival: arrival,
                        departure: departure,
                        adults: 2)
            }
        }

        when:"The save action is executed with a valid instance"
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        params.name = 'Tim'
        params.email = 'sergio.delamo@softamo.com'
        params.arrival = arrival
        params.departure = departure
        params.adults = 2
        params.rooms = [2L]
        controller.save()

        then:"A redirect is issued to the show action"
        response.redirectedUrl.startsWith('/booking/show')
        controller.flash.message != null
    }

    void "Test the save action with an invalid instance"() {
        given:
        controller.bookingService = Stub(BookingService) {
            save(_, _, _) >> {
                Booking booking = new Booking()
                throw new ValidationException("Invalid instance", booking.errors)
            }
        }

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        params.name = 'Tim'
        params.email = 'sergio.delamo@softamo.com'
        params.arrival = arrival
        params.departure = departure
        params.adults = 2
        params.rooms = [2L]
        controller.save()

        then:"The create view is rendered again with the correct model"
        model.booking != null
        view == 'create'
    }

    void "Test the show action with a null id"() {
        given:
        controller.bookingExtraService = Mock(BookingExtraService)
        controller.bookingRoomService = Mock(BookingRoomService)

        controller.bookingDataService = Mock(BookingDataService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.show(null)

        then:"The user is redirected to index"
        response.redirectedUrl == '/'
        flash.message != null
    }

    void "Test the show action with a valid id"() {
        given:
        controller.bookingExtraService = Mock(BookingExtraService)
        controller.bookingRoomService = Mock(BookingRoomService)
        controller.bookingDataService = Mock(BookingDataService) {
            1 * get(2) >> new Booking()
        }

        when:"A domain instance is passed to the show action"
        Map m = controller.show(2)

        then:"A model is populated containing the domain instance"
        m.booking instanceof Booking
        m.keySet().contains('extraList')
        m.keySet().contains('roomList')
    }

    void "Test the edit action with a null id"() {
        given:
        controller.bookingDataService = Mock(BookingDataService) {
            1 * get(null) >> null
        }

        when:"The show action is executed with a null domain"
        controller.edit(null)

        then:"The user is redirected to index"
        response.redirectedUrl == '/'
        flash.message != null
    }

    void "Test the edit action with a valid id"() {
        given:
        controller.roomService = Mock(RoomService)
        controller.extraService = Mock(ExtraService)
        controller.bookingExtraService = Mock(BookingExtraService)
        controller.bookingRoomService = Mock(BookingRoomService)

        controller.bookingDataService = Mock(BookingDataService) {
            1 * get(2) >> new Booking()
        }

        when:"A domain instance is passed to the show action"
        Map m = controller.edit(2)

        then:"A model is populated containing the domain instance"
        m.booking instanceof Booking
        m.keySet().contains('roomList')
        m.keySet().contains('extraList')
        m.keySet().contains('bookingExtraList')
        m.keySet().contains('bookingRoomList')
    }

    void "Test the update action with a null instance"() {
        when:"Save is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update()

        then: "returned to edit form, since form payload is invalid"
        view == 'edit'
    }

    void "Test the update action correctly persists"() {
        given:
        controller.bookingService = Stub(BookingService) {
            update(_, _, _) >> {
                new Booking(name: 'Tim',
                        email: 'sergio.delamo@softamo.com',
                        arrival: arrival,
                        departure: departure,
                        adults: 2)
            }        }

        when:"The save action is executed with a valid instance"
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        params.id = 1L
        params.version = 1L
        params.name = 'Tim'
        params.email = 'sergio.delamo@softamo.com'
        params.arrival = arrival
        params.departure = departure
        params.adults = 2
        params.rooms = [2L]

        controller.update()

        then:"A redirect is issued to the show action"
        response.redirectedUrl.startsWith('/booking/show')
        controller.flash.message != null
    }

    @Ignore
    void "Test the update action with an invalid instance"() {
        given:
        controller.bookingService = Stub(BookingService) {
            update(_,_,_) >> { ->
                Booking booking = new Booking()
                throw new ValidationException("Invalid instance", booking.errors)
            }
        }

        when:"The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        params.id = 1L
        params.version = 1L
        params.name = 'Tim'
        params.email = 'sergio.delamo@softamo.com'
        params.arrival = arrival
        params.departure = departure
        params.adults = 2
        params.rooms = [2L]
        controller.update()

        then:"The edit view is rendered again with the correct model"
        model.booking != null
        view == 'edit'
    }

    void "Test the delete action with a null instance"() {
        when:"The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then:"A 404 is returned"
        response.redirectedUrl == '/'
        flash.message != null
    }

    void "Test the delete action with an instance"() {
        given:
        controller.bookingService = Mock(BookingService) {
            1 * delete(2)
        }

        when:"The domain instance is passed to the delete action"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(2)

        then:"The user is redirected to index"
        response.redirectedUrl == '/'
        flash.message != null
    }
}






