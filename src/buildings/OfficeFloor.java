package buildings;
import buildings.exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class OfficeFloor implements Floor,Cloneable {
    class Node implements Cloneable {
        Node next;    // указатель на следующий элемент
        Space valueOffice;
        public Node(){
        }
        public Object clone() throws CloneNotSupportedException {
            Node nodeClone = (Node) super.clone();
            Node currentNodeClone = nodeClone;
            Node currentNode = head;
            currentNodeClone.valueOffice = (Office) this.valueOffice.clone();
            do {
                currentNodeClone.next.valueOffice = (Office)currentNode.next.valueOffice.clone();
                currentNodeClone = currentNodeClone.next;
                currentNode = currentNode.next;
            }while (currentNode!=head);
            return nodeClone;
        }
    }
    private int  size;
    private Node head;
    //приватный метод получения узла по его номеру
    private Node getNode(int number){
        Node currentNode = head;
        for(int i=0;i<number;i++)
        {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
    //приватный метод добавления узла в список по номеру
    private void addNode(int number, Node node){
        Node previousNode; //todo имя гавно. Ты определяешь предыдущий для нужного нода нод, так и называй его previousNode
        if(number==0)
        {
            previousNode = head;
        }
        else{
            previousNode=getNode(number-1);
        }
        node.next=previousNode.next;
        previousNode.next=node;
    }
    //приватный метод удаления узла из списка по его номеру
    private void removeNode(int number){
        Node previousNode; //todo имя гавно. Ты определяешь предыдущий для нужного нода нод, так и называй его previousNode
         if(number==0)
         {
            previousNode = head;
         }
         else{
            previousNode=getNode(number-1);
         }
        previousNode.next=previousNode.next.next;
    }
    //Конструктор может принимать количество офисов на этаже
    public OfficeFloor(int countOffice){
        head = new Node();
        head.valueOffice=new Office();
        Node currentNode = head;
        currentNode.next=head;
    }
    //Конструктор может принимать массив офисов этажа
    public OfficeFloor(Space[] arrayOffice){
        head = new Node();
        head.valueOffice=arrayOffice[0];
        size=arrayOffice.length;
        Node currentNode = head;
        for(int i=1;i<arrayOffice.length;i++)
        {
            Node node = new Node();
            node.valueOffice = arrayOffice[i];
            currentNode.next=node;
            currentNode=node;
        }
        currentNode.next=head;
    }
    //метод получения количества офисов на этаже
    public int getSize(){
        return size;
    }
    //метод получения общей площади помещений этажа
    public int getAreaSpace(){
        Node currentNode = head;
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
        Node currentNode = head; //todo имя гавно
        int  countOfRooms = 0;
        do
        {
            countOfRooms+=currentNode.valueOffice.getNumberOfRooms();
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return countOfRooms;
    }
    //метод получения массива офисов этажа
    public Space[] getArraySpace(){
       Space [] array = new Space[size];
       Node currentNode = head;
       int i=0;
        do
        {
            array[i]=currentNode.valueOffice;
            currentNode = currentNode.next;
            i++;
        }
        while (currentNode!=head);
       return array;
    }
    //метод получения офиса по его номеру на этаже
    public Space getSpace(int number)
    {
        return getNode(number).valueOffice;
    }
    //метод проверки находения номера в границах массива помещений
    public void checkBounds(int number)
    {
        if ((number > size)||(number < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }
    //метод изменения офиса по его номеру на этаже и ссылке на обновленный офис
    public void setSpace(int number, Space newOffice)
    {
        checkBounds(number); //todo почему не вынесла в отдельный метод checkBounds?
        getNode(number).valueOffice=newOffice;
    }
    //метод добавления нового офиса на этаже по будущему номеру офиса
    public void addNewSpace(int number, Space newOffice) {
         checkBounds(number); //todo почему не вынесла в отдельный метод checkBounds?
         Node newNode = new Node();
         newNode.valueOffice =  newOffice;
         addNode(number, newNode);
         size++;

    }
    //метод удаления офиса по его номеру на этаже
    public void removeSpace(int number){
        checkBounds(number); //todo почему не вынесла в отдельный метод checkBounds?
        removeNode(number);
        size--;

    }
    //метод getBestSpace() получения самого большого по площади офиса этажа
    public Space getBestSpace(){
        Space officeMaxArea = head.valueOffice;
        Node currentNode = head.next;
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
    public Object clone() throws CloneNotSupportedException{
        OfficeFloor clone = (OfficeFloor) super.clone();
        clone.size = this.size;
        clone.head = (Node)this.head.clone();
        return  clone;
    }
    @Override
    public String toString(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("OfficeFloor (").append(size).append(", ");
        Node currentNode = head;
        do
        {
            finalString.append(currentNode.valueOffice.toString()).append(", ");
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
        if (!(obj instanceof OfficeFloor)) {
            return false;
        }
        OfficeFloor guest = (OfficeFloor) obj;
        if(guest.size!=size)return false;
        Node currentNode = head;
        Node currentNodeGuest = guest.head;
        do {
            if(!currentNodeGuest.valueOffice.equals(currentNode.valueOffice)){
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
            hashcode^=currentNode.valueOffice.hashCode();
            currentNode = currentNode.next;
        }
        while (currentNode!=head);
        return hashcode;
    }

}
