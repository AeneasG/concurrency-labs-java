package assignment4.exercise2;

import java.util.concurrent.CyclicBarrier;

/**
 * This program tests two types of consensus, the LockConsensus and the TasConsensus
 * To enhance concurrency and test the two consensus implementations a cyclicBarrier is used
 * in order to enforce that all processes invoke the "decide" method of the Consensus impelmentation
 * at the same time
 *
 * It takes one parameter:
 * - T: Integer, number of processes proposing a value
 */
public class ConsensusTesterMain {

    public static void main(String[] args){
        if(args.length < 1) {
            throw new RuntimeException("Please provide at least 1 integer arguments");
        }
        int T;
        try {
            T = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            throw new RuntimeException("Please provide an Integer argument");
        }

        if(T < 1){
            throw new RuntimeException("Invalid Arguments");
        }

        Thread[] threads = new Thread[T];

        System.out.println("Test the LockConsensus");

        CyclicBarrier cyclicBarrier = new CyclicBarrier(T);
        IConsensus consensus = new LockConsensus();

        for(int i=0;i<T;i++){
            threads[i] = new Thread(new ConsensusTesterRunnable(consensus, i, cyclicBarrier));
        }

        for(int i=0;i<T;i++){
            threads[i].start();
        }

        for(int i=0;i<T;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){}
        }


        System.out.println("Now test the TasConsensus");

        cyclicBarrier = new CyclicBarrier(T);
        consensus = new TasConsensus();

        for(int i=0;i<T;i++){
            threads[i] = new Thread(new ConsensusTesterRunnable(consensus, i, cyclicBarrier));
        }

        for(int i=0;i<T;i++){
            threads[i].start();
        }

        for(int i=0;i<T;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){}
        }
    }
}
