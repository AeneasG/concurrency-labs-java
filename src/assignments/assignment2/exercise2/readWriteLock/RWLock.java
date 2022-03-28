package assignment2.exercise2.readWriteLock;

/**
 * Read Write lock implementation
 */
public class RWLock {

    private int nbOfReaders = 0;
    private boolean writerLock = false;

    public RWLock() {}

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
                this.writerLock = true;
                // while there are readers, we have to release the lock
                if(this.nbOfReaders > 0){
                    this.writerLock = false;
                    return false;
                } else {
                    return true;
                }
            } else {
                // writer releases the lock
                this.writerLock = false;
            }
        } else {
            // reader logic for aquiring and releasing the lock
            if(lock){
                // the writer holds the lock -> cannot enter
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
