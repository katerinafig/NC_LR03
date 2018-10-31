package buildings;
import buildings.exception.InvalidRoomsCountException;
import buildings.exception.InvalidSpaceAreaException;
import buildings.interfaces.Space;

public class Flat  implements Space {
    private static final int  DEF_ROOMS = 2;
    private static final int  DEF_AREA = 50;

    private int area;
    private int numberOfRooms;

    public Flat() {
        this(DEF_AREA,DEF_ROOMS);
    }
    public Flat(int area ) {
        this(area,DEF_ROOMS);
    }
    public Flat(int area, int numberOfRooms) {
        if(area<=0) throw new InvalidSpaceAreaException();
        if(numberOfRooms<=0) throw new InvalidRoomsCountException();
        this.area = area;
        this.numberOfRooms = numberOfRooms;
    }
    public int getArea() {
        return area;
    }
    public void setArea(int area) {
        if(area<=0) throw new InvalidSpaceAreaException();
        this.area = area;
    }
    public int getNumberOfRooms() {
        return numberOfRooms;
    }
    public void setNumberOfRooms(int numberOfRooms) {
        if(numberOfRooms<=0) throw new InvalidRoomsCountException();
        this.numberOfRooms = numberOfRooms;
    }

}