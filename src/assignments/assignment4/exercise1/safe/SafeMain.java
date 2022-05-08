package assignment4.exercise1.safe;

import assignment4.exercise1.ConsumerProducer;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * This program tests the concurrent access to a linked list which is synchronized using the Collections synchronization
 * presented by https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
 * It starts T producer and T consumer threads
 *
 * The program takes two parameters
 * - T: Integer, number of producers / consumers
 * - N: Number of writes (for a producer) / reads (for a consumer)
 */
public class SafeMain {

    public static void main(String[] args){
        if(args.length < 2) {
            throw new RuntimeException("Please provide at least 2 integer arguments");
        }
        int T;
        int N;
        try {
            T = Integer.parseInt(args[0]);
            N = Integer.parseInt(args[1]);
        } catch (NumberFormatException e){
            throw new RuntimeException("Please provide Integer arguments");
        }

        if(T < 1 || N < 2){
            throw new RuntimeException("Invalid Arguments!");
        }

        Thread[] threads = new Thread[2*T];

        // synchronize a linked list according to
        // https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
        List<Integer> list = Collections.synchronizedList(new LinkedList<Integer>());

        Date dateBefore = new Date();

        for(int i=0;i<T;i++){
            threads[2*i] = new Thread(new ConsumerProducer(true, N, list));
            threads[2*i+1] = new Thread(new ConsumerProducer(false, N, list));
        }

        for(int i=0;i<2*T;i++){
            threads[i].start();
        }

        for(int i=0;i<2*T;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){}
        }

        Date dateAfter = new Date();
        long difference = dateAfter.getTime() - dateBefore.getTime();

        System.out.println("Needed " + difference + " ms to " + N*T + " add and consume concurrently from list. The list should now be empty -> isEmtpy? " + list.isEmpty());
    }
}
