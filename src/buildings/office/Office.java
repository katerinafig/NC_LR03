package buildings.office;
import buildings.interfaces.Space;
import buildings.exception.*;

import java.io.Serializable;

public class Office implements Space,Cloneable, Serializable {
    private static final int  DEF_ROOMS = 1;
    private static final int  DEF_AREA = 250;

    private int area;
    private int numberOfRooms;

    public Office() {
        this(DEF_AREA,DEF_ROOMS);
    }
    public Office(int area ) {
        this(area,DEF_ROOMS);
    }
    public Office(int area, int numberOfRooms) {
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
    public int compareTo(Space o){
        return Integer.compare(getArea(), o.getArea());
    }
    @Override
    public String toString(){
        return String.format("Office (%d, %d.0)",numberOfRooms,area);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
        if (!(obj instanceof Office)) {
            return false;
        }
        final Office guest = (Office) obj;
        return (this.area==guest.area)&&(this.numberOfRooms==guest.numberOfRooms);
    }
    @Override
    public int hashCode() {
        return area^numberOfRooms;
    }
}
