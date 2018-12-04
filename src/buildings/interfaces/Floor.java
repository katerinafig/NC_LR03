package buildings.interfaces;

public interface Floor {
    //метод получения количества помещений на этаже
    public int getSize();
    //метод получения общей площади помещений на этаже
    public int getAreaSpace();
    //метод получения общего количества комнат в помещениях этажа
    public int getCountRoomsOnSpace();
    //метод получения массива всех помещений этажа
    public Space[] getArraySpace();
    //метод получения помещения по его номеру
    public  Space getSpace(int i);
    //метод изменения помещения по его номеру и ссылке на новое помещение,
    public void setSpace (int number, Space newSpace);
    //метод вставки помещения по его номеру и ссылке на новое помещение
    public void addNewSpace (int number, Space newFlat);
    //метод удаления помещения по его номеру
    public void removeSpace(int number);
    //метод получения лучшего помещения на этаже
    public Space getBestSpace();
    public void checkBounds(int number);
    Object clone()throws CloneNotSupportedException;
}
