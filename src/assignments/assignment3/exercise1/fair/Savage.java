package assignment3.exercise1.fair;

import assignment3.exercise1.Cook;
import assignment3.exercise1.Pot;

import java.util.Date;

/**
 * Savage tries to eat one portion of the pot
 * if the pot is empty, he tries to inform the cook and orders the pot to be refilled
 */
public class Savage implements Runnable{

    private final int threadId;
    private final Pot pot;
    private final Cook cook;
    private final int maxConsumations;
    private final FifoQueue fifoQueue;

    private int nbOfConsumations;

    public Savage(int threadId, Pot pot, Cook cook, FifoQueue fifoQueue, int maxConsumations) {
        this.threadId = threadId;
        this.pot = pot;
        this.cook = cook;
        this.maxConsumations = maxConsumations; // this in infinite in theory but for simulation purposes we set a limit
        this.nbOfConsumations = 0;
        this.fifoQueue = fifoQueue;
    }

    @Override
    public void run() {
        Date dateBefore = new Date();
        // as long as no portion could be obtained, do
        while(this.nbOfConsumations < this.maxConsumations) {
            this.fifoQueue.enqueue(this.threadId);

            // wait while the pot is being refilled
            while(this.pot.isRefilling()){}

            while(this.fifoQueue.top() != this.threadId){}

            // try to get a meal
            if(!this.pot.getMeal()) {
                // if failed, try to get the refill lock
                // NOTE: no spinning required, because once the refillLock is obtained by someone else, the cook is already informed to refill the pot
                if(this.pot.getRefillLock()) {
                    System.out.println("Tell the cook to refill");
                    // start the cook thread
                    new Thread(cook).start();
                }
            } else {
                // got one more meal
                this.nbOfConsumations++;
            }
            this.fifoQueue.dequeue();
        }
        Date dateAfter = new Date();
        long difference = dateAfter.getTime() - dateBefore.getTime();

        System.out.println("Savage " + this.threadId + " needed " + difference + " ms to eat " + this.maxConsumations + " times");
    }
}
