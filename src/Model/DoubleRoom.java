package Model;

public class DoubleRoom extends Room {
    // Spezifisches Attribut für das Doppelzimmer
    private boolean hasDoubleBed;

    // Konstruktor initialisiert das Doppelzimmer und reicht Basisdaten an die Oberklasse weiter
    public DoubleRoom(int roomNumber, double pricePerNight, boolean hasDoubleBed) {
        super(roomNumber, "Doppelzimmer", "Doppelbett", pricePerNight);
        this.hasDoubleBed = hasDoubleBed;
    }

    // Überschreibt die Interface-Methode: Doppelzimmer erlauben standardmäßig Haustiere
    @Override
    public boolean allowsPet() {
        return true;
    }

    // Überschreibt die Interface-Methode zur Preisberechnung inklusive Haustierzuschlag (15€ pro Nacht)
    @Override
    public double calculatePrice(int nights, boolean pet) {
        double total = nights * getPricePerNight();
        if (pet) {
            total += (nights * 15.0); // Aufschlag für Haustiere pro Nacht
        }
        return total;
    }
}