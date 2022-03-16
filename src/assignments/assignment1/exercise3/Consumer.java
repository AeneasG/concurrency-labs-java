package assignment1.exercise3;

import java.nio.BufferUnderflowException;

/**
 * Consumer class tries to consume as many times as "consumeTimes" from the given sharedBuffer and prints the consumed item
 * If the buffer is empty, this does not count
 */
public class Consumer implements Runnable{
    private int consumeTimes;
    private SharedBuffer<Integer> sharedBuffer;

    public Consumer(int consumeTimes, SharedBuffer<Integer> sharedBuffer) {
        this.consumeTimes = consumeTimes;
        this.sharedBuffer = sharedBuffer;
    }


    @Override
    public void run() {
        for(int i = 0; i< consumeTimes; i++){
            try{
                Integer consumedItem = this.sharedBuffer.consume();
                System.out.println("Consumed the item " + consumedItem);
            } catch (BufferUnderflowException e){
                System.out.println("Buffer is emtpy");
                i--;
            }
        }
    }
}
