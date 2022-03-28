package assignment2.exercise2.starvationFreeReadWriteLock;

/**
 * Read Write lock implementation
 * This implementation is writer starvation free, this means once the writer indicates that he likes to obtain the lock
 * no reader can obtain the lock before the writer
 */
public class RWStarvationFreeLock {

    private int nbOfReaders = 0;
    private boolean writerLock = false;
    private boolean writerIntention = false;

    public RWStarvationFreeLock() {}

    public void lockRead(){
        while(!this.lockHelper(false, true)){}
    }

    public void lockWrite(){
        while(!this.lockHelper(true, true)){}
    }

    public void unlockRead() {
        this.lockHelper(false, false);
    }

    public void unlockWrite() {
        this.lockHelper(true, false);
    }

    /**
     * Method to lock and unlock the RWLock for both, reader and writer
     * this method is synchronized to protect the internal variables from inconsistent state
     * @param isWriter          true, if this process is the writer, false otherwise
     * @param lock              true, if this process likes to obtain the lock, false otherwise
     * @return                  boolean, indication whether this process obtained the lock or not
     */
    private synchronized boolean lockHelper(boolean isWriter, boolean lock){
        // writer logic for aquiring and releasing the lock
        if(isWriter){
            if(lock){
                // signal the intention to obtain the lock
                this.writerIntention = true;
                this.writerLock = true;
                // while there are readers, we have to release the lock
                if(this.nbOfReaders > 0){
                    this.writerLock = false;
                    return false;
                } else {
                    // lock obtained, can reset intention as it now has the lock
                    this.writerIntention = false;
                    return true;
                }
            } else {
                // writer releases the lock
                this.writerLock = false;
            }
        } else {
            // reader logic for aquiring and releasing the lock
            if(lock){
                // the writer does not have the lock but he likes to obtain it
                // --> cannot enter -> return false
                if(this.writerIntention){
                    return false;
                }
                // the writer has the lock
                if(this.writerLock){
                    return false;
                }
                // we can enter, increase the number of readers
                this.nbOfReaders++;
                return true;
            } else {
                // unlock --> decrease the number of readers
                this.nbOfReaders--;
            }
        }
        return true;
    }
}
