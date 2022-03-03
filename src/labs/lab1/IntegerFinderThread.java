package labs.lab1;

public class IntegerFinderThread implements Runnable{

    NumberGetter numberGetter;

    public IntegerFinderThread(NumberGetter numberGetter){
        this.numberGetter = numberGetter;
    }

    @Override
    public void run() {
        Integer number = this.numberGetter.getNextNumber();
        while(number != null){
            int nbOfDivisors = 0;
            for(int i=2;i<number;i++){
                if(number % i == 0){
                    nbOfDivisors++;
                }
            }

            this.numberGetter.setHighestNumber(nbOfDivisors, number);

            number = this.numberGetter.getNextNumber();
        }
    }
}
