package assignment1.exercise2;

/**
 * synchronized counter that will return increase the counter beginning with given "start" Integer
 * every time the "getNext" method is invoked until the given max is reached
 */
public class SynchronizedCounter {

    private int max;
    private int counter;

    public SynchronizedCounter(int max, int start) {
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
