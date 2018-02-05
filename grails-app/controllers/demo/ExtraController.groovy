package demo

import grails.validation.ValidationException
import groovy.transform.CompileStatic
import org.springframework.context.MessageSource

@SuppressWarnings(['FactoryMethodName', 'ReturnNullFromCatchBlock'])
@CompileStatic
class ExtraController implements BeanMessage {

    ExtraDataService extraDataService
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
        respond extraDataService.list(params), model: [extraCount: extraDataService.count()]
    }

    def show(Long id) {
        respond extraDataService.get(id)
    }

    def create() {
        respond new Extra(params)
    }

    def save(NameCommand cmd) {
        if ( cmd.hasErrors() ) {
            respond cmd.errors, view: 'edit'
            return
        }

        Extra extra
        try {
            extra = extraDataService.save(cmd.name)
        } catch (ValidationException e) {
            flash.error = beanMessage(e, messageSource).join(',')
            render view: 'create'
            return
        }

        flash.message = messageSource.getMessage('default.created.message', [extraMessage(), extra.id] as Object[], 'Extra created', request.locale)
        redirect action: 'show', id: extra.id
    }

    def edit(Long id) {
        respond extraDataService.get(id)
    }

    def update(NameIdCommand cmd) {
        if ( cmd.hasErrors() ) {
            respond cmd.errors, view: 'edit'
            return
        }

        Extra extra
        try {
            extra = extraDataService.update(cmd.id, cmd.name)
        } catch (ValidationException e) {
            flash.error = beanMessage(e, messageSource).join(',')
            render view: 'edit'
            return
        }
        flash.message = messageSource.getMessage('default.updated.message', [extraMessage(), extra.id] as Object[], 'Extra updated', request.locale)
        redirect action: 'show', id: extra.id
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        extraDataService.delete(id)
        flash.message = messageSource.getMessage('default.deleted.message', [extraMessage(), id] as Object[], 'Extra deleted', request.locale)
        redirect action: 'index', method: 'GET'
    }

    protected void notFound() {
        flash.message = messageSource.getMessage('default.not.found.message', [extraMessage(), params.id] as Object[], 'Extra not found', request.locale)
        redirect action: 'index', method: 'GET'
    }

    protected String extraMessage() {
        messageSource.getMessage('extra.label', [] as Object[], 'Extra', request.locale)
    }
}
