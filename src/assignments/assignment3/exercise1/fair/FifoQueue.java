package assignment3.exercise1.fair;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Shared Buffer of size bufferSize and type clazz
 */
public class FifoQueue {

    private AtomicIntegerArray buffer;
    private volatile int head;
    private volatile int tail;

    public FifoQueue(int bufferSize) {
        this.buffer = new AtomicIntegerArray(bufferSize);
        this.head = 0;
        this.tail = 0;
    }

    public synchronized void enqueue(int id) {
        this.buffer.set(this.tail, id);
        this.tail = (this.tail + 1) % this.buffer.length();
    }

    public synchronized int dequeue() {
        int item = this.buffer.get(this.head);
        this.head = (this.head + 1) % this.buffer.length();
        return item;
    }

    public int top() {
        return this.buffer.get(this.head);
    }
}
