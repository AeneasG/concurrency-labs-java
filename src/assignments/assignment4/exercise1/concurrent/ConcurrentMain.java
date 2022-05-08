package assignment4.exercise1.concurrent;

import assignment4.exercise1.QueueConsumerProducer;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This program tests the concurrent access to a concurrent linked queue from the java.util.concurrent package
 * It starts T producer and T consumer threads
 *
 * The program takes two parameters
 * - T: Integer, number of producers / consumers
 * - N: Number of writes (for a producer) / reads (for a consumer)
 */
public class ConcurrentMain {

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


        Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

        Date dateBefore = new Date();

        for(int i=0;i<T;i++){
            threads[2*i] = new Thread(new QueueConsumerProducer(true, N, queue));
            threads[2*i+1] = new Thread(new QueueConsumerProducer(false, N, queue));
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

        System.out.println("Needed " + difference + " ms to " + N*T + " add and consume concurrently from list. The list should now be empty -> isEmtpy? " + queue.isEmpty());
    }
}
