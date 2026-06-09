package Model;

public class SuiteRoom extends Room {
    // Spezifisches Attribut für die Suite
    private boolean hasBalcony;

    // Konstruktor initialisiert die Suite und reicht Basisdaten an die Oberklasse weiter
    public SuiteRoom(int roomNumber, double pricePerNight, boolean hasBalcony) {
        super(roomNumber, "Suite", "Kingsize Bett", pricePerNight);
        this.hasBalcony = hasBalcony;
    }

    // Überschreibt die Interface-Methode: Suiten erlauben standardmäßig Haustiere
    @Override
    public boolean allowsPet() {
        return true;
    }

    // Überschreibt die Interface-Methode zur Preisberechnung inklusive Haustierzuschlag (25€ pro Nacht)
    @Override
    public double calculatePrice(int nights, boolean pet) {
        double total = nights * getPricePerNight();
        if (pet) {
            total += (nights * 25.0); // Exklusiver Aufschlag für Haustiere pro Nacht in einer Suite
        }
        return total;
    }

    // Korrigierte Getter-Methode für den Balkon-Status
    public boolean getBalcony() {
        return this.hasBalcony;
    }
}