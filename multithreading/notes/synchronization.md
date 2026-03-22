# Synchronization and Thread Safety

## 1. Shared Mutable State
Thread safety is usually only a concern when multiple threads share **mutable state**. If several threads access the same instance field or global variable, and one or more write to it, data corruption can occur.

---

## 2. Core Concept: Synchronization
Synchronization is the mechanism to "lock" access to sensitive data so only one thread can modify it at a time. It ensures **Atomicity** (all or nothing) and **Visibility** (changes are immediately seen).

### Methods of Synchronization:

### A. The `synchronized` Keyword
Can be used on methods or specific blocks of code:

- **Synchronized Method**: Locks the entire instance (`this`).
```java
public synchronized void increment() {
    count++;
}
```

- **Synchronized Block**: Locks on a specific object (preferred for fine-grained control).
```java
public void update(int value) {
    synchronized(lock) {
       this.value = value;
    }
}
```

- **Static Synchronized Method**: Locks the class object (`MyClass.class`).

### B. Locks (`ReentrantLock`)
Part of `java.util.concurrent.locks`. Offers more control than `synchronized`:
- `lock()` / `unlock()`: Explicit lock management.
- `tryLock()`: Attempt to acquire the lock without blocking indefinitely.
- `lockInterruptibly()`: Allows the thread to be interrupted while waiting for the lock.

---

## 3. Atomic Variables
For simple operations (like counters), using a full lock is overkill and expensive. Java provides lock-free **Atomic variables**:
- `AtomicInteger`, `AtomicLong`, `AtomicBoolean`, `AtomicReference<T>`.
- Use **Compare-And-Swap (CAS)** operations, which are implemented natively by modern CPUs for high performance.

```java
AtomicInteger counter = new AtomicInteger();
counter.incrementAndGet(); // Thread-safe without explicit locks
```

---

## 4. `volatile` Keyword
Used to ensure that changes to a variable are visible across all threads immediately. 
- **Visibility**: Every read of a `volatile` variable will be from main memory (not CPU cache).
- **No Atomicity**: `volatile` does NOT make an operation (like `++`) atomic.

### When to use `volatile`:
- Flag variables (e.g., `isRunning`) where only one thread updates and many read.

---

## 5. Thread-Safe Collections
Standard collections (`ArrayList`, `HashMap`) are not thread-safe.

- **Legacy**: `Vector`, `Hashtable` (slow, entire collection is locked).
- **Synchronized Wrappers**: `Collections.synchronizedList(list)`.
- **Concurrent Collections (Best)**: `ConcurrentHashMap`, `CopyOnWriteArrayList`.
    - `ConcurrentHashMap`: Uses fine-grained "bucket-level" locking.
    - `CopyOnWriteArrayList`: Creates a new copy of the array for every write operation (efficient for many-read, few-write scenarios).
