package demo

import grails.validation.ValidationException
import groovy.transform.CompileStatic
import org.springframework.context.MessageSource

@SuppressWarnings(['FactoryMethodName', 'ReturnNullFromCatchBlock'])
@CompileStatic
class BookingController implements BeanMessage {

    BookingDataService bookingDataService
    BookingExtraDataService bookingExtraDataService
    BookingRoomDataService bookingRoomDataService
    RoomDataService roomDataService
    ExtraDataService extraDataService
    MessageSource messageSource
    BookingService bookingService

    static allowedMethods = [
            index: 'GET',
            show: 'GET',
            create: 'GET',
            save: 'POST',
            edit: 'GET',
            update: 'PUT',
            delete: 'DELETE',
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bookingDataService.list(params), model: [bookingCount: bookingDataService.count()]
    }

    def show(Long id) {
        Booking booking = bookingDataService.get(id)
        if ( !booking ) {
            notFound()
            return
        }
        List<Extra> extraList = bookingExtraDataService.findBookingExtraExtra(booking)
        List<Room> roomList = bookingRoomDataService.findBookingRoomRoom(booking)
        [booking: booking, roomList: roomList, extraList: extraList]
    }

    def create() {
        List<Room> roomList = roomDataService.list([:])
        List<Extra> extraList = extraDataService.list([:])
        if (!roomList) {
            flash.message = messageSource.getMessage('norooms', [] as Object[], 'No rooms', request.locale)
            redirect action: 'index', controller: 'booking'
            return
        }
        [
                booking: new Booking(params),
                roomList: roomList,
                extraList: extraList,
        ]
    }

    def save(SaveBookingCommand cmd) {
        if ( cmd.hasErrors() ) {
            respond cmd.errors, view: 'edit'
            return
        }

        Booking booking
        try {
            booking = bookingService.save(cmd as Booking, cmd.rooms, cmd.extras)
        } catch (ValidationException e) {
            flash.error = beanMessage(e, messageSource).join(',')
            render view: 'create'
            return
        }

        flash.message = messageSource.getMessage('default.created.message', [bookingMessage(), booking.id] as Object[], 'Booking created', request.locale)
        redirect action: 'show', id: booking.id
    }

    def edit(Long id) {
        Booking booking = bookingDataService.get(id)
        if ( !booking ) {
            notFound()
            return
        }
        List<Room> roomList = roomDataService.list([:])
        List<Extra> extraList = extraDataService.list([:])

        List<Extra> bookingExtraList = bookingExtraDataService.findBookingExtraExtra(booking)
        List<Room> bookingRoomList = bookingRoomDataService.findBookingRoomRoom(booking)
        [
                booking: booking,
                roomList: roomList,
                extraList: extraList,
                bookingExtraList: bookingExtraList,
                bookingRoomList: bookingRoomList,
        ]
    }

    def update(EditBookingCommand cmd) {
        if ( cmd.hasErrors() ) {
            respond cmd.errors, view: 'edit'
            return
        }

        Booking booking
        try {
            booking = bookingService.update(cmd as Booking, cmd.rooms, cmd.extras)
        } catch (ValidationException e) {
            flash.error = beanMessage(e, messageSource).join(',')
            render view: 'edit'
            return
        }

        flash.message = messageSource.getMessage('default.updated.message', [bookingMessage(), booking.id] as Object[], 'Booking update', request.locale)
        redirect action: 'show', id: booking.id
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        bookingService.delete(id)
        flash.message = messageSource.getMessage('default.deleted.message', [bookingMessage(), id] as Object[], 'Booking deleted', request.locale)
        redirect action: 'index', method: 'GET'
    }

    protected void notFound() {
        flash.message = messageSource.getMessage('default.not.found.message', [bookingMessage(), params.id] as Object[], 'Booking not found', request.locale)
        redirect action: 'index', method: 'GET'
    }

    protected String bookingMessage() {
        messageSource.getMessage('booking.label', [] as Object[], 'Booking', request.locale)
    }
}
