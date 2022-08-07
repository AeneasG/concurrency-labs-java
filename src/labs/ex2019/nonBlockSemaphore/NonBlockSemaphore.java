package ex2019.nonBlockSemaphore;

import java.util.concurrent.atomic.AtomicInteger;

public class NonBlockSemaphore {
    private AtomicInteger cnt;

    public NonBlockSemaphore() {
        this.cnt = new AtomicInteger(1);
    }

    public void acquire(int id) {
        while(true) {
            int curr = this.cnt.get();
            if(curr > 0 && this.cnt.compareAndSet(curr, curr - 1)) {
                break;
            }
        }
        System.out.println("Get sempahore" + id);
    }

    public void release(int id) {
        System.out.println("Release sempahore" + id);
        this.cnt.incrementAndGet();
    }
}
