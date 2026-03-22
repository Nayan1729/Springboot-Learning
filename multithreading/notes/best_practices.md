# Best Practices for Concurrent Programming

## 1. Always Use Thread Pools
- **Never create `new Thread()` manually** for application logic. Use `ExecutorService` or `ForkJoinPool`.
- Properly size your thread pool:
    - **CPU-bound tasks**: `Runtime.getRuntime().availableProcessors() + 1` threads.
    - **I/O-bound tasks**: Large pools, as threads will mostly be waiting on data.

---

## 2. Favor Immutability
- **Immutable objects** are inherently thread-safe. They have no state to change, so they don't need locks.
- Use `final` everywhere you can.
- Use **String**, **Integer**, or custom records (Java 14+) which are immutable.

---

## 3. Limit Variable Scope
- Keep shared state to an absolute minimum.
- If a variable can be kept local within a method, do it. Local variables are stored on the thread's stack and are private to that thread.
- Use `ThreadLocal` for data that must be available thread-wide but is unique to each thread.

---

## 4. Use Atomic Variables Over Locks
- For simple counters and state flags, `AtomicInteger`, `AtomicLong`, and `AtomicReference` are faster and more efficient than `synchronized` blocks.

---

## 5. Prefer Concurrent Collections
- Always use `ConcurrentHashMap` instead of `Hashtable` or `Collections.synchronizedMap(hashMap)`.
- Use `CopyOnWriteArrayList` only for very high volumes of reads and low volumes of writes.

---

## 6. Proper Shutdown Procedures
- Always shut down executors. Use a `try-finally` block or a shutdown hook to ensure no threads are leaked.
- `shutdown()` is usually safer than `shutdownNow()` unless you must terminate immediately.

---

## 7. Handle `InterruptedException`
- If you catch `InterruptedException`, always **restore the interrupted status** if you don't rethrow it.
- Correct pattern:
```java
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt(); // Restore status
    // Handle the interruption
}
```

---

## 8. Document Thread-Safety
- Always document if a class or method is thread-safe.
- If it's not thread-safe, document the expected usage (e.g., "Must be synchronized externally").
