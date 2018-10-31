package buildings;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class OfficeFloor implements Floor {
    class Node {
        Node next;    // указатель на следующий элемент
        Office valueOffice;  // данные
    }
    private Node head;
    //приватный метод получения узла по его номеру
    private Node getNode(int number){
        Node temp = head;
        for(int i=0;i<number;i++)
        {
            temp = temp.next;
        }
        return temp;
    }
    //приватный метод добавления узла в список по номеру
    private void addNode(int number, Node node){
        Node temp = head;
        for(int i=0;i<number-1;i++)
        {
            temp = temp.next;
        }
        node.next=temp.next;
        temp.next=node;
    }
    //приватный метод удаления узла из списка по его номеру
    private void removeNode(int number){
        Node temp = head;
        for(int i=0;i<number-1;i++)
        {
            temp = temp.next;
        }
        temp.next=temp.next.next;
    }
    public OfficeFloor()
    {
        head = new Node();
        head.next = head;
    }
    //Конструктор может принимать количество офисов на этаже
    public OfficeFloor(int countOffice){
        this();
        head.valueOffice=new Office();
        Node temp = head;
        for(int i=1;i<countOffice;i++)
        {
            Node node = new Node();
            node.valueOffice = new Office();
            temp.next=node;
            temp=node;
        }
        temp.next=head;
    }
    //Конструктор может принимать массив офисов этажа
    public OfficeFloor(Office [] arrayOffice){
        this();
        head.valueOffice=arrayOffice[0];
        Node temp = head;
        for(int i=1;i<arrayOffice.length;i++)
        {
            Node node = new Node();
            node.valueOffice = arrayOffice[i];
            temp.next=node;
            temp=node;
        }
        temp.next=head;
    }
    //метод получения количества офисов на этаже
    public int getSize(){
        Node temp = head;
        int  count = 0;
        do{
            count++;
            temp = temp.next;
        }
        while (temp!=head);
        return count;
    }
    //метод получения общей площади помещений этажа
    public int getAreaSpace(){
        Node temp = head;
        int  area = 0;
        do
        {
            area+=temp.valueOffice.getArea();
            temp = temp.next;
        }
        while (temp!=head);
        return area;
    }
    //метод получения общего количества комнат этажа
    public int getCountRoomsOnSpace(){
        Node temp = head;
        int  countOfRooms = 0;
        do
        {
            countOfRooms+=temp.valueOffice.getNumberOfRooms();
            temp = temp.next;
        }
        while (temp!=head);
        return countOfRooms;
    }
    //метод получения массива офисов этажа
    public Office[] getArraySpace(){
       Office [] array = new Office[getSize()];
       for (int i=0;i<array.length;i++){
           array[i]=getNode(i).valueOffice;
       }
       return array;
    }
    //метод получения офиса по его номеру на этаже
    public Office getSpace(int number)
    {
        return getNode(number).valueOffice;
    }
    //метод изменения офиса по его номеру на этаже и ссылке на обновленный офис
    public void setSpace(int number, Space newOffice)
    {
        if ((number > getSize())||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        getNode(number).valueOffice=(Office)newOffice;
    }
    //метод добавления нового офиса на этаже по будущему номеру офиса
    public void addNewSpace(int number, Space newOffice) {
        if ((number > getSize())||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
         Node newNode = new Node();
         newNode.valueOffice = (Office) newOffice;
         addNode(number, newNode);

    }
    //метод удаления офиса по его номеру на этаже
    public void removeSpace(int number){
        if ((number > getSize())||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        removeNode(number);

    }
    //метод getBestSpace() получения самого большого по площади офиса этажа
    public Office getBestSpace(){
        Office officeMaxArea = getNode(0).valueOffice;
        for(int i=1; i<getSize();i++)
        {
            if((getNode(i).valueOffice!=null)&&(getNode(i).valueOffice.getArea()>officeMaxArea.getArea())){
                officeMaxArea = getNode(i).valueOffice;
            }
        }
        return  officeMaxArea;
    }



}
