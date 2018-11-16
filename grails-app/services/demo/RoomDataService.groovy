package demo

import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.CompileStatic

@CompileStatic
@Service(Room)
interface RoomDataService {

    Room get(Serializable id)

    List<Room> list(Map args)

    Number count()

    void delete(Serializable id)

    Room save(String name)

    Room save(Room room)

    Room update(Serializable id, String name)

    @Where({ id in ids })
    List<Room> find(List<Long> ids)
}
