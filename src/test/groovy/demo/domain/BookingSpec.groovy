package demo.domain

import demo.Booking
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class BookingSpec extends Specification implements DomainUnitTest<Booking> {

    @Unroll
    void 'test #propertyName cannot be null'() {
        given:
        domain[propertyName] = null

        expect:
        !domain.validate([propertyName])
        domain.errors[propertyName].code == 'nullable'

        where:
        propertyName << ['name', 'email', 'arrival', 'departure']
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

    void '# of adults should be at least 1'() {
        given:
        domain.adults = 0

        expect:
        !domain.validate(['adults'])
    }

    void '# of children should be >= 0'() {
        given:
        domain.children = -1

        expect:
        !domain.validate(['children'])
    }

    void '# of babys should be >= 0'() {
        given:
        domain.babys = -1

        expect:
        !domain.validate(['babys'])
    }

    @Unroll
    void 'test #propertyName can have a maximum of 255 characters'() {
        given:
        domain[propertyName] = name

        expect:
        isValid == domain.validate([propertyName])

        if (!isValid) {
            assert domain.errors[propertyName].code == 'maxSize.exceeded'
        }

        where:
        propertyName | name       | isValid
        'name'       | 'a' * 256  | false
        'name'       | 'a' * 255  | true
        'telephone'  | 'a' * 256  | false
        'telephone'  | 'a' * 255  | true
    }
}
