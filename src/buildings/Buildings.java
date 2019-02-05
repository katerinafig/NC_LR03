package buildings;

import buildings.factory.DwellingFactory;
import buildings.interfaces.Building;
import buildings.interfaces.BuildingFactory;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.*;
import java.util.*;

public class Buildings {
    private static BuildingFactory buildingFactory = new DwellingFactory();

    //метод, позволяющий, устанавливать ссылку на текущую конкретную фабрику.
    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public static Space createSpace(int area) {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(int roomsCount, int area) {
        return buildingFactory.createSpace(roomsCount, area);
    }

    public static Floor createFloor(int spacesCount) {
        return buildingFactory.createFloor(spacesCount);
    }

    public static Floor createFloor(Space[] spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Building createBuilding(int floorsCount, int[] spacesCounts) {
        return buildingFactory.createBuilding(floorsCount, spacesCounts);
    }

    public static Building createBuilding(Floor[] floors) {
        return buildingFactory.createBuilding(floors);
    }

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
            floor = buildingFactory.createFloor(countSpaces);
            for (int j = 0; j < countSpaces; j++) {
                numberOfRooms = dataInputStream.readInt();
                area = dataInputStream.readInt();
                floor.addNewSpace(j, buildingFactory.createSpace(numberOfRooms, area));
            }
            arrayFloors[i] = floor;
        }
        return buildingFactory.createBuilding(arrayFloors);
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
            Space[] spaces = new Space[(int) streamTokenizer.nval];
            streamTokenizer.nextToken();
            for (int j = 0, sizeFlats = spaces.length; j < sizeFlats; j++) {
                rooms = (int) streamTokenizer.nval;
                streamTokenizer.nextToken();
                area = (int) streamTokenizer.nval;
                spaces[j] = buildingFactory.createSpace(rooms, area);
                streamTokenizer.nextToken();
            }
            floors[i] = buildingFactory.createFloor(spaces);
        }
        return buildingFactory.createBuilding(floors);
    }

    //Метод сериализации здания в байтовый поток
    public static void serializeBuilding(Building building, OutputStream out) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(building);
    }

    //Метод десериализации здания из байтового потока
    public static Building deserializeBuilding(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        return (Building) objectInputStream.readObject();
    }

    //Метод текстовой записи, использующий возможности форматированного вывода
    public static void writeBuildingFormat(Building building, Writer out) {
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
            floors[i] = buildingFactory.createFloor(sizeOfFloor);
            for (int j = 0; j < sizeOfFloor; j++) {
                int roomCount = scanner.nextInt();
                int area = scanner.nextInt();
                floors[i].setSpace(j, buildingFactory.createSpace(roomCount, area));

            }
        }
        return buildingFactory.createBuilding(floors);
    }

    public static <T extends Comparable<T>> void sortUp(T[] objects) {
        for (int i = objects.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (objects[j].compareTo(objects[j + 1]) > 0) {
                    T container = objects[j];
                    objects[j] = objects[j + 1];
                    objects[j + 1] = container;
                }
            }
        }
    }

    public static <T> void sortDown(T[] objects, Comparator<T> comparator) {
        for (int i = objects.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (comparator.compare(objects[j], objects[j + 1]) > 0) {
                    T container = objects[j];
                    objects[j] = objects[j + 1];
                    objects[j + 1] = container;
                }
            }
        }
    }
    public static Floor synchronizedFloor (Floor floor) {
        return new SynchronizedFloor(floor);
    }


}
