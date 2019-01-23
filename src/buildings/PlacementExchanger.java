package buildings;
import buildings.exception.InexchangeableFloorsException;
import buildings.exception.InexchangeableSpacesException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class PlacementExchanger {

    //Метод проверки возможности обмена помещениями.
    public static boolean isSpaceExchange (Space spaceFirst, Space spaceSecond){
        if(spaceFirst.getArea()==spaceSecond.getArea()&&spaceFirst.getNumberOfRooms()==spaceSecond.getNumberOfRooms()) {
            return true;
        }
        else return false;
    }

    //Метод проверки возможности обмена этажами.
    public static boolean isFloorExchange (Floor floorFirst, Floor floorSecond){
        if (floorFirst.getSize()!=floorSecond.getSize()) return false;
        if(floorFirst.getCountRoomsOnSpace()==floorSecond.getCountRoomsOnSpace()&&floorFirst.getAreaSpace()==floorSecond.getAreaSpace()){
            return true;
        }
        else return false;
    }

    //Метод обмена помещениями двух этажей
    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2,int index2){
        floor1.checkBounds(index1);
        floor2.checkBounds(index2);
        Space space1 = floor1.getSpace(index1);
        Space space2 = floor2.getSpace(index2);
        if(!isSpaceExchange(space1,space2))throw new InexchangeableSpacesException();
        Space temporarySpace = space1;
        space1 = space2;
        space2 = temporarySpace;
    }

    //Метод обмена этажами двух зданий
    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2){
        building1.checkBounds(index1);
        building2.checkBounds(index2);
        Floor floor1  = building1.getFloor(index1);
        Floor floor2 = building2.getFloor(index2);
        if(!isFloorExchange(floor1,floor2))throw new InexchangeableFloorsException();
        Floor temporarySpace = floor1;
        floor1 = floor2;
        floor2 = temporarySpace;
    }

}
