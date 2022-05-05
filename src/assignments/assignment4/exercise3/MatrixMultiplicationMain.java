package assignment4.exercise3;


import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MatrixMultiplicationMain {
    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args){
        if(args.length < 1) {
            throw new RuntimeException("Please provide at least 1 integer arguments");
        }
        int N;
        try {
            N = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            throw new RuntimeException("Please provide an Integer argument");
        }

        if(N < 1){
            throw new RuntimeException("Invalid Arguments");
        }

        // Matrix Initialization
        Matrix a = new Matrix(N, N);
        a.fillMatrixRandom();
        Matrix b = new Matrix(N, N);
        b.fillMatrixRandom();
        Matrix c = new Matrix(N, N);

        Date dateBefore = new Date();

        Future<Long>[][] results = new Future[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                results[i][j] = executorService.submit(new MatrixFieldMultiplicationTask(a.matrixFields, b.matrixFields, i, j));
            }
        }

        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                try {
                    c.matrixFields[i][j] = results[i][j].get();
                } catch (Exception e){
                    System.out.println("Exception in calculation of value " + i + ", " + j + " -> " + e.getMessage());
                }
            }
        }

        Date dateAfter = new Date();
        long difference = dateAfter.getTime() - dateBefore.getTime();

        System.out.println("Matrix A: ");
        System.out.println(a.toString());
        System.out.println("Matrix B: ");
        System.out.println(b.toString());
        System.out.println("Matrix C = A * B: ");
        System.out.println(c.toString());

        System.out.println("Needed " + difference + " ms to multiply to " + N + " * " + N + " matrices.");

        // stop the executorService and shut down all the pooled threads
        executorService.shutdownNow();
    }
}
