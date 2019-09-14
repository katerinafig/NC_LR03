package buildings.threads;

public class Semaphore {
    volatile private  boolean flag;
    public void lock(Boolean currentFlag)
    {
        while (flag!=currentFlag)Thread.yield();
    }
    public  void unlock()
    {
        flag=!flag;
    }
}
