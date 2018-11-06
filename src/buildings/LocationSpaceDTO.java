package buildings;

public class LocationSpaceDTO {
    int floorNumber; //todo floorNumber
    int spaceNumber;  //todo spaceNumber
    public LocationSpaceDTO(int floorNumber, int spaceNumber) {
        this.floorNumber = floorNumber;
        this.spaceNumber = spaceNumber;
    }

    public int getFloor() {
        return floorNumber;
    }

    public void setFloor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getOffice() {
        return spaceNumber;
    }

    public void setOffice(int spaceNumber) {
        this.spaceNumber = spaceNumber;
    }
}
