package assignment3.exercise2;

/**
 * Fork Simulation
 * A fork can be grabbed by almost one thread at any time
 * if the threadId equals to -1, the fork is free and can be grabbed
 */
public class Fork {

    volatile int threadId;

    public Fork() {
        this.threadId = -1;
    }

    public synchronized boolean grab(int threadId) {
        if(this.threadId != -1) {
            return false;
        }
        this.threadId = threadId;
        return true;
    }

    public synchronized void release() {
        this.threadId = -1;
    }
}
