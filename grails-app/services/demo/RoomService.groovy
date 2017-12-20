package demo

import grails.gorm.services.Service
import grails.gorm.services.Where

@Service(Room)
interface RoomService {

    Room get(Serializable id)

    List<Room> list(Map args)

    Long count()

    void delete(Serializable id)

    Room save(Room room)

    @Where({ id in ids })
    List<Room> find(List<Long> ids)
}