package assignments.assignment1.exercise2;

import java.util.Date;

public class PrimeFinderMain {

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

        SynchronizedCounter syncCounter = new SynchronizedCounter(N, 1);
        Thread[] threads = new Thread[T];

        Date dateBefore = new Date();

        for(int i=0;i<T;i++){
            threads[i] = new Thread(new PrimeFinder(syncCounter));
        }

        for(int i=0;i<T;i++){
            threads[i].start();
        }

        for(int i=0;i<T;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){}
        }

        Date dateAfter = new Date();
        long difference = dateAfter.getTime() - dateBefore.getTime();

        double diffSeconds = difference / 1000;

        System.out.println("Needed " + diffSeconds + " seconds to find all primes up to " + N);
    }

}
