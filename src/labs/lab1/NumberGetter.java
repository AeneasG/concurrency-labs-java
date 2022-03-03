package labs.lab1;

import java.util.Date;

public class NumberGetter {
    int x, y;
    int currentNumber;
    int currentHighestNumber;
    int nbOfDivisorsForHighestNumber;

    public NumberGetter(Integer x, Integer y){
        this.x = x;
        this.y = y;
        this.currentNumber = x;
    }

    public void find(int nbOfThreads) {
        Thread[] threads = new Thread[nbOfThreads];

        for(int i=0;i<nbOfThreads;i++){
            threads[i] = new Thread(new IntegerFinderThread(this));
        }

        Date dateBefore = new Date();

        for(int i=0;i<nbOfThreads;i++){
            threads[i].start();
        }

        for(int i=0;i<nbOfThreads;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){}
        }

        Date dateAfter = new Date();
        long difference = dateAfter.getTime() - dateBefore.getTime();

        double diffSeconds = difference / 60;

        System.out.println("Needed " + diffSeconds + " seconds to find that between " + this.x + " and " + this.y + ", " + this.currentHighestNumber + " has the largest number of divisors with a total of " + this.nbOfDivisorsForHighestNumber);
    }

    public synchronized Integer getNextNumber(){
        if(this.currentNumber <= this.y){
            return this.currentNumber++;
        }
        return null;
    }

    public synchronized void setHighestNumber(int numberOfDivisors, int number){
        if(numberOfDivisors >= this.nbOfDivisorsForHighestNumber){
            this.nbOfDivisorsForHighestNumber = numberOfDivisors;
            this.currentHighestNumber = number;
        }
    }

    public static void main(String args[]){
        NumberGetter numberGetter = new NumberGetter(1, 100000);
        numberGetter.find(20);
    }
}
