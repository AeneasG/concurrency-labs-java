package assignments.assignment1.exercise2;

public class SynchronizedCounter {

    Integer max;
    Integer counter;

    public SynchronizedCounter(Integer max, Integer start) {
        this.max = max;
        this.counter = start;
    }

    public synchronized int getNext() {
        if(this.counter++ < this.max){
            return this.counter;
        }
        return 0;
    }
}
