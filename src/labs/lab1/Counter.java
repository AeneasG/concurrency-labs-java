package labs.lab1;

public class Counter {
    private int count;
    private Object lock;

    public Counter() {
        this.count = 0;
        this.lock = new Object();
    }

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public void synchronizedIncrement() {
        synchronized (this.lock){
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}