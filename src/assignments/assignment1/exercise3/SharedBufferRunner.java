package assignments.assignment1.exercise3;

/**
 * Runner Class to setup the required producer and consumers
 */
public class SharedBufferRunner {
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

        if(T < 1 || N < 1){
            throw new RuntimeException("Invalid Arguments!");
        }

        SharedBuffer<Integer> sharedBuffer = new SharedBuffer<Integer>(Integer.class, N);
        Thread[] threads = new Thread[2*T];

        for(int i=0;i<T;i++){
            threads[i*2] = new Thread(new Producer(10000, sharedBuffer));
            threads[i*2+1] = new Thread(new Consumer( 10000, sharedBuffer));
        }

        for(int i=0;i<2*T;i++){
            threads[i].start();
        }

        for(int i=0;i<2*T;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){}
        }
    }
}
