package demo.commands

import demo.EditBookingCommand
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class EditBookingCommandSpec extends Specification {

    @Subject
    @Shared
    EditBookingCommand cmd = new EditBookingCommand()

    @Unroll
    void 'test #propertyName cannot be null'() {
        given:
        cmd[propertyName] = null

        expect:
        !cmd.validate([propertyName])
        cmd.errors[propertyName].code == 'nullable'

        where:
        propertyName << ['name', 'email', 'arrival', 'departure']
    }

    void 'test name cannot be blank'() {
        given:
        cmd.name = ''

        expect:
        !cmd.validate(['name'])
    }

    void 'test name can have a maximum of 255 characters'() {
        given:
        cmd.name = name

        expect:
        isValid == cmd.validate(['name'])

        if (!isValid) {
            assert cmd.errors['name'].code == 'maxSize.exceeded'
        }

        where:
        name       | isValid
        'a' * 256  | false
        'a' * 255  | true
    }

    void '# of adults should be at least 1'() {
        given:
        cmd.adults = 0

        expect:
        !cmd.validate(['adults'])
    }

    void '# of children should be >= 0'() {
        given:
        cmd.children = -1

        expect:
        !cmd.validate(['children'])
    }

    void '# of babys should be >= 0'() {
        given:
        cmd.babys = -1

        expect:
        !cmd.validate(['babys'])
    }

    @Unroll
    void 'test #propertyName can have a maximum of 255 characters'() {
        given:
        cmd[propertyName] = name

        expect:
        isValid == cmd.validate([propertyName])

        if (!isValid) {
            assert cmd.errors[propertyName].code == 'maxSize.exceeded'
        }

        where:
        propertyName | name       | isValid
        'name'       | 'a' * 256  | false
        'name'       | 'a' * 255  | true
        'telephone'  | 'a' * 256  | false
        'telephone'  | 'a' * 255  | true
    }
}
