package View;

import Controller.RoomManager;
import Model.Room;
import Model.DoubleRoom;
import Model.SingleRoom;
import Model.SuiteRoom;

public class RoomPool {
    private final RoomManager roomManager;

    public RoomPool(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public void generateRooms(){
        // Erstellung von Einzelzimmern (übergibt intern "Einzelbett")
        roomManager.addRoom(new SingleRoom(101, 60.0, true));
        roomManager.addRoom(new SingleRoom(102, 55.0, false));
        roomManager.addRoom(new SingleRoom(103, 55.0, false));
        roomManager.addRoom(new SingleRoom(104, 55.0, false));

        // Erstellung von Doppelzimmern (übergibt intern "Doppelbett")
        roomManager.addRoom(new DoubleRoom(201, 95.0, true));
        roomManager.addRoom(new DoubleRoom(202, 95.0, true));
        roomManager.addRoom(new DoubleRoom(203, 90.0, false));
        roomManager.addRoom(new DoubleRoom(204, 90.0, false));

        // Erstellung von Suiten (übergibt intern "Kingsize Bett")
        roomManager.addRoom(new SuiteRoom(301, 180.0, false));
        roomManager.addRoom(new SuiteRoom(302, 220.0, true));
    }
}