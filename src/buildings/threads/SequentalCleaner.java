package buildings.threads;

import buildings.interfaces.Floor;

public class SequentalCleaner implements Runnable{
    private Floor floor;
    private Semaphore semaphore;
    public SequentalCleaner(Floor floor, Semaphore semaphore) {
        this.floor = floor;
        this.semaphore=semaphore;
    }

    @Override
    public void run() {
        semaphore.lock(true);
        for(int i=0;i<floor.getSize();i++){
            System.out.println("Cleaner space number "
                    +(i+1)
                    +" with total area "
                    +floor.getSpace(i).getArea()
                    +" square meters.");
        }
        semaphore.unlock();
    }
}
