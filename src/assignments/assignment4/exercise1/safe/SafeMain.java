package assignment4.exercise1.safe;

import assignment4.exercise1.ConsumerProducer;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

        System.out.println("Needed " + difference + " ms to " + N*T + " add and consume concurrently from list. The list should be empty; list.isEmpty? " + list.isEmpty());
    }
}
