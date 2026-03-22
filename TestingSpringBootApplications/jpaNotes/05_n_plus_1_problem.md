# The N+1 Query Problem

This is one of the most common and **deadly performance issues** in Hibernate/JPA. It occurs when your application makes many more database calls than necessary to fetch the data it needs.

## 1. What was used BEFORE?
In manual **JDBC**, developers usually knew they had to write a `JOIN` to get related data in one go.
- **Join All**: One query joined multiple tables at the cost of returning duplicate parent columns.

## 2. What is used NOW?
JPA handles fetching automatically with `LAZY` loading.
- **The Problem**: If you fetch a list of `Users` (1 query) and then for each user, you call `user.getTodos()` in a loop, JPA will execute a separate query for each user's todos.
- **N+1**: If you have N users, it executes **1** query (for the list) + **N** queries (one for each user's todos).

### Example: N+1 in Action
```java
List<User> users = userRepository.findAll(); // 1 Query
for (User user : users) {
    System.out.println(user.getTodos().size()); // N individual queries!
}
```

## 3. Why it happens?
- **Default Lazy Loading**: JPA/Hibernate loads the `User` objects, but not the `Todos`.
- **Proxy Initialization**: When `user.getTodos()` is called, Hibernate sees it's a "Proxy" and hits the DB again to load the data.

## 4. How to detect it?
Check your Spring Boot logs during a search or list operation. If you see thousands of `SELECT` statements for a single API call, you have the N+1 problem.

## 5. Solutions to N+1

### A. JOIN FETCH in JPQL
The most straightforward solution is to tell JPA to join and fetch the related data in a **single query**.

```java
@Query("SELECT u FROM User u JOIN FETCH u.todos")
List<User> findAllSubscribedUsers();
```

### B. @EntityGraph
This is a modern and clean way to define the relations you want to fetch dynamically without writing complex JPQL.

```java
@EntityGraph(attributePaths = {"todos"})
List<User> findAll();
```

### C. Hibernate` @BatchSize`
This is a "band-aid" fix. Instead of fetching 1-by-1, Hibernate will fetch in batches (e.g., 20 at a time).
- **Pro**: Easier than rewriting every query.
- **Con**: Still more than 1 query.

> [!IMPORTANT]
> **Performance Checklist**:
> 1. Use `JOIN FETCH` or `@EntityGraph` when you know you'll need child objects.
> 2. Avoid using `EAGER` loading as a permanent fix (it can cause other performance issues).
> 3. Monitor your SQL logs in development to catch N+1 early.
