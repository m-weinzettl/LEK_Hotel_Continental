package View;

import Controller.RoomManager;
import Model.Room;
import java.util.Scanner;

public class Menu {
    // Scanner für die Eingaben im Hauptmenü
    private final Scanner input = new Scanner(System.in);
    // Referenz auf den injizierten Controller (Dependency Injection)
    private final RoomManager roomManager;

    // Konstruktor nimmt den RoomManager von außen entgegen
    public Menu(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    // Startet die textbasierte Benutzeroberfläche und hält das Menü in einer Schleife aktiv
    public void showMenu() {
        int option;

        do {
            System.out.println("\n=== Hotel Continental Management ===");
            System.out.println("1. Alle Zimmer anzeigen");
            System.out.println("2. Zimmer buchen");
            System.out.println("3. Zimmer auschecken");
            System.out.println("4. Statistik anzeigen");
            System.out.println("5. Programm beenden");
            System.out.print("Deine Auswahl: ");

            // Eingabe-Validierung: Verhindert Abstürze bei Falscheingaben (z. B. Buchstaben)
            while (!input.hasNextInt()) {
                System.out.print("Bitte gib eine gültige Zahl ein: ");
                input.next();
            }

            option = input.nextInt();
            input.nextLine(); // Konsolen-Buffer leeren

            // Delegation der Menüauswahl an den zuständigen Controller
            switch (option) {
                case 1:
                    roomManager.printAllRooms();
                    break;
                case 2:
                    roomManager.handleBookRoom();
                    break;
                case 3:
                    roomManager.handleCheckoutRoom();
                    break;
                case 4:
                    roomManager.printStatistics();
                    break;
                case 5:
                    System.out.println("Anwendung wird heruntergefahren. Auf Wiedersehen!");
                    break;
                default:
                    System.out.println("Diese Option existiert nicht.");
            }
        } while (option != 5); // Wiederholt die Schleife, bis Option 5 gewählt wird
    }
}