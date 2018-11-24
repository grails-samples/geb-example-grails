package demo.domain

import demo.Extra
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ExtraSpec extends Specification implements DomainUnitTest<Extra> {

    void 'test name cannot be null'() {
        given:
        domain.name = null

        expect:
        !domain.validate(['name'])
        domain.errors['name'].code == 'nullable'
    }

    void 'test name cannot be blank'() {
        given:
        domain.name = ''

        expect:
        !domain.validate(['name'])
    }

    void 'test name can have a maximum of 255 characters'() {
        given:
        domain.name = name

        expect:
        isValid == domain.validate(['name'])

        if (!isValid) {
            assert domain.errors['name'].code == 'maxSize.exceeded'
        }

        where:
        name       | isValid
        'a' * 256  | false
        'a' * 255  | true
    }
}
