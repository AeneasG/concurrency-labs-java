package assignment1.exercise3;

import java.lang.reflect.Array;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared Buffer of size bufferSize and type clazz
 * @param <T>       Type of the Buffer
 */
public class SharedBuffer<T> {

    private final ReentrantLock lock;
    private T[] buffer;
    private int head;
    private int tail;
    private int currentSize;

    public SharedBuffer(Class<T> clazz, int bufferSize) {
        this.buffer = (T[]) Array.newInstance(clazz, bufferSize);
        // an unfair basic lock may lead to starving consumer processes and
        // failing producer processes as the buffer may be full all the times
        // therefore we need a reentrantLock that is fair
        this.lock = new ReentrantLock(true);
        this.head = 0;
        this.tail = 0;
        this.currentSize = 0;
    }

    public void produce(T item) throws BufferOverflowException {
        try {
            this.lock.lock();
            if(this.currentSize < this.buffer.length) {
                this.buffer[tail] = item;
                this.tail = (this.tail + 1) % this.buffer.length;
                this.currentSize++;
            } else {
                throw new BufferOverflowException();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public T consume() throws BufferUnderflowException {
        try {
            this.lock.lock();
            if(this.currentSize > 0){
                T item = this.buffer[this.head];
                this.head = (this.head + 1) % this.buffer.length;
                this.currentSize--;
                return item;
            } else {
                throw new BufferUnderflowException();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public int getCurrentSize() {
        return this.currentSize;
    }
}
