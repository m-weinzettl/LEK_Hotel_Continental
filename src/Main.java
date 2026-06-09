
import Controller.RoomManager;
import View.Menu;
import View.RoomPool;

class Main {
    public static void main(String[] args) {
        // Erzeugt die zentrale Instanz des RoomManagers für die Datenverwaltung
        RoomManager roomManager = new RoomManager();

        // Übergibt den Manager an den Pool, um ihn mit initialen Zimmerdaten zu befüllen
        RoomPool roomPool = new RoomPool(roomManager);
        roomPool.generateRooms();

        // Injiert denselben RoomManager in das Menü, um auf die echten Daten zuzugreifen
        Menu menu = new Menu(roomManager);

        // Startet die konsolenbasierte Benutzeroberfläche
        menu.showMenu();
    }
}