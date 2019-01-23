package buildings;

import buildings.exception.FloorIndexOutOfBoundsException;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;

public class OfficeBuilding implements Building, Cloneable, Serializable {
    private class Node implements Cloneable,Serializable{
        Node next;
        Node past;
        Floor valueOfficeFloor;
        public Node(){

        }
        public Object clone() throws CloneNotSupportedException {
            Node nodeClone = (Node) super.clone();
            nodeClone.valueOfficeFloor = (OfficeFloor) this.valueOfficeFloor.clone();
            return nodeClone;
        }
    }
    private Node head;
    private int size;
    //приватный метод получения узла по его номеру
    private Node getNode(int number){
        Node currentNode  = head;
        for(int i=0;i<number;i++)
        {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
    //приватный метод добавления узла в список по номеру
    private void addNode(int number, Node node){
        Node previousNode; //todo previousNode
        if(number==0){
            previousNode = head;
        }
        else {
            previousNode = getNode(number - 1);
        }
        previousNode.next.past=node;
        node.next=previousNode.next;
        node.past =previousNode;
        previousNode.next=node;
    }
    //приватный метод удаления узла из списка по его номеру
    private void removeNode(int number){
        Node previousNode; //todo previousNode
        if(number==0){
            previousNode = head;
        }
        else {
            previousNode = getNode(number - 1);
        }
        previousNode.next=previousNode.next.next;
        previousNode.next.past=previousNode;
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
            Node currentNode = head;
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
    public OfficeBuilding (Floor [] arrayFloors) {
        this();
        size = arrayFloors.length;
        head.valueOfficeFloor=arrayFloors[0];
        Node currentNode = head;
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
        return size;
    }
    //метод получения общего количества квартир офффисного здания
    public int getCountSpace() {
        int countFlats = 0;
        Node currentNode = head;
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
        Node currentNode = head;
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
        Node currentNode = head;
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
        Floor[] arrayOffice = new Floor[getSize()];
        int i = 0;
        Node currentNode = head;
        do
        {
            arrayOffice[i] = currentNode.valueOfficeFloor;
            currentNode = currentNode.next;
            i++;
        }
        while (currentNode!=head);
        return arrayOffice;
    }
    //метод проверки находения номера в границах массива этажа
    public void checkBounds(int number)
    {
        if ((number >= getSize())||(number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
    }
    //метод получения объкта этажа по его номеру в офффисном здании
    public  Floor getFloor(int number)
    {
        checkBounds(number); //todo почему не вынесла в отдельный метод checkBounds?
         return getNode(number).valueOfficeFloor;
    }
    //метод изменения этажа по его номеру в здании и ссылке на обновленный этаж.
    public void setFloor (int number, Floor newFloor)
    {
        checkBounds(number); //todo почему не вынесла в отдельный метод checkBounds?
        getNode(number).valueOfficeFloor=newFloor;

    }
    private LocationSpaceDTO getLocationOffice(int number)
    {
        LocationSpaceDTO location = null;
        int sizeCurrentsFloor; //todo имя гавно
        int sumSizePassedFloors = 0; //todo имя гавно
        if ((number >= getCountSpace())||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node currentNode = head;
        int i=0;
        do
        {
            sizeCurrentsFloor = currentNode.valueOfficeFloor.getSize();
            if(number-sumSizePassedFloors>=sizeCurrentsFloor){
                sumSizePassedFloors += sizeCurrentsFloor;
            }
            else{
                location=new LocationSpaceDTO(i,number-sumSizePassedFloors);
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

        LocationSpaceDTO searchLocationSpace = getLocationOffice(number); //todo spaceLocation?
        if(searchLocationSpace!=null) {
            return getFloor(searchLocationSpace.floorNumber).getSpace(searchLocationSpace.spaceNumber);
        }
        else  return null;
    }
    //метод изменения объекта офиса по его номеру в доме и ссылке типа офиса
    public void setSpace(int number, Space newOffice) {

        LocationSpaceDTO searchLocationSpace = getLocationOffice(number); //todo
        if(searchLocationSpace!=null) {
            getFloor(searchLocationSpace.floorNumber).setSpace(searchLocationSpace.spaceNumber, newOffice);
        }
    }
    //метод добавления офиса в здание по номеру офиса в здании и ссылке на офис
    public void addNewSpace(int number, Space newFlat)
    {
        LocationSpaceDTO searchLocationSpace = getLocationOffice(number); //todo
        if(searchLocationSpace!=null) {
            getFloor(searchLocationSpace.floorNumber).addNewSpace(searchLocationSpace.spaceNumber, newFlat);
        }
        else getFloor(getSize()-1).addNewSpace(number-getCountSpace()+ getFloor(getSize()-1).getSize(), newFlat);
    }
    //метод удаления офиса по его номеру в здании.
    public void removeSpace(int number)
    {
        LocationSpaceDTO searchLocationSpace = getLocationOffice(number); //todo
        if(searchLocationSpace!=null) {
            getFloor(searchLocationSpace.floorNumber).removeSpace(searchLocationSpace.spaceNumber);
        }
    }
    //метод получения самого большого по площади офиса здания
    public Space getBestSpace(){

        Node currentNode = head.next;
        Space officeMaxArea = head.valueOfficeFloor.getBestSpace();
        do
        {
            Space currentSpace = currentNode.valueOfficeFloor.getBestSpace();
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
        Floor temporaryFloor;
        Space temporaryOffice; //todo имя гавно
        Space [] arrayFlat = new Space[getCountSpace()];
        for(int i=0;i<getSize();i++) {
            /* todo в каждой итерации аж 3!!!!!! раза вызываешь getFloor(i) заставляя проходиться по нодам от head до i-го года.
               Блин на для массивов так норм, ибо внутри простая арифметика, для нодовых структур - нифига подобного.
               Записывай в переменную емае */
            temporaryFloor = getFloor(i);
            for(int j = 0; j< temporaryFloor.getSize(); j++) {
                if(temporaryFloor.getSpace(j)!=null)
                    arrayFlat[officeCount] = temporaryFloor.getSpace(j);
                officeCount++;
            }
        }
        for (int out = officeCount - 1; out >= 1; out--){
            for (int in = 0; in < out; in++){
                if(arrayFlat[in].getArea() < arrayFlat[in + 1].getArea()) {
                    temporaryOffice = arrayFlat[in];
                    arrayFlat[in] = arrayFlat[in + 1];
                    arrayFlat[in + 1] = temporaryOffice;
                }
            }
        }
        return arrayFlat;
    }
    public Object clone() throws CloneNotSupportedException{
        OfficeBuilding clone = (OfficeBuilding) super.clone();
        clone.head = (Node)head.clone();
        Node currentNode = head;
        Node currentCloneNode = clone.head;
        do {
            currentCloneNode.next = (Node) currentNode.next.clone();
            currentCloneNode = currentCloneNode.next;
            currentNode = currentNode.next;
        }
        while (currentNode.next!=head);
        clone.head.past = currentCloneNode;
        currentCloneNode.next=clone.head;
        return  clone;
    }
    @Override
    public String toString(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("OfficeBuilding (").append(size).append(", ");
        Node currentNode = head;
        do
        {
            finalString.append(currentNode.valueOfficeFloor.toString()).append(", ");
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return finalString.delete(finalString.length()-2,finalString.length()).append(")").toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
        if (!(obj instanceof OfficeBuilding)) {
            return false;
        }
        OfficeBuilding guest = (OfficeBuilding) obj;
        if(guest.size!=size)return false;
        Node currentNode = head;
        Node currentNodeGuest = guest.head;
        do {
            if(!currentNodeGuest.valueOfficeFloor.equals(currentNode.valueOfficeFloor)){
                return false;
            }
            currentNode = currentNode.next;
            currentNodeGuest=currentNodeGuest.next;
        }
        while (currentNode!=head);
        return true;
    }
    @Override
    public int hashCode() {
        int hashcode=size;
        Node currentNode = head;
        do
        {
            hashcode^=currentNode.valueOfficeFloor.hashCode();
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return hashcode;
    }
}

