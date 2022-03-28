# Assignment 2, Aeneas Grüter, 16-116-352
## Exercise 2.1
#### Execution Times
| N | T   | Time [s]          |
|---|-----|-------------------|
| 10'000'000 | 2   | 2.1               |
| 10'000'000 | 4   | 9.7               |
| 10'000'000 | 8   | 48.7              |
| 10'000'000 | 16  | 589.6 (9.83 min!) |
It is important to mention, that I solved the first assignment on a 8 processor with each two cores. Therefore, in total 16 threads could run in parallel. In this assignment, I used the much slower laptop with only 4 processors with 2 cores each. This is certainly an important reason for the high runtime for the 16 threads scenario. The other one we can observe here, is that the runtime of the algorithm increases exponentially. This can be explained with the longer array which the algorithm requires and the higher contention on the memory bus due to the additional processes.

## Exercise 2.2
The test works the following: We have T-1 read threads and 1 writer thread. Both read resp. write N times the counter variable which is protected by the RWLock implementation. The writer increments the counter, the reader reads the current value of the counter and tracks, how many times he already read this variable (over all reader threads).

We can see that in the first part (the normal read write lock), when executing the tester with values like T=5 and N=10'000, about 90% of the reads return a value in the first quarter of the range of values the counter has during execution. This means, the writer is blocked by the readers and may starve (especially if the number of read threads is increased). 

In the second part (the starvation free write lock), this number drops to less than 15%, which means now the writer will be preferred. Indeed, once the writer announces it's desire to obtain the lock, all readers have to wait, until the writer releases the lock. Thus, the second part perfers the writer. In theory, this could lead to starvation of the readers. However, as there is only a single writer which leaves the critical section at some point, readers can eventually enter the critical section.

## Exercise 3.3
Please refer to the PDF in the corresponding folder.