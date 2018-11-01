package buildings;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class DwellingFloor implements Floor {
    private int size;
    private Flat [] arrayFlat; //todo в 3-й лабе ты работаешь с переменными типа Space. ПОтому и массив тоже будет типа Space
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
        for(int i=0; i<size;i++)
        {
            if(arrayFlat[i]!=null) //todo на null уже не надо проверять
            areaFlats += arrayFlat[i].getArea();
        }
        return areaFlats;
    }
    //метод получения общего количества комнат этажа
    public int getCountRoomsOnSpace() {
        int allNomberFlats = 0;
        for(int i=0; i<size;i++)
        {
            if(arrayFlat[i]!=null)//todo на null уже не надо проверять
           allNomberFlats += arrayFlat[i].getNumberOfRooms();
        }
        return allNomberFlats;
    }
    //метод получения массива квартир этажа
    public Space[] getArraySpace() {
        Flat [] newArrayFlat = new Flat[arrayFlat.length]; //todo Space, а не Flat
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
        //todo ты эту проверку 4 выполняешь абсолютно идентично в 4-х методах!!!! - выноси проверку в отдельный метод void checkBounds() и вызывай его
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
            arrayFlat[number]=(Flat)newFlat; //todo каст делаешь ибо нужно хранить массив типа Space, а ты хранишь Flat. Храни Space, тогда и каст делать не надо

    }
    //метод добавления новой квартиры на этаже по будущему номеру квартиры
    public void addNewSpace(int number, Space newFlat)
    {
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        if(size==arrayFlat.length) {
            if (number >= size) { //todo ==, а не >=. И это условие надо проверять не перед расширением массива, а после
                Flat[] newArrayFlat = new Flat[2 * arrayFlat.length];
                System.arraycopy(arrayFlat, 0, newArrayFlat, 0, arrayFlat.length);
                arrayFlat = newArrayFlat;
            }
        }
        else {
            System.arraycopy(arrayFlat, number, arrayFlat, number+1, arrayFlat.length-number-1);

        }
        arrayFlat[number]=(Flat) newFlat;  //todo каст делаешь ибо нужно хранить массив типа Space, а ты хранишь Flat. Храни Space, тогда и каст делать не надо
        size++;

    }
    //метод удаления квартиры по её номеру на этаже
    public void removeSpace(int number)
    {
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        if(arrayFlat[number]!=null) { //todo на null уже не надо проверять
            System.arraycopy(arrayFlat, number+1, arrayFlat, number, size-number-1);
        }
        size--;
    }
    //метод получения самой большой по площади квартиры этажа
    public Flat getBestSpace(){ //todo надо возвращать не Flat, а Space
        Flat flatMaxArea = (Flat)getArraySpace()[0];
        for(int i=1; i<size;i++)
        {
            //todo на null уже не надо проверять
            //todo Зачем? .... Зачем ты вызываешь метод getArraySpace()???? ПОчему не обращаешься к массиву напрямую ???
            if((getArraySpace()[i]!=null)&&(getArraySpace()[i].getArea()>flatMaxArea.getArea())){
                flatMaxArea = (Flat)getArraySpace()[i];
            }
        }
        return  flatMaxArea;
    }

}