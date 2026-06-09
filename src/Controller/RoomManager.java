package Controller;

import Model.DoubleRoom;
import Model.Room;
import Model.SingleRoom;
import Model.SuiteRoom;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class RoomManager {
    private final Scanner input = new Scanner(System.in);
    private final List<Room> rooms;

    public RoomManager() {
        this.rooms = new LinkedList<>();
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    // Gibt alle Zimmer mit Details und Belegungsstatus auf der Konsole aus
    // Gibt alle Zimmer mit Details und Belegungsstatus auf der Konsole aus
    public void printAllRooms() {
        System.out.println("#  Zimmerliste  #");
        for (Room room : rooms) {
            String status = room.isBooked() ? "belegt" : "frei";

            // Standard-Text für jedes Zimmer
            String roomInfo = "Zimmer-Nr: " + room.getRoomNumber() +
                    " | Typ: " + room.getRoomType() +
                    " | Preis/Nacht: " + room.getPricePerNight() + "€" +
                    " | Status: " + status +
                    " | Bett: " + room.getBedType();

            // Falls es sich um eine Suite handelt, fügen wir die Balkon-Info hinzu
            if (room instanceof SuiteRoom suite) {
                roomInfo += " | Balkon: " + (suite.getBalcony() ? "Ja" : "Nein");
            }

            // Einbindung der Minibar-Info für Einzelzimmer
            if (room instanceof SingleRoom single) {
                roomInfo += " | Minibar: " + (single.getHasMinibar() ? "Ja" : "Nein");
            }

            // Haustier-Info am Ende anhängen und ausgeben
            roomInfo += " | Haustiere erlaubt: " + (room.allowsPet() ? "Ja" : "Nein");
            System.out.println(roomInfo);
        }
    }

    public void printStatistics() {
        int totalRooms = rooms.size();
        int bookedRooms = 0;
        int singleRooms = 0;
        int doubleRooms = 0;
        int suites = 0;

        for (Room room : rooms) {
            if (room.isBooked()) {
                bookedRooms++;
            }
            if (room instanceof SingleRoom) {
                singleRooms++;
            } else if (room instanceof DoubleRoom) {
                doubleRooms++;
            } else if (room instanceof SuiteRoom) {
                suites++;
            }
        }
        System.out.println("\n--- Statistik ---");
        System.out.println("Anzahl aller Zimmer: " + totalRooms);
        System.out.println("Anzahl belegter Zimmer: " + bookedRooms);
        System.out.println("Anzahl freier Zimmer: " + (totalRooms - bookedRooms));
        System.out.println("Zimmertypen:");
        System.out.println("  - Einzelzimmer: " + singleRooms);
        System.out.println("  - Doppelzimmer: " + doubleRooms);
        System.out.println("  - Suiten: " + suites);
    }

    public void handleBookRoom() {
        System.out.print("Geben Sie die Zimmernummer ein, die Sie buchen möchten: ");
        while (!input.hasNextInt()) {
            System.out.print("Bitte gib eine gültige Zahl ein: ");
            input.next();
        }
        int bookNumber = input.nextInt();
        input.nextLine();

        Room roomToBook = findRoom(bookNumber);

        if (roomToBook == null) {
            System.out.println("Zimmer existiert nicht.");
        } else if (roomToBook.isBooked()) {
            System.out.println("Zimmer ist bereits belegt.");
        } else {
            roomToBook.setBooked(true);
            System.out.println("Zimmer " + bookNumber + " wurde erfolgreich gebucht.");
        }
    }

    public void handleCheckoutRoom() {
        System.out.print("Geben Sie die Zimmernummer für den Checkout ein: ");
        while (!input.hasNextInt()) {
            System.out.print("Bitte gib eine gültige Zahl ein: ");
            input.next();
        }
        int checkoutNumber = input.nextInt();
        input.nextLine();

        Room roomToCheckout = findRoom(checkoutNumber);

        if (roomToCheckout == null) {
            System.out.println("Zimmer existiert nicht.");
        } else if (!roomToCheckout.isBooked()) {
            System.out.println("Zimmer ist nicht belegt.");
        } else {
            System.out.print("Anzahl der Nächte: ");
            while (!input.hasNextInt()) {
                System.out.print("Bitte gib eine gültige Zahl ein: ");
                input.next();
            }
            int nights = input.nextInt();
            input.nextLine();

            boolean pet = false;
            if (roomToCheckout.allowsPet()) {
                System.out.print("Wurde ein Haustier mitgebracht? (ja/nein): ");
                String petInput = input.nextLine().trim().toLowerCase();
                pet = petInput.equals("ja");
            }

            double totalPrice = roomToCheckout.calculatePrice(nights, pet);
            roomToCheckout.setBooked(false);

            System.out.println("Erfolgreich ausgecheckt!");
            System.out.println("Gesamtpreis für " + nights + " Nächte: " + totalPrice + "€");
        }
    }
}