# ExecutorService and Thread Pools

## 1. What is an `ExecutorService`?
`ExecutorService` is a higher-level API for running tasks in the background. It manages a **pool of threads**, which avoids the overhead of creating/destroying individual threads for every task.

### Why Use `ExecutorService`?
- **Resource Reuse**: Instead of starting a new thread each time, existing threads are reused.
- **Improved Performance**: Reducing thread creation/teardown overhead.
- **Manageability**: Control over the number of active threads (to avoid crashing the JVM).

---

## 2. Types of Thread Pools
Java provides several standard pool types via the `Executors` factory class:

### A. Fixed Thread Pool (`Executors.newFixedThreadPool(n)`)
Creates a pool with a set number of threads. If all threads are busy, tasks are queued until one is free.
- **Use case**: When you know exactly how many resources you can allocate.

### B. Cached Thread Pool (`Executors.newCachedThreadPool()`)
Creates new threads as needed but reuses inactive threads if they exist.
- **Use case**: When you have many short-lived, low-latency tasks.

### C. Single Thread Executor (`Executors.newSingleThreadExecutor()`)
Ensures exactly one thread executes tasks in sequence.
- **Use case**: When tasks must be performed one after another (order matters).

### D. Scheduled Thread Pool (`Executors.newScheduledThreadPool(n)`)
Supports delayed or periodic task execution.
- **Use case**: Cron-like background tasks.

---

## 3. Submitting Tasks
- `execute(Runnable)`: No return value; throws an exception if the task fails.
- `submit(Runnable)` / `submit(Callable<T>)`: Returns a `Future<T>` representing the pending result of the task.

---

## 4. `Future` and `Callable`
- **`Callable<V>`**: Similar to `Runnable`, but returns a value and can throw checked exceptions.
- **`Future<V>`**: A "handle" to the result of a task.
    - `get()`: Blocks execution until the result is available.
    - `isDone()`: Checks if the task is finished.
    - `cancel()`: Terminates the task before completion.

---

## 5. Lifecycle Management
Thread pools must be explicitly shut down, or they can prevent the JVM from exiting.
- `shutdown()`: Gradually stops the pool; finishes currently running tasks but rejects new ones.
- `shutdownNow()`: Attempts to stop all active tasks immediately and returns a list of pending tasks.
- `awaitTermination()`: Blocks the current thread until the pool is completely halted.

---

## 6. Real-world Workflow
```java
ExecutorService executor = Executors.newFixedThreadPool(4);

Future<Integer> future = executor.submit(() -> {
    // Perform complex computation
    Thread.sleep(2000);
    return 42;
});

// Do other work while the task runs...

try {
    Integer result = future.get(); // Blocks until finished
    System.out.println("Result: " + result);
} finally {
    executor.shutdown();
}
```
