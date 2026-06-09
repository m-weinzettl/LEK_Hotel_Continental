package Model;

public abstract class Room implements Booking {
    private int roomNumber;
    private String roomType;
    private String bedType;
    private double pricePerNight;
    private boolean isBooked;

    public Room(int roomNumber, String roomType, String bedType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.bedType = bedType; // Nutzt jetzt den echten Übergabeparameter
        this.pricePerNight = pricePerNight;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    // Korrigierter Getter für den RoomManager
    public String getBedType() {
        return bedType;
    }

    // Echter Setter, falls der Betttyp mal geändert werden muss
    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}