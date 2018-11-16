package demo

trait RoomFixture {

    abstract RoomDataService getRoomDataService()

    Room saveRoom(String room) {
        roomDataService.save(room)
    }

    void deleteRooms(List<Room> rooms) {
        rooms.each { roomDataService.delete(it.id) }
    }

    List<Room> saveRooms(List<String> roomNames) {
        roomNames.collect { saveRoom(it) }
    }
}
