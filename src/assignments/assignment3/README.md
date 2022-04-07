# Assignment 3, Aeneas Grüter, 16-116-352
## Exercise 3.1
Please refer to the README files in the corersponding folder (`exercise1/basic` and `exercise1/fair` respectively) for argumentation of correctness.

## Exercise 3.2
The key idea is that we enumerate all philosophers at the table. Philosophers with a even id grab first the left fork whereas philosophers with an uneven id grab first the right fork.

During waiting for a second fork the first fork is not release which ensures that no philosopher starves.

For simulation purposes, all philosophers only eat a finite number of meals (according to given parameter to the simulation).

### Correctness
Generally, for a philosopher B and his neighbors A and C, let's say that B tries to grab first the fork BC (which is the one between philosopher B and C). In this case, C tries to grab this fork as first as well.
This means, if B succeeds, then it has to compete with A for the fork AB (if A did get the other fork). Let's say, A got the other fork and also got fork AB before B, thus A can eat. B keeps the fork BC grabbed. Once A has finished eating, B can grab AB and eat as well.
If B would grab the fork AB before A, then B could eat. Thus, there is always at least one philosopher eating, thus no deadlock can occur.

Speaking different: A fork between two philosopher is either competed of both neighbouring philosophers as first fork or as second. This property prevents a deadlock. 

**Note**: It does not depend whether the number of philosophers is even or uneven.

**Note**: Because the philosophers do not release forks they already grabbed, it could occur that only 1 philosopher eats at a given time with N philosophers. However, no deadlock can occur.
