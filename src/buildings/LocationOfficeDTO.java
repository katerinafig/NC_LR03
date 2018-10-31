package buildings;

public class LocationOfficeDTO {
    int floor;
    int office;

    public LocationOfficeDTO(int floor, int office) {
        this.floor = floor;
        this.office = office;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int flat) {
        this.office = flat;
    }
}
