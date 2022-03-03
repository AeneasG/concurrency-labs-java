package labs.lab1;

public class InvokerThread implements Runnable {

    private int nbInvocations;
    private Counter counter;

    public InvokerThread(int nbInvocations, Counter counter){
        this.nbInvocations = nbInvocations;
        this.counter = counter;
    }

    public void run(){
        for(int i = 0; i<nbInvocations; i++){
            counter.increment();
        }
    }

    public static void main(String args[]){
        int nbOfThreads = 10;
        int nbOfInvocations = 100;
        Thread[] threads = new Thread[nbOfThreads];
        Counter counter = new Counter();

        for(int i=0;i<nbOfThreads;i++){
            threads[i] = new Thread(new InvokerThread(nbOfInvocations, counter));
        }

        for(int i=0;i<nbOfThreads;i++){
            threads[i].start();
        }

        for(int i=0;i<nbOfThreads;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){}
        }

        System.out.println("Counter of thread should be " + nbOfThreads * nbOfInvocations + " and the real count is " + counter.getCount());
    }


}
