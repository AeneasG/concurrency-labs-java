# Assignment 2, Aeneas Grüter, 16-116-352
## Exercise 2.1
#### Execution Times
| N | T   | Time [s]          |
|---|-----|-------------------|
| 10'000'000 | 2   | 2.1               |
| 10'000'000 | 4   | 9.7               |
| 10'000'000 | 8   | 48.7              |
| 10'000'000 | 16  | 589.6 (9.83 min!) |
It is important to mention, that while I solved the first assignment on a 8 processor with each two cores. Therefore in total 16 threads could run in parallel. In this exercise, I used the much slower laptop with only 4 processors with 2 cores each. This is certainly an important reason for the high runtime for the 16 threads scenario. The other one we can observe here, is that the runtime of the algorithm increases exponentially. This can be explained with the longer array and the higher contention on the memory bus.