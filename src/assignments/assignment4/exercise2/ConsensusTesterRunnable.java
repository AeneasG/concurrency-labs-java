package assignment4.exercise2;

import java.util.concurrent.CyclicBarrier;

public class ConsensusTesterRunnable implements Runnable {
    IConsensus consensus;
    Integer threadId;
    CyclicBarrier barrier;

    public ConsensusTesterRunnable(IConsensus consensus, Integer threadId, CyclicBarrier barrier) {
        this.consensus = consensus;
        this.threadId = threadId;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            this.barrier.await();
            String decision = (String) this.consensus.decide(this.threadId.toString());
            System.out.println("Thread " + this.threadId + " decided on value " + decision);
        } catch (Exception e){
            System.out.println("Exception!: " + e.getMessage());
        }
    }
}
