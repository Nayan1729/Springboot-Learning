import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Practice 10: Write thread-safe code.
 * Compares different ways to achieve thread safety:
 * - Unsafe (to show the race condition)
 * - Synchronized methods
 * - ReentrantLock
 * - AtomicInteger (best for this case)
 */
public class ThreadSafeCodePractice {

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 1000;
        int incrementCount = 1000;
        System.out.println("Starting Thread-Safe Counter Exercises (Threads: " + threadCount + ", Increments per thread: " + incrementCount + ")...");
        System.out.println("Total expected result: " + (threadCount * incrementCount));

        // 1. UNSAFE
        UnsafeCounter unsafe = new UnsafeCounter();
        runThreads(unsafe, threadCount, incrementCount);
        System.out.println("- Unsafe Counter Result: " + unsafe.getCount()); // Likely wrong

        // 2. SYNCHRONIZED
        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();
        runThreads(synchronizedCounter, threadCount, incrementCount);
        System.out.println("- Synchronized Counter Result: " + synchronizedCounter.getCount()); // Correct

        // 3. REENTRANTLOCK
        LockingCounter lockingCounter = new LockingCounter();
        runThreads(lockingCounter, threadCount, incrementCount);
        System.out.println("- Locking Counter Result: " + lockingCounter.getCount()); // Correct

        // 4. ATOMIC
        AtomicCounter atomicCounter = new AtomicCounter();
        runThreads(atomicCounter, threadCount, incrementCount);
        System.out.println("- Atomic Counter Result: " + atomicCounter.getCount()); // Correct
    }

    private static void runThreads(Counter counter, int threadCount, int increments) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < increments; j++) {
                    counter.increment();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}

interface Counter {
    void increment();
    int getCount();
}

/**
 * Race Condition! Multiple threads reading/writing "count" simultaneously.
 */
class UnsafeCounter implements Counter {
    private int count = 0;
    @Override
    public void increment() { count++; }
    @Override
    public int getCount() { return count; }
}

/**
 * Correct. Uses inherent lock (this).
 */
class SynchronizedCounter implements Counter {
    private int count = 0;
    @Override
    public synchronized void increment() { count++; }
    @Override
    public synchronized int getCount() { return count; }
}

/**
 * Correct. Uses explicit lock. Good for complex locking logic (tryLock, etc).
 */
class LockingCounter implements Counter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();
    @Override
    public void increment() {
        lock.lock();
        try { count++; } finally { lock.unlock(); }
    }
    @Override
    public int getCount() { return count; }
}

/**
 * Best for simple counters. Uses lock-free CAS (Compare and Swap).
 */
class AtomicCounter implements Counter {
    private final AtomicInteger count = new AtomicInteger();
    @Override
    public void increment() { count.incrementAndGet(); }
    @Override
    public int getCount() { return count.get(); }
}
