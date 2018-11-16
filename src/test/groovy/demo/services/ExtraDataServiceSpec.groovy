package demo.services

import demo.Extra
import demo.ExtraDataService
import grails.gorm.transactions.Rollback
import grails.test.hibernate.HibernateSpec
import spock.lang.Shared
import spock.lang.Subject

class ExtraDataServiceSpec extends HibernateSpec {

    @Subject
    @Shared
    ExtraDataService service

    void setupSpec() {
        service = hibernateDatastore.getService(ExtraDataService)
    }

    @Rollback
    void "verify ExtraDataService::count() == Extra.count()"() {
        expect:
        Extra.count() == service.count()
    }

    void "CRUD operations with ExtraDataService"() {

        when:
        Extra extra = service.save('102')

        then:
        extra
        extra.name == '102'
        service.count() == old(service.count()) + 1

        when:
        Extra retrieveExtra = service.get(extra.id)

        then:
        retrieveExtra
        retrieveExtra.name == '102'

        when:
        List<Extra> extraList = service.list([:])

        then:
        extraList
        extraList.size() == 1

        when:
        extraList = service.list([offset: 0, max: 1])

        then:
        extraList
        extraList.size() == 1

        when:
        service.delete(extra.id)

        then:
        !service.get(extra.id)
    }

}
