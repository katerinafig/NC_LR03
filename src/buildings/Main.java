package buildings;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;
import buildings.threads.*;

import java.io.*;

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
            dwelling.getArrayFloors()[0].setSpace(0, new Flat(666, 3));
            System.out.println(clone.toString());
            System.out.println("Office");
            OfficeFloor of = new OfficeFloor(new Office[]{new Office(440, 6), new Office(490, 3), new Office(410, 4)});
            OfficeFloor of2 = new OfficeFloor(new Office[]{new Office(200, 3), new Office(478, 8)});
            OfficeFloor of3 = new OfficeFloor(new Office[]{new Office(20, 3), new Office(48, 8), new Office(60, 10)});
            for (int i = 0; i < of.getSize(); i++) {
                System.out.print(of.getSpace(i).getArea() + " " + of.getSpace(i).getNumberOfRooms() + ";");
            }
            System.out.println();
            for (int i = 0; i < of.getSize(); i++) {
                System.out.print(of.getSpace(i).getArea() + " " + of.getSpace(i).getNumberOfRooms() + ";");
            }
            System.out.println();
            System.out.println(of.getBestSpace().getArea());
            System.out.println(of.getAreaSpace());
            System.out.println(of.getCountRoomsOnSpace());
            OfficeBuilding ofb = new OfficeBuilding(new OfficeFloor[]{of, of2, of3});
            System.out.println(ofb.getBestSpace().getArea());
            ofb.removeSpace(7);
            System.out.println(ofb.getFloor(2).getBestSpace().getArea());

            //Демонстрация кланирования
            System.out.println(ofb.toString());
            OfficeFloor ofnew = new OfficeFloor(new Office[]{new Office(666, 3), new Office(478, 8)});
            OfficeBuilding ofbnewClone = (OfficeBuilding) ofb.clone();
            System.out.println();
            System.out.println(ofb.toString());
            System.out.println(ofbnewClone.toString());
            ofbnewClone.setSpace(1, new Office(234, 9));
            System.out.println(ofb.toString());
            System.out.println(ofbnewClone.toString());

            //Демонстрация байтовых записи и чтения
            System.out.println();
            FileOutputStream fos = new FileOutputStream("src/example.bin");
            Buildings.outputBuilding(ofb, fos);
            fos.close();
            FileInputStream fis = new FileInputStream("src/example.bin");
            System.out.println(PlacementExchanger.isFloorExchange(dwelling.getArrayFloors()[0], ofnew));
            System.out.println(Buildings.inputBuilding(fis));
            fis.close();

            //Демонстрация символьных записи и чтения
            System.out.println();
            FileWriter writer = new FileWriter("src/example2.txt");
            Buildings.writeBuilding(ofb, writer);
            writer.close();
            FileReader reader = new FileReader("src/example2.txt");
            System.out.println(Buildings.readBuilding(reader));
            reader.close();

            //Демонстрация сериализации и десериализации
            System.out.println();
            FileOutputStream fos2 = new FileOutputStream("src/example3.bin");
            Buildings.serializeBuilding(dwelling,fos2);
            fos2.close();
            FileInputStream fis2 = new FileInputStream("src/example3.bin");
            Building newDwelling = Buildings.deserializeBuilding(fis2);
            System.out.println("Десериализированный: "+newDwelling.toString());
            fis2.close();
            System.out.println("Исходный: "+dwelling.toString());

            //Демонстрация нитей
            Floor floor1 = new OfficeFloor(new Office[]{new Office(110, 1), new Office(320, 2), new Office(120, 3)});
            Floor floor2 = new DwellingFloor(new Flat[]{new Flat(550, 1), new Flat(240, 2), new Flat(890, 3)});
            Floor floor3 = new OfficeFloor(new Office[]{new Office(80, 1), new Office(45, 2), new Office(345, 3)});
            Semaphore semaphore1 = new Semaphore(true);
            Semaphore semaphore2 = new Semaphore(true);
            Semaphore semaphore3 = new Semaphore(true);
            Thread repairer1 = new Thread(new SequentalRepairer(floor1,semaphore1));
            Thread repairer2 = new Thread(new SequentalRepairer(floor2,semaphore2));
            Thread repairer3 = new Thread(new SequentalRepairer(floor3,semaphore3));
            Thread cleaner1 = new Thread(new SequentalCleaner(floor1,semaphore1));
            Thread cleaner2 = new Thread(new SequentalCleaner(floor2,semaphore2));
            Thread cleaner3 = new Thread(new SequentalCleaner(floor3,semaphore3));
            repairer1.start();
            repairer2.start();
            repairer3.start();
            cleaner1.start();
            cleaner2.start();
            cleaner3.start();

        } catch (IOException ex2) {
            System.out.println("IOex");
        } catch (CloneNotSupportedException ex) {
            System.out.println("Cloneable not implemented");
        }catch (ClassNotFoundException ex3) {
            System.out.println("ClassNotFoundException");
        }


    }
}