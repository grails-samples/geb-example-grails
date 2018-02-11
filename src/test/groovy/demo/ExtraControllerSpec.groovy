package demo

import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import grails.validation.ValidationException
import spock.lang.Specification

class ExtraControllerSpec extends Specification implements ControllerUnitTest<ExtraController>, DomainUnitTest<Extra> {

    void 'Test the index action returns the correct model'() {
        given:
        controller.extraDataService = Mock(ExtraDataService) {
            1 * list(_) >> []
            1 * count() >> 0
        }

        when: 'The index action is executed'
        controller.index()

        then: 'The model is correct'
        !model.extraList
        model.extraCount == 0
    }

    void 'Test the create action returns the correct model'() {
        when: 'The create action is executed'
        controller.create()

        then: 'The model is correctly created'
        model.extra != null
    }

    void 'Test the save action with a null instance'() {
        when: 'Save is called for a domain instance that doesn\'t exist'
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        controller.save()

        then: 'returned to edit form, since form paylod is invalid'
        view == 'edit'
    }

    void 'Test the save action correctly persists'() {
        given:
        controller.extraDataService = Stub(ExtraDataService) {
            save(_ as String) >> new Extra(name: 'Crib')
        }

        when: 'The save action is executed with a valid instance'
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        params.name = 'Crib'
        controller.save()

        then: 'A redirect is issued to the show action'
        response.redirectedUrl.startsWith('/extra/show')
        controller.flash.message != null
    }

    void 'Test the save action with an invalid instance'() {
        given:
        controller.extraDataService = Stub(ExtraDataService) {
            save(_ as String) >>  { String name ->
                Extra extra = new Extra()
                throw new ValidationException('Invalid instance', extra.errors)
            }
        }

        when: 'The save action is executed with an invalid instance'
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        params.name = 'Crib'
        controller.save()

        then: 'The create view is rendered again with the correct model'
        flash.error != null
        view == '/extra/create'
    }

    void 'Test the show action with a null id'() {
        given:
        controller.extraDataService = Mock(ExtraDataService) {
            1 * get(null) >> null
        }

        when: 'The show action is executed with a null domain'
        controller.show(null)

        then: 'A 404 error is returned'
        response.status == 404
    }

    void 'Test the show action with a valid id'() {
        given:
        controller.extraDataService = Mock(ExtraDataService) {
            1 * get(2) >> new Extra()
        }

        when: 'A domain instance is passed to the show action'
        controller.show(2)

        then: 'A model is populated containing the domain instance'
        model.extra instanceof Extra
    }

    void 'Test the edit action with a null id'() {
        given:
        controller.extraDataService = Mock(ExtraDataService) {
            1 * get(null) >> null
        }

        when: 'The show action is executed with a null domain'
        controller.edit(null)

        then: 'A 404 error is returned'
        response.status == 404
    }

    void 'Test the edit action with a valid id'() {
        given:
        controller.extraDataService = Mock(ExtraDataService) {
            1 * get(2) >> new Extra()
        }

        when: 'A domain instance is passed to the show action'
        controller.edit(2)

        then: 'A model is populated containing the domain instance'
        model.extra instanceof Extra
    }

    void 'Test the update action with a null instance'() {
        when: 'Save is called for a domain instance that doesn\'t exist'
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update()

        then: 'returned to edit form, since form payload is invalid'
        view == 'edit'
    }

    void 'Test the update action correctly persists'() {
        given:
        controller.extraDataService = Stub(ExtraDataService) {
            update(_ as Long, _ as String) >> { Long id, String name ->
                new Extra(id: id, name: name)
            }
        }

        when: 'The save action is executed with a valid instance'
        response.reset()
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        params.id = 1L
        params.name = 'Room 102'
        controller.update()

        then: 'A redirect is issued to the show action'
        response.redirectedUrl.startsWith('/extra/show')
        controller.flash.message != null
    }

    void 'Test the update action with an invalid instance'() {
        given:
        controller.extraDataService = Stub(ExtraDataService) {
            update(_ as Long, _ as String) >> { Long id, String name ->
                Extra extra = new Extra(id: id, name: name)
                throw new ValidationException('Invalid instance', extra.errors)
            }
        }

        when: 'The save action is executed with an invalid instance'
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        params.id = 1
        params.name = 'Room 102'
        controller.update()

        then: 'The edit view is rendered again with the correct model'
        flash.error != null
        view == '/extra/edit'
    }

    void 'Test the delete action with a null instance'() {
        when: 'The delete action is called for a null instance'
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then: 'A 404 is returned'
        response.redirectedUrl == '/extra/index'
        flash.message != null
    }

    void 'Test the delete action with an instance'() {
        given:
        controller.extraDataService = Mock(ExtraDataService) {
            1 * delete(2)
        }

        when: 'The domain instance is passed to the delete action'
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(2)

        then: 'The user is redirected to index'
        response.redirectedUrl == '/extra/index'
        flash.message != null
    }
}
