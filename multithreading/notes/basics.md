# Thread Basics and Runnable Interface

## 1. Introduction to Threads
A **thread** is the smallest unit of execution within a process. Multithreading allows a program to perform multiple tasks concurrently, improving performance and responsiveness (especially in GUI or network-heavy applications).

### Process vs. Thread
- **Process**: An independent execution environment with its own memory space (e.g., a running application).
- **Thread**: A "lightweight process" that shares resources (memory, file handles) with other threads in the same process.

---

## 2. Creating a Thread in Java
There are two primary ways to create a thread:

### A. Extending the `Thread` Class
1. Create a class that extends `java.lang.Thread`.
2. Override the `run()` method.
3. Instantiate the class and call `start()`.

```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start(); // Spawns a new thread and executes run()
    }
}
```

### B. Implementing the `Runnable` Interface (Recommended)
This approach is preferred because:
- Java supports only single inheritance; implementing `Runnable` leaves the class free to extend another class.
- Promotes decoupling: Separate the task logic (`Runnable`) from the execution mechanism (`Thread`).

```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable running: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }
}
```

---

## 3. The Thread Lifecycle
A thread goes through several states (as defined in `Thread.State`):
1. **New**: Born but not yet started (`thread.start()` not called).
2. **Runnable**: Ready to run, waiting for CPU time.
3. **Blocked**: Waiting for a monitor lock (synchronization).
4. **Waiting**: Waiting indefinitely for another thread to perform a specific action (`wait()`, `join()`).
5. **Timed Waiting**: Waiting for a specified time (`sleep()`, `wait(timeout)`).
6. **Terminated**: Finished execution or aborted.

---

## 4. Key Java Properties & Methods
- `Thread.currentThread()`: Returns a reference to the currently executing thread.
- `getName()` / `setName(String)`: Get or set thread identity.
- `getPriority()` / `setPriority(int)`: Values from 1 (`MIN_PRIORITY`) to 10 (`MAX_PRIORITY`).
- `join()`: Current thread waits until the target thread finishes.
- `sleep(long millis)`: Pauses execution for a set duration.

---

## 5. Why `start()` instead of `run()`?
- `run()`: Executes the method in the **current** thread (no new thread is created).
- `start()`: Registers the thread with the JVM/OS and calls `run()` in a **new** call stack.
