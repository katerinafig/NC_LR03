package buildings.interfaces;

public interface BuildingFactory {
    Space createSpace(int area);

    Space createSpace(int roomsCount, int area);

    Floor createFloor(int spacesCount);

    Floor createFloor(Space[] spaces);

    Building createBuilding(int floorsCount, int[] spacesCounts);

    Building createBuilding(Floor[] floors);
}
