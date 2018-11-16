package demo.commands

import demo.NameIdCommand
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class NameIdCommandSpec extends Specification {

    @Subject
    @Shared
    NameIdCommand cmd = new NameIdCommand()

    @Unroll
    void 'test #propertyName cannot be null'() {
        given:
        cmd[propertyName] = null

        expect:
        !cmd.validate([propertyName])
        cmd.errors[propertyName].code == 'nullable'

        where:
        propertyName << ['id', 'name']
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
