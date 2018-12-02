package buildings.interfaces;

public interface Space {

    int getArea();
    void setArea(int area);
    int getNumberOfRooms();
    void setNumberOfRooms(int numberOfRooms);
    Object clone() throws CloneNotSupportedException;
}
