package buildings.interfaces;

import java.util.Iterator;

public interface Floor extends Comparable<Floor>{
    //метод получения количества помещений на этаже
    int getSize();
    //метод получения общей площади помещений на этаже
    int getAreaSpace();
    //метод получения общего количества комнат в помещениях этажа
    int getCountRoomsOnSpace();
    //метод получения массива всех помещений этажа
    Space[] getArraySpace();
    //метод получения помещения по его номеру
    Space getSpace(int i);
    //метод изменения помещения по его номеру и ссылке на новое помещение,
    void setSpace (int number, Space newSpace);
    //метод вставки помещения по его номеру и ссылке на новое помещение
    void addNewSpace (int number, Space newFlat);
    //метод удаления помещения по его номеру
    void removeSpace(int number);
    //метод получения лучшего помещения на этаже
    Space getBestSpace();
    void checkBounds(int number);
    Object clone()throws CloneNotSupportedException;
    Iterator<Space> iterator();

}
