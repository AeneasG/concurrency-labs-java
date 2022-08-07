package ex2018;

import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

public class StreamTest {
    static long n0 = 1;
    static long n1 = 1;
    static Stream<Long> get() {
        Random random = new Random();
        return Stream.generate(
                () -> {
                    return random.nextLong();
                });
    }

    public static void main(String[] args){
        Stream<Long> stream = get();
        Iterator<Long> it = stream.iterator();

        for(int i=0;i<40;i++){
            System.out.println(it.next());
        }

        System.out.println("Finished the loop");
        System.out.println(it.next());
    }
}
