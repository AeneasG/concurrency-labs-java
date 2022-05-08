package assignment4.exercise1.barriersAndLatches;

import assignment4.exercise1.QueueConsumerProducerWithBarrier;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * This program tests the concurrent access to a concurrent linked queue from the java.util.concurrent package
 * It uses a CyclicBarrier to let all producers start at the same time and a CountDownLatch to let consumers
 * start consume only after all producers finished producing.
 * It starts T producer and T consumer threads
 *
 * The program takes two parameters
 * - T: Integer, number of producers / consumers
 * - N: Number of writes (for a producer) / reads (for a consumer)
 */
public class BarrierAndLatchesMain {

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

        // any producer must wait for T threads to join (including himself) before any producer can begin to produce
        CyclicBarrier cyclicBarrier = new CyclicBarrier(T);

        // any consumer must wait for T producers to finish their job before any can begin to consume
        CountDownLatch countDownLatch = new CountDownLatch(T);

        Date dateBefore = new Date();

        for(int i=0;i<T;i++){
            threads[2*i] = new Thread(new QueueConsumerProducerWithBarrier(true, N, queue, cyclicBarrier, countDownLatch));
            threads[2*i+1] = new Thread(new QueueConsumerProducerWithBarrier(false, N, queue, cyclicBarrier, countDownLatch));
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
