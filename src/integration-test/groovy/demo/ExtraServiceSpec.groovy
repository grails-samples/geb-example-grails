package demo

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ExtraServiceSpec extends Specification {

    ExtraService extraService
    SessionFactory sessionFactory


    void "test get"() {
        when:
        Extra extra = new Extra(name: 'Breakfast').save()

        then:
        extraService.get(extra.id) != null
    }

    void "test list"() {
        given:
        new Extra(name: 'Breakfast').save()
        new Extra(name: 'Crib').save()
        new Extra(name: 'Champagne').save()
        new Extra(name: 'Late Departure').save()

        when:
        List<Extra> extraList = extraService.list(max: 2, offset: 2)

        then:
        extraList.size() == 2
        extraList[0].name == 'Champagne'
        extraList[1].name == 'Late Departure'
    }

    void "test count"() {
        when:
        new Extra(name: 'Breakfast').save()
        new Extra(name: 'Crib').save()
        new Extra(name: 'Champagne').save()
        new Extra(name: 'Late Departure').save()

        then:
        extraService.count() == old(extraService.count()) + 4
    }

    void "test delete"() {
        when:
        Extra extra = new Extra(name: 'Breakfast').save()
        new Extra(name: 'Crib').save()
        new Extra(name: 'Champagne').save()
        new Extra(name: 'Late Departure').save()

        then:
        extraService.count() == old(extraService.count()) + 4

        when:
        extraService.delete(extra.id)
        sessionFactory.currentSession.flush()

        then:
        extraService.count() == old(extraService.count()) - 1
    }

    void "test save"() {
        when:
        Extra extra = extraService.save(new Extra(name: 'Breakfast'))

        then:
        extra
        extra.id != null
        extra.name == 'Breakfast'
    }
}
