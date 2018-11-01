package buildings;

import buildings.exception.FloorIndexOutOfBoundsException;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class OfficeBuilding implements Building {
    private static class Node{
        Node next;
        Node past;
        OfficeFloor valueOfficeFloor; //todo НЕЕЕЕТТТТТТ!!!!!! Тип Floor!!!!!
    }
    private Node head;
    //todo size в студию

    //приватный метод получения узла по его номеру
    private Node getNode(int number){
        Node temp = head; //todo имя гавно
        for(int i=0;i<number;i++)
        {
            temp = temp.next;
        }
        return temp;
    }
    //приватный метод добавления узла в список по номеру
    private void addNode(int number, Node node){
        Node temp = head; //todo имя гавно
        for(int i=0;i<number;i++) //todo а дублировать код метода getNode() не стремно? У тебя двусвязный список, - всегда есть возможность получить previous нод
        {
            temp = temp.next;
        }
        temp.next.past=node;
        node.next=temp.next;
        node.past =temp;
        temp.next=node;
    }
    //приватный метод удаления узла из списка по его номеру
    private void removeNode(int number){
        Node temp = head; //todo имя гавно
        for(int i=0;i<number-1;i++) //todo а дублировать код метода getNode() не стремно?
        {
            temp = temp.next;
        }
        temp.next=temp.next.next;
        temp.next.past=temp;
    }
    private OfficeBuilding() {
        head = new Node();
        head.next = head;
        head.past = head;
    }
    //Конструктор может принимать количество этажей и массив количества офисов по этажам
    public OfficeBuilding(int size,int [] countsOffice)
    {
        this();
        if(size==countsOffice.length) {
            head.valueOfficeFloor=new OfficeFloor(countsOffice[0]);
            Node temp = head; //todo имя гавно
            for (int i = 1; i < size; i++) {
                Node node = new Node();
                node.valueOfficeFloor = new OfficeFloor(countsOffice[i]);
                temp.next=node;
                node.past=temp;
                temp=node;
            }
            head.past = temp;
            temp.next=head;
        }
    }
    //Конструктор может принимать массив этажей дома.
    public OfficeBuilding (OfficeFloor [] arrayFloors) //todo не OfficeFloor[], а Floor[]
    {
        this();
        head.valueOfficeFloor=arrayFloors[0];
        Node temp = head; //todo имя гавно
        for (int i = 1; i < arrayFloors.length; i++) {
            Node node = new Node();
            node.valueOfficeFloor = arrayFloors[i];
            temp.next=node;
            node.past=temp;
            temp=node;
        }
        head.past = temp;
        temp.next=head;
    }
    //метод получения общего количества этажей оффисного здания
    public int getSize() {
        //todo Храни поле size, чтоб каждый раз не пересчитывать число floor-ов
        Node temp = head; //todo имя гавно
        int  count = 0;
        do{
            count++;
            temp = temp.next;
        }
        while (temp!=head);
        return count;
    }
    //метод получения общего количества квартир офффисного здания
    public int getCountSpace() {
        int countFlats = 0;
        Node temp = head; //todo имя гавно
        do
        {
            countFlats+=temp.valueOfficeFloor.getSize();
            temp = temp.next;
        }
        while (temp!=head);
        return countFlats;
    }
    //метод получения общей площади оффисов офффисного здания
    public int getAreaSpace() {
        int areaFlats = 0;
        Node temp = head; //todo имя гавно
        do
        {
            areaFlats+=temp.valueOfficeFloor.getAreaSpace();
            temp = temp.next;
        }
        while (temp!=head);
        return areaFlats;
    }
    //метод получения общего количества оффисов офффисного здания
    public int getCountRoomsOnBuilding() {
        int countRooms = 0;
        Node temp = head; //todo имя гавно
        do
        {
            countRooms+=temp.valueOfficeFloor.getCountRoomsOnSpace();
            temp = temp.next;
        }
        while (temp!=head);
        return countRooms;
    }
    //метод получения массива этажей офффисного здания
    public Floor[] getArrayFloors() {
        OfficeFloor[] arrayOffice = new OfficeFloor[getSize()]; //todo не OfficeFloor[], а Floor[]
        int i = 0;
        Node temp = head; //todo имя гавно
        do
        {
            arrayOffice[i] = temp.valueOfficeFloor;
            temp = temp.next;
            i++;
        }
        while (temp!=head);
        return arrayOffice;
    }
    //метод получения объкта этажа по его номеру в офффисном здании
    public  Floor getFloor(int number)
    {
        if ((number >= getSize())||(number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        return getNode(number).valueOfficeFloor;
    }
    //метод изменения этажа по его номеру в здании и ссылке на обновленный этаж.
    public void setFloor (int number, Floor newFloor)
    {
        if ((number >= getSize())||(number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
            getNode(number).valueOfficeFloor=(OfficeFloor)newFloor; //todo каст уйдет, как поменяешь тип поля в ноде

    }
    private LocationOfficeDTO getLocationFlat(int number)
    {
        LocationOfficeDTO location = null;
        int tempSize=0; //todo имя гавно
        int count = 0; //todo имя гавно
        if ((number >= getCountSpace())||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        //todo нененене getFloor(i) здесь вообще не эффективно. Гуляешь по нодам в цикле сама (Как в предыдущих методах) и каждый раз идешь к следующему по ссылке next. За один проход, а не за n*n как у тебя
        for (int i = 0; i < getSize(); i++) {
            tempSize = getFloor(i).getSize();
            if(number-count>=tempSize){
                count += tempSize;
            }
            else{
                location=new LocationOfficeDTO(i,number-count);
                break;
            }
        }

        return location;
    }
    //метод получения объекта офиса по его номеру в офисном здании
    public Space getSpace(int number) {

        LocationOfficeDTO temp = getLocationFlat(number); //todo имя гавно
        if(temp!=null) {
            return getFloor(temp.floor).getSpace(temp.office);
        }
        else  return null;
    }
    //метод изменения объекта офиса по его номеру в доме и ссылке типа офиса
    public void setSpace(int number, Space newOffice) {

        LocationOfficeDTO temp = getLocationFlat(number); //todo имя гавно
        if(temp!=null) {
            getFloor(temp.floor).setSpace(temp.office, newOffice);
        }
    }
    //метод добавления офиса в здание по номеру офиса в здании и ссылке на офис
    public void addNewSpace(int number, Space newFlat)
    {
        LocationOfficeDTO temp = getLocationFlat(number); //todo имя гавно
        if(temp!=null) {
            getFloor(temp.floor).addNewSpace(temp.office, newFlat);
        }
        else getFloor(getSize()-1).addNewSpace(number-getCountSpace()+ getFloor(getSize()-1).getSize(), newFlat);
    }
    //метод удаления офиса по его номеру в здании.
    public void removeSpace(int number)
    {
        LocationOfficeDTO temp = getLocationFlat(number); //todo имя гавно
        if(temp!=null) {
            getFloor(temp.floor).removeSpace(temp.office);
        }
    }
    //метод получения самого большого по площади офиса здания
    public Space getBestSpace(){
        Office officeMaxArea = (Office)getFloor(0).getBestSpace(); //todo не Office, а Space
        for(int i=1;i<getSize();i++) {
            //todo нененене getFloor(i) здесь вообще не эффективно. Гуляешь по нодам в цикле сама и каждый раз идешь к следующему по ссылке next. За один проход, а не за n*n как у тебя
            Office temp = (Office)getFloor(i).getBestSpace(); //todo ну и имя - гавно =)))))), а тип - Space, а не Office
            if(officeMaxArea.getArea() < temp.getArea())
            {
                officeMaxArea = temp;
            }
        }
        return officeMaxArea;
    }
    //метод получения отсортированного по убыванию площадей массива офисов
    public Space [] sortByAreaSpace()
    {
        int k=0;
        Office buf = null; //todo имя гавно
        Office [] arrayFlat = new Office[getCountSpace()]; //todo Space[], а не Office[]
        for(int i=0;i<getSize();i++) {
            for(int j = 0; j< getFloor(i).getSize(); j++) {
                if(getFloor(i).getSpace(j)!=null)
                    arrayFlat[k] =  (Office) getFloor(i).getSpace(j);
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
