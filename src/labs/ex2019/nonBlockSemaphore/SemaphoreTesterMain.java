package ex2019.nonBlockSemaphore;

import java.util.Date;

public class SemaphoreTesterMain {

    private static class Tester implements Runnable {

        int iterations;
        int id;
        NonBlockSemaphore semaphore;

        public Tester(int iterations, int id, NonBlockSemaphore semaphore) {
            this.iterations = iterations;
            this.id = id;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            for(int i = 0; i<this.iterations;i++) {
                this.semaphore.acquire(this.id);
                System.out.println("Thread has semaphore with id " + this.id);
                this.semaphore.release(this.id);
            }
        }
    }

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


        NonBlockSemaphore semaphore = new NonBlockSemaphore();
        for(int i=0;i<T;i++){
            threads[i] = new Thread(new Tester(N, i, semaphore));
        }

        Date dateBefore = new Date();

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

        System.out.println("Needed " + difference + " ms to execute with nb of iterations: " + N);
    }

}
