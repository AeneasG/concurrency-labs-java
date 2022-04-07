package assignment3.exercise1.fair;

import assignment3.exercise1.Cook;
import assignment3.exercise1.Pot;

import java.util.Date;

/**
 * Idea: reuse the shared buffer from Assignment 1, Ex. 3; each savage puts himself in the list and spins on the head of the queue
 *
 */
public class SavagesFair {

    public static void main(String args[]){
        if(args.length < 3) {
            throw new RuntimeException("Please provide at least 3 integer arguments");
        }
        int nbOfSavages;
        int potCapacity;
        int maxConsumations;
        try {
            nbOfSavages = Integer.parseInt(args[0]);
            potCapacity = Integer.parseInt(args[1]);
            maxConsumations = Integer.parseInt(args[2]);
        } catch (NumberFormatException e){
            throw new RuntimeException("Please provide Integer arguments");
        }

        if(nbOfSavages < 1 || potCapacity < 2 || maxConsumations < 10){
            throw new RuntimeException("Invalid Arguments!");
        }

        Thread[] threads = new Thread[nbOfSavages];

        Date dateBefore = new Date();

        FifoQueue fifoQueue = new FifoQueue(nbOfSavages);
        Pot pot = new Pot();

        Cook cook = new Cook(nbOfSavages, potCapacity, pot);

        for(int i=0;i<nbOfSavages;i++){
            threads[i] = new Thread(new Savage(i, pot, cook, fifoQueue, maxConsumations));
        }

        for(int i=0;i<nbOfSavages;i++){
            threads[i].start();
        }

        for(int i=0;i<nbOfSavages;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){
                System.out.println("Interrupted Exception " + e.getMessage());
            }
        }

        Date dateAfter = new Date();
        long difference = dateAfter.getTime() - dateBefore.getTime();

        System.out.println("Needed " + difference + " ms to get all savages full");

    }
}
