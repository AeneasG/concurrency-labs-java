package assignments.assignment1.exercise2;

public class PrimeFinder implements Runnable {

    private SynchronizedCounter counter;

    public PrimeFinder(SynchronizedCounter counter){
        this.counter = counter;
    }

    @Override
    public void run() {
        int currentNumberToTest = this.counter.getNext();

        while(currentNumberToTest != 0){

            if(this.isPrime(currentNumberToTest)){
                System.out.println(currentNumberToTest + " is prime.");
            }

            currentNumberToTest = this.counter.getNext();
        }
    }

    private boolean isPrime(int number){
        int squareRoot = (int) Math.floor(Math.sqrt(number));

        boolean isPrime = true;
        for (int i=2;i<=squareRoot;i++){
            if(number % i == 0){
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }
}
