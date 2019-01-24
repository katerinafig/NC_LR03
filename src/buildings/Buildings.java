package buildings;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;

import java.io.*;
import java.util.Scanner;

public class Buildings {

    //Метод записи данных о здании в байтовый поток
    public static void outputBuilding(Building building, OutputStream out) throws IOException {
        Floor floor;
        Space space;
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        int countFloors = building.getSize();
        dataOutputStream.writeInt(countFloors);
        for (int i = 0; i < countFloors; i++) {
            floor = building.getFloor(i);
            dataOutputStream.writeInt(floor.getSize());
            for (int j = 0; j < floor.getSize(); j++) {
                space = floor.getSpace(j);
                dataOutputStream.writeInt(space.getNumberOfRooms());
                dataOutputStream.writeInt(space.getArea());
            }
        }
    }

    //Метод чтения данных о здании из байтового потока
    public static Building inputBuilding(InputStream in) throws IOException {
        Floor floor;
        int countSpaces, area, numberOfRooms;
        DataInputStream dataInputStream = new DataInputStream(in);
        int countFloors = dataInputStream.readInt();
        Floor[] arrayFloors = new Floor[countFloors];
        for (int i = 0; i < countFloors; i++) {
            countSpaces = dataInputStream.readInt();
            floor = new DwellingFloor(countSpaces);
            for (int j = 0; j < countSpaces; j++) {
                numberOfRooms = dataInputStream.readInt();
                area = dataInputStream.readInt();
                floor.addNewSpace(j, new Flat(area, numberOfRooms));
            }
            arrayFloors[i] = floor;
        }
        return new Dwelling(arrayFloors);
    }

    //Метод записи здания в символьный поток
    public static void writeBuilding(Building building, Writer out) throws IOException {
        Floor floor;
        Space space;
        PrintWriter printWriter = new PrintWriter(out);
        int countFloors = building.getSize();
        printWriter.printf("%d ", countFloors);
        for (int i = 0; i < countFloors; i++) {
            floor = building.getFloor(i);
            printWriter.printf("%d ", floor.getSize());
            for (int j = 0; j < floor.getSize(); j++) {
                space = floor.getSpace(j);
                printWriter.printf("%d ", space.getNumberOfRooms());
                printWriter.printf("%d ", space.getArea());
            }
        }
        printWriter.println();
    }

    //Метод чтения здания из символьного потока
    public static Building readBuilding(Reader in) throws IOException {
        int rooms, area;
        StreamTokenizer streamTokenizer = new StreamTokenizer(in);
        streamTokenizer.nextToken();
        Floor[] floors = new Floor[(int) streamTokenizer.nval];
        streamTokenizer.nextToken();
        for (int i = 0, sizeFloors = floors.length; i < sizeFloors; i++) {
            Space[] flats = new Space[(int) streamTokenizer.nval];
            streamTokenizer.nextToken();
            for (int j = 0, sizeFlats = flats.length; j < sizeFlats; j++) {
                rooms = (int) streamTokenizer.nval;
                streamTokenizer.nextToken();
                area = (int) streamTokenizer.nval;
                flats[j] = new Office(area, rooms);
                streamTokenizer.nextToken();
            }
            floors[i] = new OfficeFloor(flats);
        }
        return new OfficeBuilding(floors);
    }

    //Метод сериализации здания в байтовый поток
    public static void serializeBuilding (Building building,OutputStream out)throws IOException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(building);
    }

    //Метод десериализации здания из байтового потока
    public static Building deserializeBuilding (InputStream in)throws IOException, ClassNotFoundException{
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        return (Building) objectInputStream.readObject();
    }

    //Метод текстовой записи, использующий возможности форматированного вывода
    public static void writeBuildingFormat (Building building, Writer out){
        Floor floor;
        Space space;
        PrintWriter printWriter = new PrintWriter(out);
        int countFloors = building.getSize(), countSpaces;
        printWriter.println("Здание");
        printWriter.println("Количество этажей: " + countFloors);
        printWriter.println();
        for (int i = 0; i < countFloors; i++) {
            floor = building.getFloor(i);
            printWriter.println("Этаж " + i);
            countSpaces = floor.getSize();
            printWriter.println("Количество помещений " + countSpaces);
            for (int j = 0; j < countSpaces; j++) {
                printWriter.println();
                printWriter.println("Помещение " + j);
                space = floor.getSpace(j);
                printWriter.println("Площадь помещения: " + space.getArea());
                printWriter.println("Количество комнат в помещении: " + space.getNumberOfRooms());
            }
            printWriter.println();
        }
    }

    //Метод текстового чтения с помощью класса Scanner
    public static Building readBuilding(Scanner scanner) {
            int sizeOfBuilding = scanner.nextInt();
            Floor[] floors = new Floor[sizeOfBuilding];
            for (int i = 0; i < sizeOfBuilding; i++) {
                int sizeOfFloor = scanner.nextInt();
                floors[i] = new OfficeFloor(sizeOfFloor);
                for (int j = 0; j < sizeOfFloor; j++) {
                    int roomCount = scanner.nextInt();
                    int area = scanner.nextInt();
                    floors[i].setSpace(j, new Office(roomCount, area));

                }
            }
            return new OfficeBuilding(floors);
    }
}
