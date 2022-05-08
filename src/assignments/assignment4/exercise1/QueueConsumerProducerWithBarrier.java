package assignment4.exercise1;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class QueueConsumerProducerWithBarrier implements Runnable {

    private final int nbOfIterations;
    private final boolean isProducer;
    private final Queue<Integer> list;
    private final CyclicBarrier cyclicBarrier;
    private final CountDownLatch countDownLatch;


    public QueueConsumerProducerWithBarrier(boolean isProducer, int nbOfIterations, Queue<Integer> list, CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch) {
        this.nbOfIterations = nbOfIterations;
        this.isProducer = isProducer;
        this.list = list;
        this.cyclicBarrier = cyclicBarrier;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        if (this.isProducer) {
            try {
                // wait for all producers to come here in order to begin producing
                cyclicBarrier.await();
                for (int i = 0; i < this.nbOfIterations; i++) {
                    this.list.add(i);
                }
            } catch (Exception e) {
            } finally {
                // this producer finished the job; countDown the countDownLatch such that after the last producer the consumers can begin
                countDownLatch.countDown();
            }
        } else {
            try {
                // wait until all producers have finished producing their values
                countDownLatch.await();
                int nbOfFailures = 0;
                int failureThreshold = 2 * this.nbOfIterations;
                for (int i = 0; i < this.nbOfIterations; i++) {
                    try {
                        this.list.remove();
                    } catch (Exception e) {
                        nbOfFailures++;
                        i--;
                        if (nbOfFailures > failureThreshold) {
                            System.out.println("Give up after too many failures after " + i + " reads and " + nbOfFailures + " failures");
                            break;
                        }
                    }
                }
            } catch (Exception e){}
        }
    }
}
