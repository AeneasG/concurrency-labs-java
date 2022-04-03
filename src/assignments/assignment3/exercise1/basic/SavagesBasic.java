package assignment3.exercise1.basic;

import java.util.Date;

public class SavagesBasic {

    public static void main(String args[]){
        if(args.length < 2) {
            throw new RuntimeException("Please provide at least 2 integer arguments");
        }
        int nbOfSavages;
        int potCapacity;
        try {
            nbOfSavages = Integer.parseInt(args[0]);
            potCapacity = Integer.parseInt(args[1]);
        } catch (NumberFormatException e){
            throw new RuntimeException("Please provide Integer arguments");
        }

        if(nbOfSavages < 1 || potCapacity < 2){
            throw new RuntimeException("Invalid Arguments!");
        }

        Thread[] threads = new Thread[nbOfSavages];

        Date dateBefore = new Date();

        Pot pot = new Pot();

        Cook cook = new Cook(nbOfSavages, potCapacity, pot);

        for(int i=0;i<nbOfSavages;i++){
            threads[i] = new Thread(new Savage(i, pot, cook));
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
