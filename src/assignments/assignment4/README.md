# Assignment 4, Aeneas Grüter, 16-116-352
## Exercise 1
#### Execution Times
| Impelementation      | Time [s] |
|----------------------|----------|
| unsafe               | ∞        |
| safe                 | 4.7      |
| concurrent           | 2.4      |
| barriers and latches | 6.7      |

#### Comments
In order to enhance reusage of the classes, the safe and unsafe ``ConsumerProducer`` class is the same which receives a different list. The concurrent variant uses a new ``QueueConsumerProducer`` class because it operates with a queue which is not compatible with the list interface. Lastly, the barriers and latches implementation requires some extra logic to use the barrier and latches and therefore again needs an own implementation, the ``QueueConsumerProducerWithBarrier``.

In the implementation of the unsafe variant I integrated an exit which is reached after a process tried to read a value more than twice as often without getting one. Interestingly, a reader fails after very few reads. This indicates that the list is messed up quite quickly. 

Without this abort condition every consumer is likely to run forever. This is because he continues trying to remove an element from the list. Because the list is accessed concurrently by 2 producers and 1 other consumer, the list is broken and the consumer cannot consume enough items even though the producers would have produces enough.

For this reason, the execution time of the unsafe implementation is set to infinity. The execution time of the actual implementation is not of interest because it largely depends how fast we allow a consumer to give up. 

## Exercise 2
#### Comments
The implementation is possible and may look like the following:
`````java
public class TasConsensus implements IConsensus{

    private class NullObject {}

    AtomicReference<Object> decision;
    Object nullObject;

    public TasConsensus() {
        this.nullObject = new NullObject();
        this.decision = new AtomicReference<Object>(this.nullObject);
    }

    @Override
    public Object decide(Object v) {
        this.decision.compareAndSet(this.nullObject, v);
        return this.decision.get();
    }
}
`````
It uses a private class NullObject, creates a new reference on such an object and stores this in the atomic object reference called ``decision``. It is important that the ``deciscion`` initially holds a private null value which no other process is able to propose to propose (by invoking decide). The idea is that if some process invokes decide, he will try to set his own value as ``decision``. In the end, the result of ``compareAndSet`` does not matter, because the decision does not change after the first successful swap and `decision` will hold the correct reference at any time after `compareAndSet` has been invoked the first time.

The LockConsensus from the exercise, a shared Consensus interface, and a tester to test both implementations of Consensus are provided. The tester first uses the LockConsensus and after the TasConsensus to decide for a value among N processes, where N is a input to the program. 