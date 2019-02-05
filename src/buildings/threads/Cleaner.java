package buildings.threads;

import buildings.interfaces.Floor;

public class Cleaner extends Thread {
    Floor floor;

    public Cleaner(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        for(int i=0;i<floor.getSize();i++){
            System.out.println("Cleaning room number"
                    +(i+1)
                    +" with total area "
                    +floor.getSpace(i).getArea()
                    +" square meters.");
        }
    }
}
