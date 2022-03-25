package assignment2.exercise1;

import java.util.Date;

public class CounterMain {

    static long counter;
    static PetersonFilterLock lock;

    static class CounterThread implements Runnable {
        int id;
        long n;

        public CounterThread(int id, long n) {
            this.id = id;
            this.n = n;
        }

        @Override
        public void run() {
            if (id % 2 == 0) {
                for (long l = 0; l < n; l++) {
                    lock.lock(this.id);
                    counter++;
                    lock.unlock(this.id);
                }
            } else {
                for (long l = 0; l < n; l++) {
                    lock.lock(this.id);
                    counter--;
                    lock.unlock(this.id);
                }
            }
        }
    }

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

        Date dateBefore = new Date();

        lock = new PetersonFilterLock(T);

        for(int i=0;i<T;i++){
            threads[i] = new Thread(new CounterThread(i, N));
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

        System.out.println("Needed " + difference + " ms to count around and got the following result: " + counter);
    }

}
