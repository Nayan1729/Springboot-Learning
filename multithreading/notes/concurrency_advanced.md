# Advanced Java: Concurrency & Multithreading Overview

## 1. Why Concurrency?
- **Throughput**: Performing multiple tasks concurrently (e.g., serving several HTTP requests at once).
- **Responsiveness**: Keeping the UI responsive while background tasks (I/O, network) run.
- **Resource Utilization**: Effectively using modern multi-core CPUs.

---

## 2. Challenges in Multithreading
Multithreaded programs are often harder to design, debug, and reason about than single-threaded ones.

- **Race Conditions**: When two or more threads access shared data and try to change it at the same time.
- **Deadlocks**: When two threads are stuck waiting for each other to release a resource/lock.
- **Livelocks**: When threads keep changing states in response to each other but never progress.
- **Starvation**: When a thread is perpetually denied access to resources.
- **Memory Visibility**: Changes made by one thread might not be immediately visible to others due to CPU caching.

---

## 3. The `java.util.concurrent` Package
Introduced in Java 5, this package (`JUC`) revolutionized concurrency by providing high-level utilities that abstract away the complexity of low-level `wait()`/`notify()` / `synchronized`.

### Core JUC Components:
- **Executor Framework**: Decouples task submission from execution.
- **Queues**: Thread-safe collections (e.g., `BlockingQueue`, `ConcurrentLinkedQueue`).
- **Locks**: Flexible alternatives to `synchronized` (e.g., `ReentrantLock`, `ReadWriteLock`).
- **Synchronizers**: Coordination mechanisms (`CountDownLatch`, `CyclicBarrier`, `Semaphore`).
- **Atomic Variables**: Lock-free operations for single variables (`AtomicInteger`, `AtomicLong`).
- **Concurrent Collections**: Optimized thread-safe collections (`ConcurrentHashMap`).

---

## 4. Modern Approaches (Java 8+)
Java 8 and beyond introduced a functional programming style that makes asynchronous and parallel code much more expressive.

### A. CompletableFuture
- **Functional Style**: Allows chaining tasks (`thenApply`, `thenAccept`, `thenCompose`) without nested callbacks.
- **Async Pipelines**: You can easily combine the results of two independent async tasks with `thenCombine`.
- **Exception Handling**: Built-in support for handling errors asynchronously via `exceptionally` or `handle`.

#### Before vs After: Chaining Async Tasks
| Aspect | Before (Manual `Future` / Callbacks) | After (`CompletableFuture`) |
| :--- | :--- | :--- |
| **Blocks thread?** | Yes, `future.get()` blocks the main thread. | No, `thenAccept()` runs when ready. |
| **Complexity** | Requires complex `try-catch` and nested logic. | Fluent, readable functional chain. |
| **Error Handling** | Must catch `ExecutionException` manually. | Handled gracefully with `.exceptionally()`. |

**Before (Callback Hell / Blocking):**
```java
Future<User> future = executor.submit(() -> fetchUser(id));
try {
    User user = future.get(); // Blocks thread!
    String greeting = formatGreeting(user);
    System.out.println(greeting);
} catch (Exception ex) {
    System.out.println("Hello, Stranger");
}
```

**After (Fluent Chain):**
```java
CompletableFuture.supplyAsync(() -> fetchUser(id))
    .thenApply(user -> formatGreeting(user))
    .exceptionally(ex -> "Hello, Stranger")
    .thenAccept(System.out::println);
```


### B. Parallel Streams
- **Effortless Parallelism**: Converts a collection into a stream that can be processed across multiple CPU cores by simply calling `parallelStream()`.
- **Under the Hood**: Uses the standard `ForkJoinPool.commonPool()` to split the data.
- **Caveat**: Not always faster! High overhead for small collections. Use only for CPU-intensive tasks with large amounts of data.

### C. Fork/Join Framework (Java 7+)
- **Work-Stealing Algorithm**: Busy threads "steal" tasks from the back of the deques of other busy/overloaded threads to keep the CPU fully utilized.
- **Divide and Conquer**: Ideal for recursive tasks where a large problem is broken down into smaller sub-tasks (e.g., sorting, image processing).
- **Core Classes**: `ForkJoinPool`, `RecursiveTask<V>`, and `RecursiveAction`.

---

## 5. Virtual Threads (Java 21+)
The most significant change to Java's concurrency model in decades, introduced as part of **Project Loom**.

### A. Lightweight, Not OS-Specific
- **Platform Threads**: (Prior to Java 21) Mapped 1:1 to OS kernel threads. They are "heavy" (typically 1-2MB of stack space) and context-switching is expensive.
- **Virtual Threads**: Managed by the JVM, not the OS. They are "lightweight" (only a few hundred bytes) and "cheap" to create—you can have millions of them.

### B. The "Blocking" Solution
- Traditionally, developers used reactive programming (like RxJava) to avoid blocking threads. Virtual threads make this unnecessary.
- **Simple Code**: You can write simple, readable, synchronized/blocking code, and the JVM will automatically handle the context switching when a virtual thread blocks (e.g., on I/O).

### C. When to Use:
- **High Throughput**: Perfect for servers handling many thousands of concurrent requests where most tasks spend time waiting on I/O (database, web services).
- **Easy Transition**: You can migrate existing `ExecutorService` code to virtual threads simply by using `Executors.newVirtualThreadPerTaskExecutor()`.

