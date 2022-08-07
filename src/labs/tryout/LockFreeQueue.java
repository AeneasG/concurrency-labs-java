package tryout;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue {

    private AtomicReference<Node> head, tail;

    private class Node {
        AtomicReference<Node> next;
        Object obj;

        public Node() {}
    }

    public LockFreeQueue(){
        this.head = new AtomicReference<>(new Node());
        this.tail = new AtomicReference<>(this.head.get());
        this.tail.get().next = new AtomicReference<>();
    }

    public void enqueue(Object obj) {
        while(true) {
            Node node = new Node();
            node.obj = obj;
            node.next = new AtomicReference<>();
            Node currTail = this.tail.get();
            if(currTail.next.compareAndSet(null, node)){
                this.tail.set(node);
                return;
            }
        }
    }

    public Object dequeue() {
        while(true) {
            Node currHead = this.head.get();
            Node firstElem = currHead.next.get();

            if(firstElem != null && this.head.compareAndSet(currHead, firstElem)) {
                return firstElem.obj;
            }
        }
    }

    public static void main(String[] args) {
        int T = 4;
        int N = 1000;
        Thread[] threads = new Thread[T];


        LockFreeQueue lockFreeQueue = new LockFreeQueue();
        for(int i=0;i<T;i++){
            threads[i] = new Thread(new QueueTester(i % 2 == 0, 0, N, lockFreeQueue));
        }

        Date dateBefore = new Date();

        for(int i=0;i<T;i++){
            threads[i].start();
        }

        for(int i=0;i<T;i++){
            try{
                threads[i].join();
            } catch (InterruptedException e){}
        }

        Date dateAfter = new Date();
        long difference = dateAfter.getTime() - dateBefore.getTime();

        System.out.println("Needed " + difference + " ms to execute with nb of iterations: " + N);
        System.out.println("queue is empty -> " + lockFreeQueue.head.get().next.compareAndSet(null, null));
    }

    private static class QueueTester implements Runnable {
        boolean isProducer;
        int start, end;
        LockFreeQueue queue;
        public QueueTester(boolean b, int start, int end, LockFreeQueue queue) {
            this.isProducer = b;
            this.start = start;
            this.end = end;
            this.queue = queue;
        }

        @Override
        public void run() {
            if(this.isProducer) {
                for(int i = start; i < end; i++){
                    this.queue.enqueue("" + i);
                }
            } else {
                for(int i = start; i < end; i++){
                    String result = (String) this.queue.dequeue();
                    System.out.println(result);
                }
            }
        }
    }
}
