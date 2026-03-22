# Common Concurrency Issues

## 1. Race Conditions
A race condition occurs when two or more threads compete for the same shared resources, and the outcome depends on the order of execution.

### Example: "Check-Then-Act" (Typical Race)
- **Problem**: Thread A checks `if (count < 10)`, then Thread B checks `if (count < 10)`. Both threads proceed to increment `count`, resulting in `count = 11`.
- **Solution**: Use atomic operations or synchronized blocks to ensure check and act are performed together (atomically).

---

## 2. Deadlocks
A deadlock happens when two or more threads are waiting for each other to release a resource/lock indefinitely.

### Conditions for Deadlock:
- **Mutual Exclusion**: At least one resource must be held in non-sharable mode.
- **Hold and Wait**: A thread is holding a resource while waiting for another.
- **No Preemption**: A resource cannot be taken away by force.
- **Circular Wait**: A set of threads waiting in a cycle.

### Avoiding Deadlocks:
- **Consistent Lock Ordering**: Always acquire locks in the same order.
- **Timeouts**: Use `tryLock(timeout, timeUnit)` to avoid waiting forever.
- **Avoid Nested Locks**: Try not to hold more than one lock at a time.

---

## 3. Livelocks
Similar to deadlocks, but threads are not blocked; they are actively changing their states in response to each other, but making no progress.
- **Analogy**: Two people in a narrow hallway keep stepping aside to let the other pass, but stepping in the same direction every time.

---

## 4. Starvation
Starvation occurs when a thread is perpetually denied access to resources because other "greedy" threads are prioritized or held in a long-running lock.

### Solutions:
- **Fair Locks**: `new ReentrantLock(true)` ensures FIFO (first-in-first-out) lock acquisition.
- **Reasonable Priorities**: Don't use extreme thread priorities unless absolutely necessary.

---

## 5. Memory Visibility Issues
Modern CPUs use caches. Thread A might update a variable in its cache, and Thread B might read the old value from its own cache or main memory.
- **Solution**: Use `volatile`, `synchronized`, or `final` to ensure memory visibility between threads.

---

## 6. Thread Leaks
When threads are created but never terminated, consuming system resources until the JVM runs out of memory (OOM).
- **Solution**: Always shut down `ExecutorService` and use thread pools instead of manually creating threads.
