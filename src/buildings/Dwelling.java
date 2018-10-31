package buildings;
import buildings.exception.FloorIndexOutOfBoundsException;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class Dwelling implements Building {
    private int size;
    private DwellingFloor [] arrayFloors ;
    //Конструктор может принимать количество этажей и массив количества квартир по этажам
    public Dwelling(int size,int [] countsFlat)
    {
        if(size==countsFlat.length) {
            arrayFloors = new DwellingFloor[size];
            this.size = size;
            for (int i = 0; i < size; i++) {
                arrayFloors[i] = new DwellingFloor(countsFlat[i]);
            }
        }
    }
    //Конструктор может принимать массив этажей дома.
    public Dwelling (DwellingFloor [] arrayFloors)
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
    //метод получения объкта этажа по его номеру в доме
    public  Floor getFloor(int number)
    {
        if ((number >= size)||(number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        return getArrayFloors()[number];
    }
    //метод изменения этажа по его номеру и ссылке на этаж
    public void setFloor (int number, Floor newFloor)
    {
        if ((number >= size)||(number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
            arrayFloors[number]=(DwellingFloor)newFloor; //todo зачем getArrayFloors() когда можешь напрямую к массиву обращаться?

    }
    //метод получения объекта расположения квартиры в доме по её номеру в доме
    private LocationFlatDTO getLocationFlat(int number) // todo такие вещи относятся к особенностям реализации и должны быть приватными
    {
        LocationFlatDTO location = null;
        int tempSize=0;
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
                location=new LocationFlatDTO(i,number-count);
            }
        }

        return location;
    }
    //метод получения объекта квартиры по её номеру в доме
    public Flat getSpace(int number) {

        LocationFlatDTO temp = getLocationFlat(number);
        if(temp!=null) {
            return (Flat)getArrayFloors()[temp.floor].getSpace(temp.flat);
        }
        else  return null;
    }
    //метод изменения квартиры по её номеру в доме и ссылке на квартиру
    public void setSpace (int number, Space newFlat) {

        LocationFlatDTO temp = getLocationFlat(number);
        if(temp!=null) {
            getArrayFloors()[temp.floor].setSpace(temp.flat, newFlat);
        }
    }
    //метод добавления новой квартиры по её номеру в доме и ссылке на квартиру без увеличения числа этажей
    public void addNewSpace(int number, Space newFlat)
    {
        LocationFlatDTO temp = getLocationFlat(number);
        if(temp!=null) {
            getArrayFloors()[temp.floor].addNewSpace(temp.flat, newFlat);
        }
        else getArrayFloors()[size-1].addNewSpace(number- getCountSpace()+getArrayFloors()[size-1].getSize(), newFlat);
    }
    //метод удаления квартиры по её номеру в доме
    public void removeSpace(int number)
    {
        LocationFlatDTO temp = getLocationFlat(number);
        if(temp!=null) {
            getArrayFloors()[temp.floor].removeSpace(temp.flat);
        }
    }
    //метод получения самой большой по площади квартиры дома
    public Flat getBestSpace(){
        Flat flatMaxArea = arrayFloors[0].getBestSpace();
        for(int i=1;i<size;i++) {
            Flat temp = arrayFloors[i].getBestSpace();
            if(flatMaxArea.getArea() < temp.getArea())
            {
                flatMaxArea = temp;
            }
        }
        return flatMaxArea;
    }
    //Метод получения отсортированного по убыванию площадей массива квартир
    public Flat [] sortByAreaSpace()
    {
        int k=0;
        Flat buf = null;
        Flat [] arrayFlat = new Flat[getCountSpace()];
        for(int i=0;i<getArrayFloors().length;i++) {
            for(int j=0; j<getArrayFloors()[i].getSize();j++) { //todo если getArrayFloors() возвращает копию массива, то лучше по квартирам гулять методом getFlat класса DwellingFloor()
                if(getArrayFloors()[i].getSpace(j)!=null)
                arrayFlat[k] = (Flat)getArrayFloors()[i].getSpace(j);
                k++;
            }
        }
        for (int out = k - 1; out >= 1; out--){
            for (int in = 0; in < out; in++){
                if(arrayFlat[in].getArea() < arrayFlat[in + 1].getArea()) {
                    buf = arrayFlat[in];
                    arrayFlat[in] = arrayFlat[in + 1];
                    arrayFlat[in + 1] = buf;
                }
            }
        }
        return arrayFlat;
    }

}