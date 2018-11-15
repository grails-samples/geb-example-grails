package demo

import grails.validation.ValidationException
import groovy.transform.CompileStatic
import org.springframework.context.MessageSource

@SuppressWarnings(['FactoryMethodName', 'ReturnNullFromCatchBlock'])
@CompileStatic
class RoomController implements BeanMessage {

    RoomDataService roomDataService

    MessageSource messageSource

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
        respond roomDataService.list(params), model: [roomCount: roomDataService.count()]
    }

    def show(Long id) {
        respond roomDataService.get(id)
    }

    def create() {
        respond new Room(params)
    }

    def save(Room room) {
        if (room == null) {
            notFound()
            return
        }

        try {
            roomDataService.save(room)
        } catch (ValidationException e) {
            flash.error = beanMessage(e, messageSource).join(',')
            render view: 'create'
            return
        }

        flash.message = messageSource.getMessage('default.created.message', [roomMessage(), room.id] as Object[], 'Room created', request.locale)
        redirect room
    }

    def edit(Long id) {
        respond roomDataService.get(id)
    }

    def update(Room room) {
        if (room == null) {
            notFound()
            return
        }

        try {
            roomDataService.save(room)
        } catch (ValidationException e) {
            flash.error = beanMessage(e, messageSource).join(',')
            render view: 'edit'
            return
        }

        flash.message = messageSource.getMessage('default.updated.message', [roomMessage(), room.id] as Object[], 'Room updated', request.locale)
        redirect room
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        roomDataService.delete(id)

        flash.message = messageSource.getMessage('default.deleted.message', [roomMessage(), id] as Object[], 'Room deleted', request.locale)
        redirect action: 'index', method: 'GET'
    }

    protected void notFound() {
        flash.message = messageSource.getMessage('default.not.found.message', [roomMessage(), params.id] as Object[], 'Room not found', request.locale)
        redirect action: 'index', method: 'GET'
    }

    protected String roomMessage() {
        messageSource.getMessage('room.label', [] as Object[], 'Room', request.locale)
    }
}
