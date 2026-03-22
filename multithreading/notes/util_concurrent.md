# java.util.concurrent Utilities

## 1. Concurrent Collections
Better alternatives to synchronized wrappers (`Collections.synchronizedList`).

- **ConcurrentHashMap**: Uses bucket-level locking for multi-threaded access.
- **CopyOnWriteArrayList/Set**: Creates a new copy for every write operation, making it ideal for many-read, few-write scenarios.
- **ConcurrentLinkedQueue**: A non-blocking FIFO queue using CAS (Compare-and-Swap).

---

## 2. Blocking Queues
Extremely useful for implementing **Producer-Consumer patterns**.

- **ArrayBlockingQueue / LinkedBlockingQueue**: 
    - `put()`: Blocks if queue is full.
    - `take()`: Blocks if queue is empty.
    - `poll(timeout)`: Blocks for a specified time before returning `null`.

---

## 3. Synchronizers
Mechanism for coordinating threads.

### A. `CountDownLatch`
Allows one or more threads to wait until a set of operations being performed in other threads completes.
- **Use case**: A main thread waiting for 5 worker threads to finish their startup tasks.
- **Key Methods**: `countDown()`, `await()`.

### B. `CyclicBarrier`
Allows a set of threads to all wait for each other to reach a common barrier point.
- **Use case**: Multiple threads performing parallel computation, then merging results. 
- **Key Methods**: `await()`.
- **Note**: Reusable, unlike `CountDownLatch`.

### C. `Semaphore`
Maintains a set of permits. Threads wait until a permit is available.
- **Use case**: Limiting access to a finite resource (e.g., a connection pool of only 10 connections).
- **Key Methods**: `acquire()`, `release()`.

---

## 4. DelayQueue
An unbounded blocking queue of Delayed elements, in which an element can only be taken when its delay has expired.
- **Use case**: Job scheduling, expiring sessions.

---

## 5. Exchanger
A coordination point at which threads can pair and swap elements within pairs.
- **Use case**: Buffer-filling thread swapping with a buffer-processing thread.
