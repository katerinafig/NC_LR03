package buildings;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class DwellingFloor implements Floor {
    private int size;
    private Flat [] arrayFlat;
    //Конструктор может принимать количество квартир на этаже.
    public DwellingFloor(int countFlats) {
        arrayFlat = new Flat[countFlats];
      this.size = 0;
    }
    //Конструктор может принимать массив квартир этажа.
    public DwellingFloor(Flat[] arrayFlat) {
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
        for(int i=0; i<size;i++) //todo а size тебе зачем?
        {
            if(arrayFlat[i]!=null)
            areaFlats += arrayFlat[i].getArea();
        }
        return areaFlats;
    }
    //метод получения общего количества комнат этажа
    public int getCountRoomsOnSpace() {
        int allNomberFlats = 0;
        for(int i=0; i<size;i++)
        {
            if(arrayFlat[i]!=null)
           allNomberFlats += arrayFlat[i].getNumberOfRooms();
        }
        return allNomberFlats;
    }
    //метод получения массива квартир этажа
    public Space[] getArraySpace() { //todo лучше копию возвращать
        Flat [] newArrayFlat = new Flat[arrayFlat.length];
        System.arraycopy(arrayFlat, 0, newArrayFlat, 0, arrayFlat.length);
        return  newArrayFlat;
    }
    //метод получения объекта квартиры по её номеру на этаже
    public  Space getSpace(int number)
    {
        if ((number > size)||(number < 0)) {
        throw new SpaceIndexOutOfBoundsException();
    }
        return arrayFlat[number];
    }
    //метод изменения квартиры
    public void setSpace (int number, Space newFlat)
    {
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
            arrayFlat[number]=(Flat)newFlat;

    }
    //метод добавления новой квартиры на этаже по будущему номеру квартиры
    public void addNewSpace(int number, Space newFlat)
    {
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        if(size==arrayFlat.length) {
            if (number >= size) { //todo цикл то нафиг? просто проверка аналогичная методу setFlat()
                Flat[] newArrayFlat = new Flat[2 * arrayFlat.length];
                System.arraycopy(arrayFlat, 0, newArrayFlat, 0, arrayFlat.length);
                arrayFlat = newArrayFlat;
            }
        }
        else {
            System.arraycopy(arrayFlat, number, arrayFlat, number+1, arrayFlat.length-number-1);

        }
        arrayFlat[number]=(Flat) newFlat;
        size++; //todo почему length то, если size++?

    }
    //метод удаления квартиры по её номеру на этаже
    public void removeSpace(int number)
    {
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        if(arrayFlat[number]!=null) {
            System.arraycopy(arrayFlat, number+1, arrayFlat, number, size-number-1);
        }
        size--;//todo почему length то, если size--?
    }
    //метод получения самой большой по площади квартиры этажа
    public Flat getBestSpace(){
        Flat flatMaxArea = (Flat)getArraySpace()[0];
        for(int i=1; i<size;i++) //todo а size тебе зачем?
        {
            if((getArraySpace()[i]!=null)&&(getArraySpace()[i].getArea()>flatMaxArea.getArea())){
                flatMaxArea = (Flat)getArraySpace()[i];
            }
        }
        return  flatMaxArea;
    }

}