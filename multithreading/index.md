# Multithreading Learning Resources

This directory contains learning notes and exercises for Java Concurrency and Multithreading, as outlined in the learning goals.

## 📚 Learning Notes
- [**Thread Basics & Runnable**](file:D:/Learning%20Planner/multithreading/notes/basics.md)
  - Understand threads vs. processes, thread lifecycle, and how to create threads (Runnable vs. Thread).
- [**Advanced Concurrency Overview**](file:"D:/Learning Planner/multithreading/notes/concurrency_advanced.md")
  - Key challenges, `java.util.concurrent` overview, and modern Java approaches.
- [**ExecutorService & Thread Pools**](file:D:/Learning%20Planner/multithreading/notes/executor_service.md)
  - Benefits of thread reuse, various pool types, and managing task submission with `Future` and `Callable`.
- [**Synchronization & Thread Safety**](file:///d:/Learning%20Planner/multithreading/notes/synchronization.md)
  - Shared mutable state, locks, atomic variables, and `volatile`.
- [**Common Concurrency Issues**](file:///d:/Learning%20Planner/multithreading/notes/concurrency_issues.md)
  - Race conditions, deadlocks, livelocks, starvation, and memory visibility.
- [**java.util.concurrent Utilities**](file:///d:/Learning%20Planner/multithreading/notes/util_concurrent.md)
  - Concurrent collections, Blocking Queues, and Synchronizers (`CountDownLatch`, `Semaphore`, etc.).
- [**Best Practices**](file:///d:/Learning%20Planner/multithreading/notes/best_practices.md)
  - Sizing thread pools, favors immutability, properly shutting down executors.

## 💻 Exercises & Practice
- [**Async Data Processing with CompletableFuture**](file:///d:/Learning%20Planner/multithreading/exercises/CompletableFuturePractice.java)
  - Demonstrates chaining (`thenApply`), combining (`thenCombine`), and handling multiple tasks (`allOf`).
- [**Parallel Task Execution with ExecutorService**](file:///d:/Learning%20Planner/multithreading/exercises/ParallelTasksDemo.java)
  - Demonstrates workload partitioning and collecting partial results into a final sum.
- [**Thread-Safe Counter Implementation**](file:///d:/Learning%20Planner/multithreading/exercises/ThreadSafeCodePractice.java)
  - Comparison of Unsafe, Synchronized, Locking, and Atomic counter techniques.

---
*Created as part of the Multithreading Learning Plan.*
