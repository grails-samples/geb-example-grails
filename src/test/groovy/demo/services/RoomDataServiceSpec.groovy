package demo.services

import demo.Room
import demo.RoomDataService
import grails.gorm.transactions.Rollback
import grails.test.hibernate.HibernateSpec
import spock.lang.Shared
import spock.lang.Subject

class RoomDataServiceSpec extends HibernateSpec {

    @Subject
    @Shared
    RoomDataService service

    void setupSpec() {
        service = hibernateDatastore.getService(RoomDataService)
    }

    @Rollback
    void "verify RoomDataService::count() == Room.count()"() {
        expect:
        Room.count() == service.count()
    }

    void "CRUD operations with RoomDataService"() {

        when:
        Room room = service.save(new Room(name: '102'))

        then:
        room
        room.name == '102'
        service.count() == old(service.count()) + 1

        when:
        Room retrieveRoom = service.get(room.id)

        then:
        retrieveRoom
        retrieveRoom.name == '102'

        when:
        List<Room> roomList = service.list([:])

        then:
        roomList
        roomList.size() == 1

        when:
        roomList = service.list([offset: 0, max: 1])

        then:
        roomList
        roomList.size() == 1

        when:
        service.delete(room.id)

        then:
        !service.get(room.id)
    }

}
