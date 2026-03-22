# Lazy vs Eager Loading

This topic is fundamental to performance in JPA applications. It defines **HOW** and **WHEN** related entities are fetched from the database.

## 1. What was used BEFORE?
In manual **JDBC queries**, developers had two choices:
- Fetch everything at once with complex SQL `JOIN` statements (Equivalent to Eager).
- Run separate SQL queries for each relation as needed (Equivalent to Lazy).
- **Cons**: Both were manual and error-prone. You could easily end up fetching way too much data or running hundreds of unnecessary queries.

## 2. What is used NOW?
JPA provides **FetchType** strategies to handle related records automatically.

### A. Eager Loading (FetchType.EAGER)
Eager loading fetches the related entities **at the same time** as the parent entity.
- **Example**: When you load a `User`, JPA automatically loads all of its `Todos`.
- **Default for**: `@OneToOne` and `@ManyToOne` (the "To-One" side).

```java
@ManyToOne(fetch = FetchType.EAGER)
private User user;
```

> [!WARNING]
> Eager loading is dangerous. It can lead to massive data being loaded unintentionally, which kills application performance. Use it sparingly.

### B. Lazy Loading (FetchType.LAZY)
Lazy loading fetches the related entities **only when you first access them** (e.g., calling `user.getTodos()`).
- **Example**: JPA loads the `User` record first. A "Proxy" is used for the `todos` list. The database query for todos runs only when they are needed.
- **Default for**: `@OneToMany` and `@ManyToMany` (the "To-Many" side).

```java
@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
private List<Todo> todos;
```

## 3. How Lazy Loading Works: The Hibernate Proxy
When you set a field to `LAZY`, Hibernate doesn't load the real object. Instead, it creates a **Proxy object**.
- **Proxy**: A placeholder object that looks like your entity but is empty.
- **Trigger**: When you call a getter (e.g., `user.getName()`), Hibernate realizes it's a proxy, hits the database, and "initializes" the object.

## 4. Pros and Cons

| Strategy | Pros | Cons |
| :--- | :--- | :--- |
| **Eager** | One round-trip to database. Data is always ready. | Slow startup, memory intensive, "N+1" query risk. |
| **Lazy** | Only fetches what's needed. Super fast initial load. | Risk of `LazyInitializationException` if called outside transaction. |

## 5. Avoiding LazyInitializationException
If you try to access a `LAZY` collection after the session (transaction) is closed, you'll get this common error.
- **Solution**: Always use `@Transactional` in the service layer, or use `@EntityGraph` to fetch data explicitly when needed.

> [!TIP]
> **Best Practice**: Use `FetchType.LAZY` for almost everything. It's much easier to turn on fetching when you need it (using `@EntityGraph`) than to stop an eager load that's slowing down your whole app.
