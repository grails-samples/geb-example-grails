package demo

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class RoomDataServiceSpec extends Specification {

    RoomDataService roomDataService
    SessionFactory sessionFactory

    void "test get"() {
        when:
        Room room = new Room(name: 'Room 101').save()

        then:
        roomDataService.get(room.id) != null
    }

    void "test list"() {
        given:
        new Room(name: 'Room 101').save()
        new Room(name: 'Room 102').save()
        new Room(name: 'Room 103').save()
        new Room(name: 'Room 104').save()

        when:
        List<Room> roomList = roomDataService.list(max: 2, offset: 2)

        then:
        roomList.size() == 2
        roomList.get(0).name == 'Room 103'
        roomList.get(1).name == 'Room 104'
    }

    void "test count"() {
        when:
        new Room(name: 'Room 101').save()
        new Room(name: 'Room 102').save()
        new Room(name: 'Room 103').save()
        new Room(name: 'Room 104').save()

        then:
        roomDataService.count() == old(roomDataService.count()) + 4
    }

    void "test delete"() {
        when:
        new Room(name: 'Room 101').save()
        Room room = new Room(name: 'Room 102').save()
        new Room(name: 'Room 103').save()
        new Room(name: 'Room 104').save()
        new Room(name: 'Room 105').save()

        then:
        roomDataService.count() == old(roomDataService.count()) + 5

        when:
        roomDataService.delete(room.id)
        sessionFactory.currentSession.flush()

        then:
        roomDataService.count() == old(roomDataService.count()) + -1
    }

    void "test save"() {
        when:
        Room room = roomDataService.save(new Room(name: 'Room 106'))

        then:
        room
        room.id != null
        room.name == 'Room 106'
    }
}
