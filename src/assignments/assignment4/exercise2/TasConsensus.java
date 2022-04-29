package assignment4.exercise2;

import java.util.concurrent.atomic.AtomicReference;

public class TasConsensus implements IConsensus{

    private class NullObject {
    }

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
