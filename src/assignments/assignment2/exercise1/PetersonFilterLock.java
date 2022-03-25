package assignment2.exercise1;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class PetersonFilterLock {
    AtomicIntegerArray level;
    AtomicIntegerArray victim;

    public PetersonFilterLock(int nbOfThreads){
        this.level = new AtomicIntegerArray(nbOfThreads);
        this.victim = new AtomicIntegerArray(nbOfThreads);
        for(int i = 0; i < nbOfThreads; i++){
            this.level.set(i, 0);
        }
    }

    public void lock(int id){
        int nbOfThreads = this.level.length();
        for(int L = 1; L < nbOfThreads; L++){
            this.level.set(id, L);
            this.victim.set(L, id);
            while(otherProcessesWaiting(id, L) && this.victim.get(L) == id){}
        }
    }

    private boolean otherProcessesWaiting(int id, int level) {
        for(int i = 0; i < this.level.length(); i++){
            if(id != i && this.level.get(i) >= level) {
                return true;
            }
        }
        return false;
    }

    public void unlock(int id){
        this.level.set(id, 0);
    }
}
