package buildings;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;


public class OfficeFloor implements Floor {
    class Node {
        Node next;    // указатель на следующий элемент
        Office valueOffice;  // todo Space а не Office!
    }
    //todo здесь логично хранить size, как в DwellingFloor
    private Node head;
    //приватный метод получения узла по его номеру
    private Node getNode(int number){
        Node temp = head;  //todo имя гавно
        for(int i=0;i<number;i++)
        {
            temp = temp.next;
        }
        return temp;
    }
    //приватный метод добавления узла в список по номеру
    private void addNode(int number, Node node){
        Node temp = head; //todo имя гавно
        for(int i=0;i<number-1;i++) //todo а дублировать код метода getNode() не стремно? Делаешь проверку number ==0 , если да - то работаешь с head, если нет - вызываешь getNode(number - 1)
        {
            temp = temp.next;
        }
        node.next=temp.next;
        temp.next=node;
    }
    //приватный метод удаления узла из списка по его номеру
    private void removeNode(int number){
        Node temp = head; //todo имя гавно
        for(int i=0;i<number-1;i++) //todo аналогично addNode()
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
        Node temp = head; //todo имя гавно
        for(int i=1;i<countOffice;i++) //todo Не надо здесь создавать структуру нодов, ибо при вставке ты все равно новые ноды создаешь. Просто создаешь head и все
        {
            Node node = new Node();
            node.valueOffice = new Office();
            temp.next=node;
            temp=node;
        }
        temp.next=head;
    }
    //Конструктор может принимать массив офисов этажа
    public OfficeFloor(Office [] arrayOffice){ //todo массив не Office[], a Space[]
        this();
        head.valueOffice=arrayOffice[0];
        Node temp = head; //todo имя гавно
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
        //todo Я начал уже тебя ненавидеть =)))))))) Храни поле size, чтоб каждый раз не пересчитывать число space-ов
        Node temp = head; //todo имя гавно
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
        Node temp = head; //todo имя гавно
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
    public Office[] getArraySpace(){ //todo возвращаешь Space[], а не Office[]
       Office [] array = new Office[getSize()];
       for (int i=0;i<array.length;i++){
           //todo нененене getNode(i) здесь вообще не эффективно. Гуляешь по нодам в цикле сама (Как в предыдущих методах) и каждый раз идешь к следующему по ссылке next. За один проход, а не за n*n как у тебя
           array[i]=getNode(i).valueOffice;
       }
       return array;
    }
    //метод получения офиса по его номеру на этаже
    public Office getSpace(int number) //todo возвращаешь Space, а не Office
    {
        return getNode(number).valueOffice;
    }
    //метод изменения офиса по его номеру на этаже и ссылке на обновленный офис
    public void setSpace(int number, Space newOffice)
    {
        if ((number > getSize())||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        getNode(number).valueOffice=(Office)newOffice; //todo каст уйдет, как поменяешь тип поля value в ноде
    }
    //метод добавления нового офиса на этаже по будущему номеру офиса
    public void addNewSpace(int number, Space newOffice) {
        if ((number > getSize())||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
         Node newNode = new Node();
         newNode.valueOffice = (Office) newOffice; //todo каст уйдет, как поменяешь тип поля value в ноде
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
        Office officeMaxArea = getNode(0).valueOffice; //todo Space, а не Office
        for(int i=1; i<getSize();i++)
        {
            //todo нененене getNode(i) здесь вообще не эффективно. Гуляешь по нодам в цикле сама и каждый раз идешь к следующему по ссылке next. За один проход, а не за n*n как у тебя
            if((getNode(i).valueOffice!=null)&&(getNode(i).valueOffice.getArea()>officeMaxArea.getArea())){
                officeMaxArea = getNode(i).valueOffice;
            }
        }
        return  officeMaxArea;
    }



}
