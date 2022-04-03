package assignment3.exercise1.basic;

/**
 * Cook is a thread that prepares upon invocation the required number of portions and puts them into the pot
 */
public class Cook implements Runnable {

    // the pot to refill
    private Pot pot;

    private int threadId;
    // how many portions to refill
    private int nbPortions;

    public Cook(int threadId, int nbPortions, Pot pot) {
        this.threadId = threadId;
        this.nbPortions = nbPortions;
        this.pot = pot;
    }

    @Override
    public void run() {
        doRefill();
    }

    public void doRefill(){
        try {
            // assert that the pot refill lock is set
            while(!this.pot.isRefilling()){}
            System.out.println("Refill the pot");
            // perform the refill
            this.pot.refill(this.nbPortions);
        } finally {
            // release the lock
            this.pot.finishRefill();
        }
    }
}
