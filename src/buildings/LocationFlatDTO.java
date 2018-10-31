package buildings;

public class LocationFlatDTO {
    int floor;
    int flat;

    public LocationFlatDTO(int floor, int flat) {
        this.floor = floor;
        this.flat = flat;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }
}
