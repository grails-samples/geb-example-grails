package demo

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class RoomServiceSpec extends Specification {

    RoomService roomService
    SessionFactory sessionFactory

    void "test get"() {
        when:
        new Room(name: 'Room 101').save()

        then:
        roomService.get(1) != null
    }

    void "test list"() {
        given:
        new Room(name: 'Room 101').save()
        new Room(name: 'Room 102').save()
        new Room(name: 'Room 103').save()
        new Room(name: 'Room 104').save()

        when:
        List<Room> roomList = roomService.list(max: 2, offset: 2)

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
        roomService.count() == old(roomService.count()) + 4
    }

    void "test delete"() {
        when:
        new Room(name: 'Room 101').save()
        Room room = new Room(name: 'Room 102').save()
        new Room(name: 'Room 103').save()
        new Room(name: 'Room 104').save()
        new Room(name: 'Room 105').save()

        then:
        roomService.count() == old(roomService.count()) + 5

        when:
        roomService.delete(room.id)
        sessionFactory.currentSession.flush()

        then:
        roomService.count() == old(roomService.count()) + -1
    }

    void "test save"() {
        when:
        Room room = roomService.save(new Room(name: 'Room 106'))

        then:
        room
        room.id != null
        room.name == 'Room 106'
    }
}
