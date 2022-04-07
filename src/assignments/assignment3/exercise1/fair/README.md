# Explanation
The basic idea is as follows:

We reuse the same Pot and Cook as for the basic implemenation. The savages get additionally a FIFO-Queue which is based from the logic on the circular buffer from Assignment 1.
This buffer serves as a FIFO-Lock and is implemented as circular buffer with the capacity equal to the number of savages. 

All savages enqueue on the queue if they like to remove a portion from the pot and once a savage is the head of the queue he can enter the cirtical section i.e. can take a meal out of the pot and if it is empty order the cook to refill.

The FIFO-Queue ensures the fairness between the savages - however, with the current implementation a savage cannot leave the queue. This is not a problem because a savage is hungry all the time.

## Correctness
Because the FIFO-Queue maintains a strict order, once a savage waits in the queue, he is guaranteed that he cannot be overtaken and eventually can eat.
As the buffer has the size of the number of savages, all savages can enqueue in the queue to wait for the removal of the meal one by one.
