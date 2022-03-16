package assignment1.exercise3;

import java.lang.Integer;
import java.nio.BufferOverflowException;

/**
 * Producer produces integer numbers and publishes them to the given shared buffer
 * it starts with 0 and ends with the given number of "produceIterations"
 */
public class Producer implements Runnable {

    private int produceIterations;
    private SharedBuffer<Integer> sharedBuffer;

    public Producer(int produceIterations, SharedBuffer<Integer> sharedBuffer) {
        this.produceIterations = produceIterations;
        this.sharedBuffer = sharedBuffer;
    }

    public void run() {
        for(int i = 0;i<this.produceIterations;i++){
            try {
                sharedBuffer.produce(i);
            } catch (BufferOverflowException e){
                System.out.println("Buffer is full");
                i--;
            }
        }
    }
}
