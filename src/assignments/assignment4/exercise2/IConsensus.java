package assignment4.exercise2;

/**
 * Consensus interface; A process can propose a value by invoking "decide" with the proposal. The return value is the decision that has been made.
 */
public interface IConsensus {

    // propose value v and return agreed-upon value
    Object decide(Object v);
}
