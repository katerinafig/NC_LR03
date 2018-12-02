package buildings;

public class Main {

  public static void main(String[] args) {
      try {
          System.out.println("Dwelling");
          int[] countFlat = {2, 5};
          Dwelling dwelling = new Dwelling(2, countFlat);
          System.out.println(dwelling.getCountSpace());
          dwelling.getArrayFloors()[0].addNewSpace(0, new Flat(200, 3));
          dwelling.getArrayFloors()[0].addNewSpace(1, new Flat(478, 8));
          dwelling.getArrayFloors()[1].addNewSpace(0, new Flat(20, 1));
          dwelling.getArrayFloors()[1].addNewSpace(1, new Flat(30, 2));
          System.out.println(dwelling.getAreaSpace());
          System.out.println(dwelling.getCountSpace());
          System.out.println(dwelling.getAreaSpace());
          System.out.println(dwelling.getCountRoomsOnBuilding());
          System.out.println(dwelling.getBestSpace().getArea());
          System.out.println(dwelling.getBestSpace().getArea());
          for (int i = 0; i < dwelling.getCountSpace(); i++) {
              System.out.print(dwelling.sortByAreaSpace()[i].getArea() + " ");
          }
          System.out.println();
          System.out.println(dwelling.toString());
          System.out.println(dwelling.getArrayFloors()[0].equals(dwelling.getArrayFloors()[1]));
          Dwelling clone = (Dwelling) dwelling.clone();
          dwelling.getArrayFloors()[0].setSpace(0, new Flat(666,3));
          System.out.println(clone.toString());
          System.out.println("Office");
          OfficeFloor of = new OfficeFloor(new Office[]{new Office(440, 6), new Office(490, 3), new Office(410, 4)});
          OfficeFloor of2 = new OfficeFloor(new Office[]{new Office(200, 3), new Office(478, 8)});
          OfficeFloor of3 = new OfficeFloor(new Office[]{new Office(20, 3), new Office(48, 8), new Office(60, 10)});
          for (int i = 0; i < of.getSize(); i++) {
              System.out.print(of.getSpace(i).getArea() + " " + of.getSpace(i).getNumberOfRooms() + ";");
          }
          System.out.println();
          of.addNewSpace(3, new Office(300, 2));
          for (int i = 0; i < of.getSize(); i++) {
              System.out.print(of.getSpace(i).getArea() + " " + of.getSpace(i).getNumberOfRooms() + ";");
          }
          System.out.println();
          System.out.println(of.getBestSpace().getArea());
          System.out.println(of.getAreaSpace());
          System.out.println(of.getCountRoomsOnSpace());
          OfficeBuilding ofb = new OfficeBuilding(new OfficeFloor[]{of, of2, of3});
          System.out.println(ofb.getBestSpace().getArea());
          ofb.removeSpace(8);
          System.out.println(ofb.getFloor(2).getBestSpace().getArea());
          System.out.println(ofb.toString());
          OfficeBuilding clone2 = (OfficeBuilding) ofb.clone();
          of.setSpace(3, new Office(666,1));
          for(int k=0;k<clone2.getSize();k++) {
              for (int i = 0; i < clone2.getFloor(k).getSize(); i++) {
                  System.out.print(clone2.getFloor(k).getSpace(i).toString());
              }
              System.out.println();
          }

      }
      catch(CloneNotSupportedException ex){
      System.out.println("Cloneable not implemented");
      }

  }
}