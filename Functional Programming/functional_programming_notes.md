# Master Functional Programming in Java: From Lambdas to Streams

Traditional Java was **Imperative** (how to do it). Starting with Java 8, we moved toward **Functional Programming** (what to do). This shifts the focus from managing state with loops to transforming data through continuous pipelines.

---

## 1. Lambda Expressions: The Foundation
A Lambda is an **anonymous function** (a method without a name). It allows you to treat code as data—passing logic into methods as easily as you pass a string.

### Syntax
```java
(parameters) -> { body }
```
1.  **Parameters**: Similar to method parameters.
2.  **Arrow**: `->` separates parameters from the body.
3.  **Body**: The code to execute.

### The "Rules" of Lambdas
*   **Single Line**: If the body has one line, `{}` and `return` are optional:
    `s -> s.toUpperCase()`
*   **Type Inference**: You don't need to specify types. Java knows `s` is a String if the context says so.
*   **Effectively Final**: Variables from outside the lambda used inside must be `final` or "effectively final" (not modified after initialization). This is because lambdas capture the value, not the variable itself.

---

## 2. Functional Interfaces
A Lambda cannot exist in a vacuum. It must implement a **Functional Interface**.

### What makes it "Functional"?
An interface with **exactly one Abstract Method (SAM)**.
*   It can have many `default` or `static` methods.
*   The `@FunctionalInterface` annotation is optional but recommended (it triggers a compiler error if you accidentally add a second abstract method).

### The Big Four (Built-in)
Java provides these in `java.util.function` so you don't have to write your own:

| Interface | Method | Input | Output | Example Use Case |
| :--- | :--- | :--- | :--- | :--- |
| **Predicate<T>** | `test(T t)` | One | `boolean` | Filtering (Is number > 10?) |
| **Function<T, R>** | `apply(T t)` | One | `R` (Result) | Mapping (Convert String to Integer) |
| **Consumer<T>** | `accept(T t)` | One | `void` | Printing or modifying state |
| **Supplier<T>** | [get()](file:///d:/Learning%20Planner/TestingSpringBootApplications/src/main/java/com/example/testingspringbootapplications/TodoController.java#31-56) | None | `T` | Generating IDs or lazy loading |

---

## 3. Method References (`::`)
Method references are "shorthand" for lambdas that do nothing but call an existing method.

*   **Lambda**: `s -> System.out.println(s)`
*   **Method Ref**: `System.out::println`

### 4 Types of Method References:
1.  **Static**: `Math::abs` (instead of `n -> Math.abs(n)`)
2.  **Instance on Specific Object**: `myObj::doSomething`
3.  **Instance on Type**: `String::toUpperCase` (calls it on the parameter passed in)
4.  **Constructor**: `ArrayList::new` (instead of [() -> new ArrayList<>()](file:///d:/Learning%20Planner/TestingSpringBootApplications/src/main/java/com/example/testingspringbootapplications/Todo.java#14-30))

---

## 4. The Streams API: Data Pipelines
A Stream is **not** a data structure (like a List); it is a **sequence of elements** from a source (collection, array) that supports aggregate operations.

### The Pipeline Lifecycle
1.  **Source**: `list.stream()`
2.  **Intermediate Operations**: (Transform/Filter) - These are **LAZY** (they don't run until a terminal op is called).
3.  **Terminal Operation**: (Produce Result) - This starts the processing and closes the stream.

### Common Intermediate Operations (Returns a Stream)
*   **`filter(Predicate)`**: Keep items that match a condition.
*   **`map(Function)`**: Transform each item.
*   **`sorted()`**: Sort items (natural or custom comparator).
*   **`distinct()`**: Remove duplicates.
*   **`limit(n)`**: Stop after *n* items.
*   **`flatMap(Function)`**: Used for "flattening" nested structures (like a List of Lists into a single List).

### Common Terminal Operations (Returns non-Stream)
*   **`forEach(Consumer)`**: Perform action for each.
*   **`collect(Collector)`**: Turn the results back into a List, Set, or Map.
*   **`count()`**: Count items.
*   **`reduce()`**: Combine all items into one (e.g., Summing numbers).
*   **`anyMatch` / `allMatch`**: Returns boolean.

---

## 5. `Optional<T>`: Avoiding the Null Check
`Optional` is a container object used to represent the presence or absence of a value. It prevents `NullPointerException`.

### Chaining with Optional
Instead of `if (x != null)`, use:
```java
Optional.ofNullable(getUser())
        .map(User::getName)
        .orElse("Guest");
```

### Key Methods:
*   `isPresent()` / `isEmpty()`: Check state.
*   `ifPresent(Consumer)`: Run code if value exists.
*   `orElse(default)`: Return default if empty.
*   `orElseThrow()`: Throw exception if empty.

---

## Summary Flow
1.  **Why?** Code is cleaner, safer, and side-effect-free.
2.  **How?** Write a **Lambda** or **Method Reference**.
3.  **Where?** Pass it to a **Functional Interface**.
4.  **Processing?** Use the **Stream API** to build a pipeline.
5.  **Safety?** Wrap return values in **Optional**.
