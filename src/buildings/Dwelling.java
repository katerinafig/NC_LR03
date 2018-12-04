package buildings;
import buildings.exception.FloorIndexOutOfBoundsException;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class Dwelling implements Building, Cloneable {
    private int size;
    private Floor [] arrayFloors ;
    //Конструктор может принимать количество этажей и массив количества квартир по этажам
    public Dwelling(int size,int [] countsFlat)
    {
        if(size==countsFlat.length) {
            arrayFloors = new Floor[size];
            this.size = size;
            for (int i = 0; i < size; i++) {
                arrayFloors[i] = new DwellingFloor(countsFlat[i]);
            }
        }
    }
    //Конструктор может принимать массив этажей дома.
    public Dwelling (Floor [] arrayFloors)
    {
        this.arrayFloors = arrayFloors;
    }
    //метод получения общего количества этажей дома
    public int getSize() {
        return size;
    }
    //метод получения общего количества квартир дома
    public int getCountSpace() {
        int countFlats = 0;
        for(int i=0;i<size;i++) {
            countFlats += arrayFloors[i].getSize();
        }
        return countFlats;
    }
    //метод получения общей площади квартир дома
    public int getAreaSpace() {
        int areaFlats = 0;
        for(int i=0;i<size;i++) {
            areaFlats += arrayFloors[i].getAreaSpace();
        }
        return areaFlats;
    }
    //метод получения общего количества комнат дома
    public int getCountRoomsOnBuilding() {
        int countRooms = 0;
        for(int i=0;i<getSize();i++) {
            countRooms += arrayFloors[i].getCountRoomsOnSpace();
        }
        return countRooms;
    }
    //метод получения массива этажей жилого дома
    public Floor[] getArrayFloors() {
        return arrayFloors;
    }
    //метод проверки находения номера в границах массива этажа
    public void checkBounds(int number)
    {
        if ((number >= size)||(number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
    }
    //метод получения объкта этажа по его номеру в доме
    public  Floor getFloor(int number)
    {
        //todo почему не вынесла в отдельный метод checkBounds?
        checkBounds(number);
        return arrayFloors[number];
    }
    //метод изменения этажа по его номеру и ссылке на этаж
    public void setFloor (int number, Floor newFloor)
    {
        checkBounds(number);//todo почему не вынесла в отдельный метод checkBounds?
        arrayFloors[number]=newFloor;

    }
    //метод получения объекта расположения квартиры в доме по её номеру в доме
    private LocationSpaceDTO getLocationFlat(int number)
    {
        LocationSpaceDTO location = null;
        int tempSize;
        int count = 0;
        if ((number >= getCountSpace())||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        for (int i = 0; i < size; i++) {
            tempSize = arrayFloors[i].getSize();
            if(number-count>=tempSize){
                count += tempSize;
            }
            else{
                location=new LocationSpaceDTO(i,number-count);
            }
        }

        return location;
    }
    //метод получения объекта квартиры по её номеру в доме
    public Space getSpace(int number) {

        LocationSpaceDTO searchLocationSpace = getLocationFlat(number);
        if(searchLocationSpace!=null) {
            return arrayFloors[searchLocationSpace.floorNumber].getSpace(searchLocationSpace.spaceNumber);
        }
        else  return null;
    }
    //метод изменения квартиры по её номеру в доме и ссылке на квартиру
    public void setSpace (int number, Space newFlat) {

        LocationSpaceDTO searchLocationSpace = getLocationFlat(number);
        if(searchLocationSpace!=null) {
            arrayFloors[searchLocationSpace.floorNumber].setSpace(searchLocationSpace.spaceNumber, newFlat);
        }
    }
    //метод добавления новой квартиры по её номеру в доме и ссылке на квартиру без увеличения числа этажей
    public void addNewSpace(int number, Space newFlat)
    {
        LocationSpaceDTO searchLocationSpace = getLocationFlat(number);
        if(searchLocationSpace!=null) {
            arrayFloors[searchLocationSpace.floorNumber].addNewSpace(searchLocationSpace.spaceNumber, newFlat);
        }
        else arrayFloors[size-1].addNewSpace(number- getCountSpace()+arrayFloors[size-1].getSize(), newFlat);
    }
    //метод удаления квартиры по её номеру в доме
    public void removeSpace(int number)
    {
        LocationSpaceDTO searchLocationSpace = getLocationFlat(number);
        if(searchLocationSpace!=null) {
            arrayFloors[searchLocationSpace.floorNumber].removeSpace(searchLocationSpace.spaceNumber);
        }
    }
    //метод получения самой большой по площади квартиры дома
    public Space getBestSpace(){
        Space flatMaxArea = arrayFloors[0].getBestSpace();
        for(int i=1;i<size;i++) {
            Space currentSpace = arrayFloors[i].getBestSpace();
            if(flatMaxArea.getArea() < currentSpace.getArea())
            {
                flatMaxArea = currentSpace;
            }
        }
        return flatMaxArea;
    }
    //Метод получения отсортированного по убыванию площадей массива квартир
    public Space [] sortByAreaSpace() {
        int flatsCount =0;
        Space containerFlats;
        Space [] arrayFlat = new Space[getCountSpace()];
        for(int i=0;i<arrayFloors.length;i++) {
            for(int j=0; j<arrayFloors[i].getSize();j++) {
                if(arrayFloors[i].getSpace(j)!=null)
                arrayFlat[flatsCount] = arrayFloors[i].getSpace(j);
                flatsCount++;
            }
        }
        for (int out = flatsCount - 1; out >= 1; out--){
            for (int in = 0; in < out; in++){
                if(arrayFlat[in].getArea() < arrayFlat[in + 1].getArea()) {
                    containerFlats = arrayFlat[in];
                    arrayFlat[in] = arrayFlat[in + 1];
                    arrayFlat[in + 1] = containerFlats;
                }
            }
        }
        return arrayFlat;
    }
    public Object clone() throws CloneNotSupportedException{//todo аналогично dwellingfloor
        Dwelling result = (Dwelling) super.clone();
        result.arrayFloors=arrayFloors.clone();
        for(int i = 0; i < size; i++) {
            result.setFloor(i, (Floor) arrayFloors[i].clone());
        }
        return result;
    }
    @Override
    public String toString(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("Dwelling (").append(size).append(", ");
        for (int i=0;i<size;i++) {
            finalString.append(arrayFloors[i].toString()).append(", ");
        }
        return finalString.delete(finalString.length()-2,finalString.length()).append(")").toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
        if (!(obj instanceof Dwelling)) {
            return false;
        }
        Dwelling guest = (Dwelling) obj;
        if(guest.size!=size)return false;
        for (int i = 0; i < size; i++) {
            if (!guest.arrayFloors[i].equals(arrayFloors[i])) {
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
            hashcode^=arrayFloors[i].hashCode();
        }
        return hashcode;
    }
}