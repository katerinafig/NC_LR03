package buildings.threads;

public class Semaphore {
    private  boolean flag;

    public Semaphore(boolean flag) {
        this.flag = flag;
    }
    public void lock(Boolean currentFlag)
    {
        while (flag!=currentFlag)Thread.yield();
    }
    public  void unlock()
    {
        flag=!flag;
    }
}
