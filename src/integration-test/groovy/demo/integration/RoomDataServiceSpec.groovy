package demo.integration

import demo.LeakageDetector
import demo.Room
import demo.RoomDataService
import demo.RoomFixture
import grails.core.GrailsApplication
import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class RoomDataServiceSpec extends Specification implements LeakageDetector, RoomFixture {

    RoomDataService roomDataService
    SessionFactory sessionFactory
    GrailsApplication grailsApplication

    def setup() {
        setupLeakage()
    }

    def cleanup() {
        verifyLeakage()
    }

    void "test get"() {
        when:
        Room room = saveRoom('Room 101')

        then:
        roomDataService.get(room.id) != null
    }

    void "test list"() {
        given:
        saveRooms(['Room 101', 'Room 102', 'Room 103', 'Room 104'])

        when:
        List<Room> roomList = roomDataService.list(max: 2, offset: 2)

        then:
        roomList.size() == 2
        roomList.get(0).name == 'Room 103'
        roomList.get(1).name == 'Room 104'
    }

    void "test count"() {
        when:
        saveRooms(['Room 101', 'Room 102', 'Room 103', 'Room 104'])

        then:
        roomDataService.count() == old(roomDataService.count()) + 4
    }

    void "test delete"() {
        when:
        saveRooms(['Room 101', 'Room 103', 'Room 104', 'Room 105'])
        Room room = saveRoom('Room 102')

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
        Room room = saveRoom('Room 106')

        then:
        room
        room.id != null
        room.name == 'Room 106'
    }
}
