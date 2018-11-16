package demo.commands

import demo.NameCommand
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class NameCommandSpec extends Specification {

    @Subject
    @Shared
    NameCommand cmd = new NameCommand()

    void 'test name cannot be null'() {
        given:
        cmd.name = null

        expect:
        !cmd.validate(['name'])
        cmd.errors['name'].code == 'nullable'
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
}
