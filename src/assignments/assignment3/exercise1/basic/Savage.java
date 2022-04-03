package assignment3.exercise1.basic;

/**
 * Savage tries to eat one portion of the pot
 * if the pot is empty, he tries to inform the cook and orders the pot to be refilled
 */
public class Savage implements Runnable{

    private int threadId;
    private Pot pot;
    private Cook cook;
    private boolean ate;

    public Savage(int threadId, Pot pot, Cook cook) {
        this.threadId = threadId;
        this.pot = pot;
        this.cook = cook;
        this.ate = false;
    }

    @Override
    public void run() {
        // as long as no portion could be obtained, do
        while(!this.ate) {
            // wait while the pot is being refilled
            while(this.pot.isRefilling()){}

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
                // got a meal, done
                this.ate = true;
            }
        }
    }
}
