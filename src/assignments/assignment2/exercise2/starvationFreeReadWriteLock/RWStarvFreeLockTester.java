package assignment2.exercise2.starvationFreeReadWriteLock;


import java.util.Date;

public class RWStarvFreeLockTester {

    static int counter = 0;
    static RWStarvationFreeLock lock;
    static int[] nbOfReads;

    static class ProducerAndConsumer implements Runnable {
        int id;
        long n;

        public ProducerAndConsumer(int id, long n) {
            this.id = id;
            this.n = n;
        }

        @Override
        public void run() {
            if (id == 0) {
                for (long l = 0; l < n; l++) {
                    lock.lockWrite();
                    counter++;
                    lock.unlockWrite();
                }
            } else {
                for (long l = 0; l < n; l++) {
                    lock.lockRead();
                    nbOfReads[counter]++;
                    lock.unlockRead();
                }
            }
        }
    }

    public static void main(String args[]){
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

        lock = new RWStarvationFreeLock();
        nbOfReads = new int[N+1];

        for(int i=0;i<T;i++){
            threads[i] = new Thread(new ProducerAndConsumer(i, N));
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
        int cnt = 0;
        for(int i=0;i<N/4;i++){
            cnt += nbOfReads[i];
        }
        int total = (T-1)*N;
        System.out.println(100 * cnt / total + "% of reads occured in the first quarter.");
    }
}
