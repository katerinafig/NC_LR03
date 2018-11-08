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
        Floor valueOfficeFloor; //todo НЕЕЕЕТТТТТТ!!!!!! Тип Floor!!!!!
    }
    private Node head;
    private int size;//todo size в студию

    //приватный метод получения узла по его номеру
    private Node getNode(int number){
        Node currentNode  = head; //todo имя гавно
        for(int i=0;i<number;i++)
        {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
    //приватный метод добавления узла в список по номеру
    private void addNode(int number, Node node){
        Node searchNode; //todo имя гавно
         //todo а дублировать код метода getNode() не стремно? У тебя двусвязный список, - всегда есть возможность получить previous нод
        if(number==0){
            searchNode = head;
        }
        else {
            searchNode = getNode(number - 1);
        }
        searchNode.next.past=node;
        node.next=searchNode.next;
        node.past =searchNode;
        searchNode.next=node;
    }
    //приватный метод удаления узла из списка по его номеру
    private void removeNode(int number){
        Node searchNode; //todo имя гавно
        //todo а дублировать код метода getNode() не стремно?
        if(number==0){
            searchNode = head;
        }
        else {
            searchNode = getNode(number - 1);
        }
        searchNode.next=searchNode.next.next;
        searchNode.next.past=searchNode;
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
        this.size = size;
        if(size==countsOffice.length) {
            head.valueOfficeFloor=new OfficeFloor(countsOffice[0]);
            Node currentNode = head; //todo имя гавно
            for (int i = 1; i < size; i++) {
                Node node = new Node();
                node.valueOfficeFloor = new OfficeFloor(countsOffice[i]);
                currentNode.next=node;
                node.past=currentNode;
                currentNode=node;
            }
            head.past = currentNode;
            currentNode.next=head;
        }
    }
    //Конструктор может принимать массив этажей дома.
    public OfficeBuilding (Floor [] arrayFloors) //todo не OfficeFloor[], а Floor[]
    {
        this();
        size = arrayFloors.length;
        head.valueOfficeFloor=arrayFloors[0];
        Node currentNode = head; //todo имя гавно
        for (int i = 1; i < arrayFloors.length; i++) {
            Node node = new Node();
            node.valueOfficeFloor = arrayFloors[i];
            currentNode.next=node;
            node.past=currentNode;
            currentNode=node;
        }
        head.past = currentNode;
        currentNode.next=head;
    }
    //метод получения общего количества этажей оффисного здания
    public int getSize() {
        //todo Храни поле size, чтоб каждый раз не пересчитывать число floor-ов
        return size;
    }
    //метод получения общего количества квартир офффисного здания
    public int getCountSpace() {
        int countFlats = 0;
        Node currentNode = head; //todo имя гавно
        do
        {
            countFlats+=currentNode.valueOfficeFloor.getSize();
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return countFlats;
    }
    //метод получения общей площади оффисов офффисного здания
    public int getAreaSpace() {
        int areaFlats = 0;
        Node currentNode = head; //todo имя гавно
        do
        {
            areaFlats+=currentNode.valueOfficeFloor.getAreaSpace();
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return areaFlats;
    }
    //метод получения общего количества оффисов офффисного здания
    public int getCountRoomsOnBuilding() {
        int countRooms = 0;
        Node currentNode = head; //todo имя гавно
        do
        {
            countRooms+=currentNode.valueOfficeFloor.getCountRoomsOnSpace();
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return countRooms;
    }
    //метод получения массива этажей офффисного здания
    public Floor[] getArrayFloors() {
        Floor[] arrayOffice = new Floor[getSize()]; //todo не OfficeFloor[], а Floor[]
        int i = 0;
        Node currentNode = head; //todo имя гавно
        do
        {
            arrayOffice[i] = currentNode.valueOfficeFloor;
            currentNode = currentNode.next;
            i++;
        }
        while (currentNode!=head);
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
            getNode(number).valueOfficeFloor=newFloor; //todo каст уйдет, как поменяешь тип поля в ноде

    }
    private LocationSpaceDTO getLocationSpace(int number)
    {
        LocationSpaceDTO location = null;
        int sizeFloor=0; //todo имя гавно
        int sumSizeFloors = 0; //todo имя гавно
        if ((number >= getCountSpace())||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        //todo нененене getFloor(i) здесь вообще не эффективно. Гуляешь по нодам в цикле сама (Как в предыдущих методах) и каждый раз идешь к следующему по ссылке next. За один проход, а не за n*n как у тебя
        Node currentNode = head;
        int i=0;
        do
        {
            sizeFloor = currentNode.valueOfficeFloor.getSize();
            if(number-sumSizeFloors>=sizeFloor){
                sumSizeFloors += sizeFloor;
            }
            else{
                location=new LocationSpaceDTO(i,number-sumSizeFloors);
                break;
            }
            currentNode = currentNode.next;
            i++;
        }
        while (currentNode!=head);
        return location;
    }
    //метод получения объекта офиса по его номеру в офисном здании
    public Space getSpace(int number) {

        LocationSpaceDTO searchLocationSpace = getLocationSpace(number); //todo имя гавно
        if(searchLocationSpace!=null) {
            return getFloor(searchLocationSpace.floorNumber).getSpace(searchLocationSpace.spaceNumber);
        }
        else  return null;
    }
    //метод изменения объекта офиса по его номеру в доме и ссылке типа офиса
    public void setSpace(int number, Space newOffice) {

        LocationSpaceDTO searchLocationSpace = getLocationSpace(number); //todo имя гавно
        if(searchLocationSpace!=null) {
            getFloor(searchLocationSpace.floorNumber).setSpace(searchLocationSpace.spaceNumber, newOffice);
        }
    }
    //метод добавления офиса в здание по номеру офиса в здании и ссылке на офис
    public void addNewSpace(int number, Space newFlat)
    {
        LocationSpaceDTO searchLocationSpace = getLocationSpace(number); //todo имя гавно
        if(searchLocationSpace!=null) {
            getFloor(searchLocationSpace.floorNumber).addNewSpace(searchLocationSpace.spaceNumber, newFlat);
        }
        else getFloor(getSize()-1).addNewSpace(number-getCountSpace()+ getFloor(getSize()-1).getSize(), newFlat);
    }
    //метод удаления офиса по его номеру в здании.
    public void removeSpace(int number)
    {
        LocationSpaceDTO searchLocationSpace = getLocationSpace(number); //todo имя гавно
        if(searchLocationSpace!=null) {
            getFloor(searchLocationSpace.floorNumber).removeSpace(searchLocationSpace.spaceNumber);
        }
    }
    //метод получения самого большого по площади офиса здания
    public Space getBestSpace(){

        Node currentNode = head.next;
        Space officeMaxArea = head.valueOfficeFloor.getBestSpace(); //todo не Office, а Space
        do
        {
            //todo нененене getFloor(i) здесь вообще не эффективно. Гуляешь по нодам в цикле сама и каждый раз идешь к следующему по ссылке next. За один проход, а не за n*n как у тебя
            Space currentSpace = currentNode.valueOfficeFloor.getBestSpace(); //todo ну и имя - гавно =)))))), а тип - Space, а не Office
            if(officeMaxArea.getArea() < currentSpace.getArea())
            {
                officeMaxArea = currentSpace;
            }
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return officeMaxArea;
    }
    //метод получения отсортированного по убыванию площадей массива офисов
    public Space [] sortByAreaSpace()
    {
        int officeCount=0;
        Space containerOffice; //todo имя гавно
        Space [] arrayFlat = new Space[getCountSpace()]; //todo Space[], а не Office[]
        for(int i=0;i<getSize();i++) {
            for(int j = 0; j< getFloor(i).getSize(); j++) {
                if(getFloor(i).getSpace(j)!=null)
                    arrayFlat[officeCount] = getFloor(i).getSpace(j);
                officeCount++;
            }
        }
        for (int out = officeCount - 1; out >= 1; out--){
            for (int in = 0; in < out; in++){
                if(arrayFlat[in].getArea() < arrayFlat[in + 1].getArea()) {
                    containerOffice = arrayFlat[in];
                    arrayFlat[in] = arrayFlat[in + 1];
                    arrayFlat[in + 1] = containerOffice;
                }
            }
        }
        return arrayFlat;
    }
}
