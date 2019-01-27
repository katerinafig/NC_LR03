package buildings.interfaces;

import java.io.Serializable;

public interface Space extends Comparable<Space> {

    int getArea();
    void setArea(int area);
    int getNumberOfRooms();
    void setNumberOfRooms(int numberOfRooms);
    Object clone() throws CloneNotSupportedException;
}
