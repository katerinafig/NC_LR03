package buildings;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.util.Iterator;

public class SynchronizedFloor implements Floor {
    protected Floor floor;

    public SynchronizedFloor(Floor floor) {
        this.floor = floor;
    }

    @Override
    public synchronized int getSize() {
        return floor.getSize();
    }

    @Override
    public synchronized int getAreaSpace() {
        return floor.getAreaSpace();
    }

    @Override
    public synchronized int getCountRoomsOnSpace() {
        return floor.getCountRoomsOnSpace();
    }

    @Override
    public synchronized Space[] getArraySpace() {
        return floor.getArraySpace();
    }

    @Override
    public synchronized Space getSpace(int i) {
        return floor.getSpace(i);
    }

    @Override
    public synchronized void setSpace(int number, Space newSpace) {
        floor.setSpace(number, newSpace);
    }

    @Override
    public synchronized void addNewSpace(int number, Space newFlat) {
        floor.addNewSpace(number, newFlat);
    }

    @Override
    public synchronized void removeSpace(int number) {
        floor.removeSpace(number);
    }

    @Override
    public synchronized Space getBestSpace() {
        return floor.getBestSpace();
    }

    @Override
    public synchronized void checkBounds(int number) {
        floor.checkBounds(number);
    }

    @Override
    public synchronized Object clone() throws CloneNotSupportedException {
        return floor.clone();
    }

    @Override
    public synchronized Iterator<Space> iterator() {
        return floor.iterator();
    }

    @Override
    public synchronized int compareTo(Floor o) {
        return floor.compareTo(o);
    }

    @Override
    public synchronized int hashCode() {
        return floor.hashCode();
    }

    @Override
    public synchronized boolean equals(Object obj) {
        return floor.equals(obj);
    }

    @Override
    public synchronized String toString() {
        return floor.toString();
    }
}
