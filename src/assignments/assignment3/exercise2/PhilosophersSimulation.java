package assignment3.exercise2;

import java.util.Date;

/**
 * Simulation of the Philosophers Eating and Sleeping
 * Arguments
 * - (1): number of philosophers to simulate
 * - (2): number of portions that a philosopher eats before the thread finished. This parameter is used to make the simulation finite. Use a high value
 */
public class PhilosophersSimulation {

    public static void main(String args[]) {
        if(args.length < 2) {
            throw new RuntimeException("Please provide at least 2 integer arguments");
        }
        int nbOfPhilosophers;
        int nbOfConsumptionsRequired;
        try {
            nbOfPhilosophers = Integer.parseInt(args[0]);
            nbOfConsumptionsRequired = Integer.parseInt(args[1]);
        } catch (NumberFormatException e){
            throw new RuntimeException("Please provide Integer arguments");
        }

        if(nbOfPhilosophers < 1 || nbOfConsumptionsRequired < 100){
            throw new RuntimeException("Invalid Arguments!");
        }

        Thread[] threads = new Thread[nbOfPhilosophers];
        Fork[] forks = new Fork[nbOfPhilosophers];

        Date dateBefore = new Date();

        for(int i=0;i<nbOfPhilosophers;i++){
            forks[i] = new Fork();
        }

        for(int i=0;i<nbOfPhilosophers;i++){
            threads[i] = new Thread(new Philosopher(forks[i], forks[(i+1) % nbOfPhilosophers], nbOfConsumptionsRequired, i));
        }

        for(int i=0;i<nbOfPhilosophers;i++){
            threads[i].start();
        }

        for(int i=0;i<nbOfPhilosophers;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){
                System.out.println("Interrupted Exception " + e.getMessage());
            }
        }

        Date dateAfter = new Date();
        long difference = dateAfter.getTime() - dateBefore.getTime();

        System.out.println("Needed " + difference + " ms to get all philosophers full");
    }
}
