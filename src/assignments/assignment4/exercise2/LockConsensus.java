package assignment4.exercise2;

public class LockConsensus implements IConsensus {

    public LockConsensus(){}

    Object decision = null;

    @Override
    public synchronized Object decide(Object v) {
        if (decision == null)
            decision = v;
        return decision;
    }
}
