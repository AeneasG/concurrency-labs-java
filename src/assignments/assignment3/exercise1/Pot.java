package assignment3.exercise1;

/**
 * shared resource Pot
 * Either one cook can add new items or many savages can eat at the same time
 */
public class Pot {

    // counter of how many portions are currently in the pot
    private volatile int nbOfPortions;
    // boolean to indicate whether the pot is locked to perform a refill
    private volatile boolean refillLock;

    public Pot() {
        this.nbOfPortions = 0;
        this.refillLock = false;
    }

    /**
     * trys to aquire the refillLock
     * @return  boolean, true if you can obtain the lock (i.e. the pot is empty and nobody else has the lock), false otherwise
     */
    public synchronized boolean getRefillLock() {
        if(this.nbOfPortions > 0 || this.refillLock) {
            return false;
        }
        return this.lockHelper(true);
    }

    /**
     * refills the pot with the given number of portions
     * @param nbPortions    int, the number of portions that are in the pot
     */
    public synchronized void refill(int nbPortions) {
        this.nbOfPortions = nbPortions;
        this.lockHelper(false);
    }

    /**
     * accessor method to check whether a refill is ongoing
     * @return  boolean, true if a refill is going on, false otherwise
     */
    public boolean isRefilling() {
        return this.refillLock;
    }

    /**
     * method for a savage to eat a portion
     * @return  boolean, true if the savage got a portion, false otherwise
     */
    public synchronized boolean getMeal() {
        if(this.nbOfPortions > 0){
            this.nbOfPortions--;
            return true;
        }
        return false;
    }

    /**
     * synchronized method to handle the locking of the refillLock
     * @param lock      true, if you like to lock, false otherwise
     * @return          boolean, true if the lock could be obtained (or released), false otherwise
     */
    private synchronized boolean lockHelper(boolean lock) {
        if(lock && this.refillLock) {
            return false;
        } else if(lock && !this.refillLock){
            this.refillLock = true;
        } else {
            this.refillLock = false;
        }
        return true;
    }
}
