# Explanation
The basic idea is the following. Savages and the cook are threads. The savages are started by the SavagesBasic program. Once the pot is emtpy, i.e. a savage could not get a meal, he tries to get the lock to issue a refill request. If he can get the lock, he starts the cook that performs the refill. Another savage that cannot get a meal as well will not get the lock and thus does not order the cook to refill. Instead of spinning on the lock, he just passes and waits until the pot is refilled.

### Correctness
Because every savage waits until a refill is finished, during the refill of the cook no one tries to order the pot again to be refilled. Savages already tried to get a meal and failed do not spin on the refillLock but simply perceed and wait as well until the pot is refilled because the one savage that obtained the refill lock will notify the cook. This ensures, that the cook does not get two orders at the same time (resp. while he is perparing the new portions). 

As the savages wait until the pot is refilled, a savage cannot eat a portion from an emtpy pot and the cook can calmly refill the pot before the savages try to get a portion.