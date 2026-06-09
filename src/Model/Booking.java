package Model;

public interface Booking {
    // Definiert den Vertrag für alle Klassen, die das Verhalten bezüglich Haustieren festlegen müssen
    boolean allowsPet();

    // Definiert den Vertrag für die individuelle Preisberechnung basierend auf Nächten und Haustieren
    double calculatePrice(int nights, boolean pet);
}