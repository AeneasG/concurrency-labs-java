package assignments.assignment1.exercise1;

public class PrimeFinder implements Runnable {

    private Integer from;
    private Integer to;

    public PrimeFinder(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        for(int i=from;i<to;i++){
            if(this.isPrime(i)){
                System.out.println(i + " is prime.");
            }
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
