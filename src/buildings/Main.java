package buildings;

public class Main {

  public static void main(String[] args) {
      System.out.println("Dwelling");
      int [] countFlat = {2,5};
      Dwelling dwelling = new Dwelling(2,countFlat);
      System.out.println(dwelling.getCountSpace());
      dwelling.getArrayFloors()[0].addNewSpace(0,new Flat(20,1));
      dwelling.getArrayFloors()[0].addNewSpace(1,new Flat(30,2));
      dwelling.getArrayFloors()[0].addNewSpace(2,new Flat(15,1));
      dwelling.getArrayFloors()[0].setSpace(0,new Flat(45,1));
      dwelling.getArrayFloors()[1].addNewSpace(0,new Flat(55,2));
      dwelling.getArrayFloors()[1].addNewSpace(1,new Flat(70,1));
      System.out.println(dwelling.getAreaSpace());
      System.out.println(dwelling.getCountSpace());
      dwelling.addNewSpace(4,new Flat(90,1));
      System.out.println(dwelling.getAreaSpace());
      System.out.println(dwelling.getCountRoomsOnBuilding());
      System.out.println(dwelling.getBestSpace().getArea());
      dwelling.getArrayFloors()[1].removeSpace(0);
      System.out.println(dwelling.getBestSpace().getArea());
      for (int i = 0; i<dwelling.getCountSpace(); i++)
      {
          System.out.print(dwelling.sortByAreaSpace()[i].getArea()+" ");
      }
      System.out.println();
      System.out.println("Office");
    OfficeFloor of = new OfficeFloor(new Office[] {new Office(440,6),new Office(490,3),new Office(410,4)});
    OfficeFloor of2 = new OfficeFloor(new Office[] {new Office(200,3),new Office(478,8),new Office(600,10)});
    OfficeFloor of3 = new OfficeFloor(new Office[] {new Office(20,3),new Office(48,8),new Office(60,10)});
    for(int i=0;i<of.getSize();i++)
    {
      System.out.print(of.getSpace(i).getArea()+" "+of.getSpace(i).getNumberOfRooms()+";");
    }
System.out.println();
    of.addNewSpace(3,new Office(300,2));
    for(int i=0;i<of.getSize();i++)
    {
      System.out.print(of.getSpace(i).getArea()+" "+of.getSpace(i).getNumberOfRooms()+";");
    }
    System.out.println();
    System.out.println(of.getBestSpace().getArea());
    of.removeSpace(1);
    for(int i=0;i<of.getSize();i++)
    {
      System.out.print(of.getSpace(i).getArea()+" "+of.getSpace(i).getNumberOfRooms()+";");
    }
    System.out.println();
    System.out.println(of.getAreaSpace());
    System.out.println(of.getCountRoomsOnSpace());
    System.out.println();
    OfficeBuilding ofb = new OfficeBuilding(new OfficeFloor[] {of,of2,of3});
    for(int i=0;i<ofb.getCountSpace();i++)
    {
      System.out.print(ofb.getSpace(i).getArea()+" "+ofb.getSpace(i).getNumberOfRooms()+";");
    }
    System.out.println();
    for (int i=0;i<ofb.getCountSpace();i++)
    {
      if(ofb.sortByAreaSpace()[i].getArea()!=-1)
        System.out.print(ofb.sortByAreaSpace()[i].getArea()+" ");
    }
    System.out.println();
    System.out.println(ofb.getBestSpace().getArea());
    ofb.removeSpace(8);
    System.out.println(ofb.getFloor(2).getBestSpace().getArea());
  }
}