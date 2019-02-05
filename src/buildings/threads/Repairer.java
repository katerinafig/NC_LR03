package buildings.threads;

import buildings.interfaces.Floor;

public class Repairer extends Thread{
    Floor floor;

    public Repairer(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        for(int i=0;i<floor.getSize();i++){
            System.out.println("Repairing space number"
                    +(i+1)
                    +" with total area "
                    +floor.getSpace(i).getArea()
                    +" square meters.");
        }
    }
}
