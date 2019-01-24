package buildings.dwelling;
import buildings.exception.InvalidRoomsCountException;
import buildings.exception.InvalidSpaceAreaException;
import buildings.interfaces.Space;

import java.io.Serializable;

public class Flat  implements Space, Cloneable, Serializable {
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
    public Object clone() throws CloneNotSupportedException{

        return super.clone();
    }
    @Override
    public String toString(){
        return String.format("Flat (%d, %d.0)",numberOfRooms,area);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
        if (!(obj instanceof Flat)) {
            return false;
        }
        final Flat guest = (Flat) obj;
        return (this.area==guest.area)&&(this.numberOfRooms==guest.numberOfRooms);
    }
    @Override
    public int hashCode() {
        return area^numberOfRooms;
    }
}

