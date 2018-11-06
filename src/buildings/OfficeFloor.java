package buildings;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;


public class OfficeFloor implements Floor {
    class Node {
        Node next;    // указатель на следующий элемент
        Space valueOffice;  // todo Space а не Office!
    }

    int  size;//todo здесь логично хранить size, как в DwellingFloor
    private Node head;
    //приватный метод получения узла по его номеру
    private Node getNode(int number){
        Node currentNode = head;  //todo имя гавно
        for(int i=0;i<number;i++)
        {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
    //приватный метод добавления узла в список по номеру
    private void addNode(int number, Node node){
        Node searchNode; //todo имя гавно
        //todo а дублировать код метода getNode() не стремно? Делаешь проверку number ==0 , если да - то работаешь с head, если нет - вызываешь getNode(number - 1)
        if(number==0)
        {
            searchNode = head;
        }
        else{
            searchNode=getNode(number-1);
        }
        node.next=searchNode.next;
        searchNode.next=node;
    }
    //приватный метод удаления узла из списка по его номеру
    private void removeNode(int number){
        Node searchNode; //todo имя гавно
        //todo аналогично addNode()
         if(number==0)
         {
            searchNode = head;
         }
         else{
            searchNode=getNode(number-1);
         }
        searchNode.next=searchNode.next.next;
    }
    //Конструктор может принимать количество офисов на этаже
    public OfficeFloor(int countOffice){
        size=countOffice;
        head.valueOffice=new Office();
        Node currentNode = head; //todo имя гавно
        for(int i=1;i<countOffice;i++) //todo Не надо здесь создавать структуру нодов, ибо при вставке ты все равно новые ноды создаешь. Просто создаешь head и все
        {
            currentNode.next.valueOffice=new Office();
            currentNode=currentNode.next;
        }
        currentNode.next=head;
    }
    //Конструктор может принимать массив офисов этажа
    public OfficeFloor(Space [] arrayOffice){ //todo массив не Office[], a Space[]

        head = new Node();
        head.valueOffice=arrayOffice[0];
        size=arrayOffice.length;
        Node currentNode = head; //todo имя гавно
        for(int i=1;i<arrayOffice.length;i++)
        {
            currentNode.next.valueOffice=arrayOffice[i];
            currentNode=currentNode.next;
        }
        currentNode.next=head;
    }
    //метод получения количества офисов на этаже
    public int getSize(){
        //todo Я начал уже тебя ненавидеть =)))))))) Храни поле size, чтоб каждый раз не пересчитывать число space-ов
        return size;
    }
    //метод получения общей площади помещений этажа
    public int getAreaSpace(){
        Node currentNode = head; //todo имя гавно
        int  area = 0;
        do
        {
            area+=currentNode.valueOffice.getArea();
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return area;
    }
    //метод получения общего количества комнат этажа
    public int getCountRoomsOnSpace(){
        Node temp = head; //todo имя гавно
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
    public Space[] getArraySpace(){ //todo возвращаешь Space[], а не Office[]
       Space [] array = new Space[size];
       Node currentNode = head;
       int i=0;
        //todo нененене getNode(i) здесь вообще не эффективно. Гуляешь по нодам в цикле сама (Как в предыдущих методах) и каждый раз идешь к следующему по ссылке next. За один проход, а не за n*n как у тебя
        do
        {
            array[i]=getNode(i).valueOffice;
            currentNode = currentNode.next;
            i++;
        }
        while (currentNode!=head);
       return array;
    }
    //метод получения офиса по его номеру на этаже
    public Space getSpace(int number) //todo возвращаешь Space, а не Office
    {
        return getNode(number).valueOffice;
    }
    //метод изменения офиса по его номеру на этаже и ссылке на обновленный офис
    public void setSpace(int number, Space newOffice)
    {
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        getNode(number).valueOffice=newOffice; //todo каст уйдет, как поменяешь тип поля value в ноде
    }
    //метод добавления нового офиса на этаже по будущему номеру офиса
    public void addNewSpace(int number, Space newOffice) {
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
         Node newNode = new Node();
         newNode.valueOffice =  newOffice; //todo каст уйдет, как поменяешь тип поля value в ноде
         addNode(number, newNode);
         size++;

    }
    //метод удаления офиса по его номеру на этаже
    public void removeSpace(int number){
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        removeNode(number);
        size--;

    }
    //метод getBestSpace() получения самого большого по площади офиса этажа
    public Space getBestSpace(){
        Space officeMaxArea = head.valueOffice; //todo Space, а не Office
        Node currentNode = head.next;
        //todo нененене getNode(i) здесь вообще не эффективно. Гуляешь по нодам в цикле сама (Как в предыдущих методах) и каждый раз идешь к следующему по ссылке next. За один проход, а не за n*n как у тебя
        do
        {
            if(currentNode.valueOffice.getArea()>officeMaxArea.getArea()){
                officeMaxArea = currentNode.valueOffice;
            }
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return  officeMaxArea;
    }



}
