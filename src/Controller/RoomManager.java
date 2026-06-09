package Controller;

import Model.DoubleRoom;
import Model.Room;
import Model.SingleRoom;
import Model.SuiteRoom;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class RoomManager {
    // Scanner für die Erfassung von Benutzereingaben über die Konsole
    private final Scanner input = new Scanner(System.in);
    // Zentrale Liste zur Speicherung aller Hotelzimmer (Polymorphie: nutzt die abstrakte Basisklasse)
    private final List<Room> rooms;

    // Konstruktor initialisiert die Zimmerliste als LinkedList
    public RoomManager() {
        this.rooms = new LinkedList<>();
    }

    // Fügt der Hotelverwaltung ein neues Zimmer hinzu
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    // Gibt die vollständige Liste aller verwalteten Zimmer zurück
    public List<Room> getRooms() {
        return rooms;
    }

    // Sucht ein Zimmer anhand seiner eindeutigen Zimmernummer (liefert null, wenn nicht vorhanden)
    public Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    // Gibt die detaillierte Übersicht aller Zimmer inklusive spezifischer Eigenschaften auf der Konsole aus
    public void printAllRooms() {
        System.out.println("#  Zimmerliste  #");
        for (Room room : rooms) {
            // Ternärer Operator zur lesbaren Darstellung des Belegungsstatus
            String status = room.isBooked() ? "belegt" : "frei";

            // Erstellung des Basis-Informationsstrings, den jedes Zimmer besitzt
            String roomInfo = "Zimmer-Nr: " + room.getRoomNumber() +
                    " | Typ: " + room.getRoomType() +
                    " | Preis/Nacht: " + room.getPricePerNight() + "€" +
                    " | Status: " + status +
                    " | Bett: " + room.getBedType();

            // Pattern Matching (Java 16+): Prüft auf SuiteRoom und castet direkt zur Nutzung des Balkon-Getters
            if (room instanceof SuiteRoom suite) {
                roomInfo += " | Balkon: " + (suite.getBalcony() ? "Ja" : "Nein");
            }

            // Pattern Matching: Prüft auf SingleRoom und hakt den Minibar-Status ab
            if (room instanceof SingleRoom single) {
                roomInfo += " | Minibar: " + (single.getHasMinibar() ? "Ja" : "Nein");
            }

            // Interface-Methode aufrufen (Polymorphie) und Gesamtergebnis ausgeben
            roomInfo += " | Haustiere erlaubt: " + (room.allowsPet() ? "Ja" : "Nein");
            System.out.println(roomInfo);
        }
    }

    // Ermittelt die Belegungszahlen sowie die Verteilung der Zimmertypen für die Statistik
    public void printStatistics() {
        int totalRooms = rooms.size();
        int bookedRooms = 0;
        int singleRooms = 0;
        int doubleRooms = 0;
        int suites = 0;

        // Iteration über alle Zimmer zur Typbestimmung mittels instanceof
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

        // Ausgabe der geforderten Kennzahlen auf der Konsole
        System.out.println("\n--- Statistik ---");
        System.out.println("Anzahl aller Zimmer: " + totalRooms);
        System.out.println("Anzahl belegter Zimmer: " + bookedRooms);
        System.out.println("Anzahl freier Zimmer: " + (totalRooms - bookedRooms));
        System.out.println("Zimmertypen:");
        System.out.println("  - Einzelzimmer: " + singleRooms);
        System.out.println("  - Doppelzimmer: " + doubleRooms);
        System.out.println("  - Suiten: " + suites);
    }

    // Handhabt den Buchungsprozess (Check-In) mit integrierter Typ-Validierung
    public void handleBookRoom() {
        System.out.print("Geben Sie die Zimmernummer ein, die Sie buchen möchten: ");
        // Schleife läuft so lange, bis der Benutzer eine echte Ganzzahl eingibt (verhindert Abstürze)
        while (!input.hasNextInt()) {
            System.out.print("Bitte gib eine gültige Zahl ein: ");
            input.next();
        }
        int bookNumber = input.nextInt();
        input.nextLine(); // Konsolen-Buffer leeren

        Room roomToBook = findRoom(bookNumber);

        // Validierung der Buchungsvoraussetzungen gemäß LEK-Vorgabe
        if (roomToBook == null) {
            System.out.println("Zimmer existiert nicht.");
        } else if (roomToBook.isBooked()) {
            System.out.println("Zimmer ist bereits belegt.");
        } else {
            roomToBook.setBooked(true); // Statusänderung bei Erfolg
            System.out.println("Zimmer " + bookNumber + " wurde erfolgreich gebucht.");
        }
    }

    // Handhabt den Checkout-Prozess, berechnet den Endpreis via Interface und gibt das Zimmer frei
    public void handleCheckoutRoom() {
        System.out.print("Geben Sie die Zimmernummer für den Checkout ein: ");
        // Validierungsschleife für die Zimmernummer
        while (!input.hasNextInt()) {
            System.out.print("Bitte gib eine gültige Zahl ein: ");
            input.next();
        }
        int checkoutNumber = input.nextInt();
        input.nextLine();

        Room roomToCheckout = findRoom(checkoutNumber);

        // Validierung der Checkout-Voraussetzungen gemäß LEK-Vorgabe
        if (roomToCheckout == null) {
            System.out.println("Zimmer existiert nicht.");
        } else if (!roomToCheckout.isBooked()) {
            System.out.println("Zimmer ist nicht belegt.");
        } else {
            System.out.print("Anzahl der Nächte: ");
            // Validierungsschleife für die Anzahl der Übernachtungen
            while (!input.hasNextInt()) {
                System.out.print("Bitte gib eine gültige Zahl ein: ");
                input.next();
            }
            int nights = input.nextInt();
            input.nextLine();

            // Haustier-Abfrage wird nur gestartet, wenn das spezifische Zimmer Haustiere erlaubt
            boolean pet = false;
            if (roomToCheckout.allowsPet()) {
                System.out.print("Wurde ein Haustier mitgebracht? (ja/nein): ");
                String petInput = input.nextLine().trim().toLowerCase();
                pet = petInput.equals("ja");
            }

            // Polymorpher Aufruf der Preisberechnung (Logik liegt in den jeweiligen Subklassen)
            double totalPrice = roomToCheckout.calculatePrice(nights, pet);
            roomToCheckout.setBooked(false); // Zimmer wieder freigeben

            System.out.println("Erfolgreich ausgecheckt!");
            System.out.println("Gesamtpreis für " + nights + " Nächte: " + totalPrice + "€");
        }
    }
}