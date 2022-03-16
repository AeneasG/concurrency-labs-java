package assignments.assignment1.exercise1;

import java.util.Date;

public class PrimeFinderMain {

    /**
     * start the Primefinder with
     * java PrimeFinderMain T N
     * where T are the number of threads and N is the number until where primes should be searched
     */
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

        Thread[] threads = new Thread[T];

        int[] splits = new int[T+1];
        splits[0] = 1;
        splits[T] = N+1; // N must be tested as well for prime
        int stepSize = Math.floorDiv(N, T);
        for(int i=1;i<T;i++){
            splits[i] = i*stepSize;
        }

        Date dateBefore = new Date();

        for(int i=0;i<T;i++){
            threads[i] = new Thread(new PrimeFinder(splits[i], splits[i+1]));
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
