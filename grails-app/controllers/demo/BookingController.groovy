package demo

import grails.validation.ValidationException
import groovy.transform.CompileStatic
import org.springframework.context.MessageSource

@CompileStatic
class BookingController {

    BookingService bookingService
    MessageSource messageSource

    static allowedMethods = [
            index: 'GET',
            create: 'GET',
            save: "POST",
            edit: 'GET',
            update: "PUT",
            delete: "DELETE"
    ]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bookingService.list(params), model:[bookingCount: bookingService.count()]
    }

    def show(Long id) {
        respond bookingService.get(id)
    }

    def create() {
        respond new Booking(params)
    }

    def save(Booking booking) {
        if (booking == null) {
            notFound()
            return
        }

        try {
            bookingService.save(booking)
        } catch (ValidationException e) {
            respond booking.errors, view:'create'
            return
        }

        flash.message = messageSource.getMessage('default.created.message', [bookingMessage(), booking.id] as Object[], 'Booking created', request.locale)
        redirect booking
    }

    def edit(Long id) {
        respond bookingService.get(id)
    }

    def update(Booking booking) {
        if (booking == null) {
            notFound()
            return
        }

        try {
            bookingService.save(booking)
        } catch (ValidationException e) {
            respond booking.errors, view:'edit'
            return
        }

        flash.message = messageSource.getMessage('default.updated.message', [bookingMessage(), booking.id] as Object[], 'Booking update', request.locale)
        redirect booking
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        bookingService.delete(id)
        flash.message = messageSource.getMessage('default.deleted.message', [bookingMessage(), id] as Object[], 'Booking deleted', request.locale)
        redirect action:"index", method:"GET"
    }

    protected void notFound() {
        flash.message = messageSource.getMessage('default.not.found.message', [bookingMessage(), params.id] as Object[], 'Booking not found', request.locale)
        redirect action: "index", method: "GET"
    }

    protected String bookingMessage() {
        messageSource.getMessage('booking.label', [] as Object[], 'Booking', request.locale)
    }
}
