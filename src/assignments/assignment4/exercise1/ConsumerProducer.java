package assignment4.exercise1;

import java.util.List;

public class ConsumerProducer implements Runnable {

    private final int nbOfIterations;
    private final boolean isProducer;
    private final List<Integer> list;

    public ConsumerProducer(boolean isProducer, int nbOfIterations, List<Integer> list) {
        this.isProducer = isProducer;
        this.nbOfIterations = nbOfIterations;
        this.list = list;
    }

    @Override
    public void run() {
        if(this.isProducer){
            for(int i=0;i<this.nbOfIterations;i++){
                this.list.add(i);
            }
        } else {
            int nbOfFailures = 0;
            int failureThreshold = 2*this.nbOfIterations;
            for(int i=0;i<this.nbOfIterations;i++){
                try {
                    this.list.remove(0);
                } catch (Exception e) {
                    nbOfFailures++;
                    i--;
                    if(nbOfFailures > failureThreshold){
                        System.out.println("Give up after too many failures in iteration " + i + " and nbOfFailures: " + nbOfFailures);
                        break;
                    }
                }
            }
        }
    }
}
