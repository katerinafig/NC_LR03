package buildings.interfaces;

import java.util.Iterator;

public interface Building {
    //метод получения количества этажей в здании
    public int getSize();
    //метод получения количества помещений в здании
    public int getCountSpace();
    //метод получения общей площади всех помещений здания
    public int getAreaSpace();
    //метод получения общего количества комнат в помещениях здания
    public int getCountRoomsOnBuilding();
    //метод получения массива этажей здания
    public Floor[] getArrayFloors();
    //метод получения этажа здания по его номеру
    public  Floor getFloor(int i);
    //метод изменения этажа по его номеру и ссылке на новый этаж
    public void setFloor (int number, Floor newFloor);
    //метод получения помещения по его номеру в здании
    public Space getSpace(int number);
    //метод изменения помещения в здании по номеру и ссылке на новое помещение
    public void setSpace (int number, Space newSpace);
    //метод вставке помещения в здании по будущему номеру и ссылке на новое помещение
    public void addNewSpace (int number, Space newSpace);
    //метод удаления помещения из здания
    public void removeSpace(int number);
    //метод получения лучшего помещения в здании
    public Space getBestSpace();
    //метод получения отсортированного массива всех помещений
    public Space [] sortByAreaSpace();
    public void checkBounds(int number);
    Object clone()throws CloneNotSupportedException;
    Iterator<Floor> iterator();
}
