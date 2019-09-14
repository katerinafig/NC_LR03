package buildings;

import buildings.factory.DwellingFactory;
import buildings.interfaces.Building;
import buildings.interfaces.BuildingFactory;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Buildings {
    private static BuildingFactory buildingFactory;

    //метод, позволяющий, устанавливать ссылку на текущую конкретную фабрику.
    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public static Space createSpace(int area, Class spaceClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor[] constructors = spaceClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] paramTypes = constructor.getParameterTypes();
            if(paramTypes.length==1&& paramTypes[0].getName().equals("int")) {
                return (Space) constructor.newInstance(area);
            }
        }
        return null;
    }

    public static Space createSpace(int roomsCount, int area, Class spaceClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor[] constructors = spaceClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] paramTypes = constructor.getParameterTypes();
            if(paramTypes.length==2&& paramTypes[0].getName().equals("int")&&paramTypes[1].getName().equals("int")) {
                return (Space) constructor.newInstance(area,roomsCount);
            }
        }
        return null;
    }

    public static Floor createFloor(int spacesCount, Class floorClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor[] constructors = floorClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] paramTypes = constructor.getParameterTypes();
            if(paramTypes.length==1&& paramTypes[0].getName().equals("int")) {
                return (Floor) constructor.newInstance(spacesCount);
            }
        }
        return null;
    }

    public static Floor createFloor(Space[] spaces, Class floorClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor[] constructors = floorClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] paramTypes = constructor.getParameterTypes();
            if(paramTypes.length==1&& paramTypes[0].getName().equals("[Lbuildings.interfaces.Space;")) {
                return (Floor) constructor.newInstance((Object) spaces);
            }
        }
        return null;
    }

    public static Building createBuilding(int floorsCount, int[] spacesCounts, Class buildingClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor[] constructors = buildingClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] paramTypes = constructor.getParameterTypes();
            if(paramTypes.length==2&&paramTypes[0].getName().equals("int")&& paramTypes[1].getName().equals("[I")) {
                return (Building) constructor.newInstance(floorsCount,spacesCounts);
            }
        }
        return null;
    }

    public static Building createBuilding(Floor[] floors, Class buildingClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor[] constructors = buildingClass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] paramTypes = constructor.getParameterTypes();
            if(paramTypes.length==1&& paramTypes[0].getName().equals("[Lbuildings.interfaces.Floor;")) {
                return (Building) constructor.newInstance((Object)floors);
            }
        }
        return null;
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
    public static Building inputBuilding(InputStream in)
            throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
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
                Objects.requireNonNull(floor).addNewSpace(j, buildingFactory.createSpace(numberOfRooms,area));
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
    public static Building readBuilding(Reader in, Class buildingClass, Class floorClass, Class spaceClass)
            throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
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
                spaces[j] = createSpace(rooms, area, spaceClass);
                streamTokenizer.nextToken();
            }
            floors[i] = createFloor(spaces,floorClass);
        }
        return createBuilding(floors,buildingClass);
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
    public static Building readBuilding(Scanner scanner, Class buildingClass, Class floorClass, Class spaceClass)
            throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
        int sizeOfBuilding = scanner.nextInt();
        Floor[] floors = new Floor[sizeOfBuilding];
        for (int i = 0; i < sizeOfBuilding; i++) {
            int sizeOfFloor = scanner.nextInt();
            floors[i] = createFloor(sizeOfFloor,floorClass);
            for (int j = 0; j < sizeOfFloor; j++) {
                int roomCount = scanner.nextInt();
                int area = scanner.nextInt();
                Objects.requireNonNull(floors[i]).setSpace(j, createSpace(roomCount, area,spaceClass));

            }
        }
        return createBuilding(floors,buildingClass);
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
