package Model;

public class SingleRoom extends Room {
    // Spezifisches Attribut für das Einzelzimmer
    private boolean hasMinibar;

    // Konstruktor zur Initialisierung des Einzelzimmers, reicht allgemeine Daten an die Basisklasse weiter
    public SingleRoom(int roomNumber, double pricePerNight, boolean hasMinibar) {
        super(roomNumber, "Einzelzimmer", "Einzelbett", pricePerNight);
        this.hasMinibar = hasMinibar;
    }

    // Überschreibt die Interface-Methode: Einzelzimmer erlauben grundsätzlich keine Haustiere
    @Override
    public boolean allowsPet() {
        return false;
    }

    // Überschreibt die Interface-Methode zur Preisberechnung ohne Haustierzuschlag
    @Override
    public double calculatePrice(int nights, boolean pet) {
        return nights * getPricePerNight();
    }

    public boolean getHasMinibar() {
        return hasMinibar;
    }
}