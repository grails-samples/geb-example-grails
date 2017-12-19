package demo

import grails.gorm.services.Service

@Service(Room)
interface RoomService {

    Room get(Serializable id)

    List<Room> list(Map args)

    Long count()

    void delete(Serializable id)

    Room save(Room room)

}