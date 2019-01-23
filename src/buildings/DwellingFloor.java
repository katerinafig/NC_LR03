package buildings;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;

public class DwellingFloor implements Floor,Cloneable, Serializable {
    private int size;
    private Space [] arrayFlat;
    //Конструктор может принимать количество квартир на этаже.
    public DwellingFloor(int countFlats) {
        arrayFlat = new Space[countFlats];
      this.size = 0;
    }
    //Конструктор может принимать массив квартир этажа.
    public DwellingFloor(Space[] arrayFlat) {
        this.arrayFlat = arrayFlat;
        this.size = arrayFlat.length;
    }
    //метод получения количества квартир на этаже
    public int getSize() {
        return size;
    }
    //метод получения общей площади квартир этажа
    public int getAreaSpace() {
        int areaFlats = 0;
        for(int i=0; i<size;i++)
        {
            areaFlats += arrayFlat[i].getArea();
        }
        return areaFlats;
    }
    //метод получения общего количества комнат этажа
    public int getCountRoomsOnSpace() {
        int allNomberFlats = 0;
        for(int i=0; i<size;i++)
        {
           allNomberFlats += arrayFlat[i].getNumberOfRooms();
        }
        return allNomberFlats;
    }
    //метод получения массива квартир этажа
    public Space[] getArraySpace() {
        Space [] newArrayFlat = new Space[arrayFlat.length];
        System.arraycopy(arrayFlat, 0, newArrayFlat, 0, arrayFlat.length);
        return  newArrayFlat;
    }
    //метод проверки находения номера в границах массива этажа
    public void checkBounds(int number)
    {
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }
    //метод получения объекта квартиры по её номеру на этаже
    public  Space getSpace(int number)
    {
        checkBounds(number);
        return arrayFlat[number];
    }
    //метод изменения квартиры
    public void setSpace (int number, Space newFlat)
    {
        checkBounds(number);
        arrayFlat[number]=newFlat;

    }
    //метод добавления новой квартиры на этаже по будущему номеру квартиры
    public void addNewSpace(int number, Space newFlat)
    {
        checkBounds(number);
        if(size==arrayFlat.length) {
            if (number >= size) {
                Flat[] newArrayFlat = new Flat[2 * arrayFlat.length];
                System.arraycopy(arrayFlat, 0, newArrayFlat, 0, arrayFlat.length);
                arrayFlat = newArrayFlat;
            }
        }
        else {
            System.arraycopy(arrayFlat, number, arrayFlat, number+1, arrayFlat.length-number-1);

        }
        arrayFlat[number]= newFlat;
        size++;

    }
    //метод удаления квартиры по её номеру на этаже
    public void removeSpace(int number)
    {
        checkBounds(number);
        System.arraycopy(arrayFlat, number+1, arrayFlat, number, size-number-1);
        size--;
    }
    //метод получения самой большой по площади квартиры этажа
    public Space getBestSpace(){
        Space flatMaxArea = arrayFlat[0];
        for(int i=1; i<size;i++)
        {
            if(arrayFlat[i].getArea()>flatMaxArea.getArea()){
                flatMaxArea = arrayFlat[i];
            }
        }
        return  flatMaxArea;
    }
    public Object clone() throws CloneNotSupportedException{
        DwellingFloor result = (DwellingFloor)super.clone();
        result.arrayFlat=arrayFlat.clone();
        for(int i = 0; i < size; i++) {
            result.arrayFlat[i]=(Space)arrayFlat[i].clone();
        }
        return result;
    }
    @Override
    public String toString(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("DwellingFloor (").append(size).append(", ");
        for (int i=0;i<size;i++) {
            finalString.append(arrayFlat[i].toString()).append(", ");
        }
        finalString.delete(finalString.length()-2,finalString.length()).append(")");
        return finalString.toString();
    }
    @Override
    public boolean equals(Object obj) {

        if (obj==this) {
            return true;
        }
        if (!(obj instanceof DwellingFloor)) {
            return  false;
        }
        DwellingFloor guest = (DwellingFloor) obj;
        if(guest.size!=size)return false;
        for (int i = 0; i < size; i++) {
            if (!guest.arrayFlat[i].equals(arrayFlat[i])) {
                return false;
            }
        }

        return true;
    }
    @Override
    public int hashCode() {
        int hashcode=size;
        for(int i=0;i<size;i++)
        {
            hashcode^=arrayFlat[i].hashCode();
        }
        return hashcode;
    }

}