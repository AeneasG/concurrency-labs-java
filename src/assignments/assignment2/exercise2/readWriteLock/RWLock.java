package assignment2.exercise2.readWriteLock;

public class RWLock {

    int nbOfReaders = 0;
    boolean writerLock = false;

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

    private synchronized boolean lockHelper(boolean isWriter, boolean lock){
        if(isWriter){
            if(lock){
                this.writerLock = true;
                if(this.nbOfReaders > 0){
                    this.writerLock = false;
                    return false;
                } else {
                    return true;
                }
            } else {
              this.writerLock = false;
            }
        } else {
            if(lock){
                if(this.writerLock){
                    return false;
                }
                this.nbOfReaders++;
                return true;
            } else {
                this.nbOfReaders--;
            }
        }
        return true;
    }
}
