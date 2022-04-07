package assignment3.exercise2;

import java.util.Date;

/**
 * Philosopher class that simulates the behavior of a Philosopher i.e. he tries to grab the two forks around him
 * and once he got two, he starts eating. Which fork is grabbed first, is decided upon the threadId
 */
public class Philosopher implements Runnable {
    private final Fork firstFork;
    private final Fork secondFork;
    private final int nbOfConsumptionsRequired;
    private int eatCounter;
    private final int threadId;

    public Philosopher(Fork leftFork, Fork rightFork, int nbOfConsumptionsRequired, int threadId) {
        boolean equalThreadNumber = threadId % 2 == 0;
        // philosophers with an equal thread number first grab the fork to their left
        // the other philosophers grab first the fork to their right
        if(equalThreadNumber) {
            this.firstFork = leftFork;
            this.secondFork = rightFork;
        } else {
            this.firstFork = rightFork;
            this.secondFork = leftFork;
        }
        // for simulation purposes we stop after nbOfConsumptionsRequired consumptions
        this.nbOfConsumptionsRequired = nbOfConsumptionsRequired;
        this.eatCounter = 0;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        Date dateBefore = new Date();

        while(this.eatCounter < this.nbOfConsumptionsRequired){
            while(!this.firstFork.grab(this.threadId)){}
            while(!this.secondFork.grab(this.threadId)){}

            // philosopher eating
            this.eatCounter++;

            // release the firstFork as first one such that a waiting philosopher waiting for the first fork can grab it
            // this is to enhance fairness; otherwise, if the firstFork would be released as last fork,
            // the philosopher may immediately grab it again and continue to eat before the thread waiting for the first fork ate
            this.firstFork.release();
            this.secondFork.release();

            // philosopher sleeping
        }

        Date dateAfter = new Date();
        long difference = dateAfter.getTime() - dateBefore.getTime();

        System.out.println("Philosopher " + this.threadId + " needed " + difference + " ms to eat " + this.nbOfConsumptionsRequired + " times");
    }
}
