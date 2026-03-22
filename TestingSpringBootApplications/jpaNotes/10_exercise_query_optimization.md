# Exercise 2: Optimize Queries to Avoid N+1

This exercise demonstrates how to find and fix the N+1 problem in your Spring Boot application using `@EntityGraph`.

## Goal
- Detect an N+1 query problem.
- List all Todos including their Categories and Users in a single query.
- Use `@EntityGraph` for performance optimization.

## 1. Before implementation
Understand the "Silent Killer":
- Calling `todoRepository.findAll()` only fetches the `todos`.
- If you access `todo.getCategories()` in a loop, it makes a database call for **each** todo record.
- If you have 100 todos, it makes 101 database queries.

## 2. Java Implementation Details

### A. The N+1 Trigger (Avoid)
```java
// Service call
List<Todo> todos = todoRepository.findAll();
// Mapping to DTO or calling .size() will trigger N+1
todos.forEach(todo -> todo.getCategories().size());
```

### B. The Optimization (@EntityGraph)
```java
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Override
    @EntityGraph(attributePaths = {"categories", "user"})
    List<Todo> findAll();
}
```

## 3. Step-by-Step Exercise Implementation
1. **Enable SQL Logging**: Set `logging.level.org.hibernate.SQL=DEBUG` in `application.properties`.
2. **Reproduce N+1**: Run the app and get all todos. Look at the console. You should see multiple `SELECT` statements for categories.
3. **Apply @EntityGraph**: Add the annotation to your Repository.
4. **Verify**: Restart and search again. You should see a single SQL `LEFT JOIN` statement instead of multiple ones.

## 4. Key Takeaways
- **Performance**: A single query is almost always faster than many small queries.
- **Maintainability**: Using `@EntityGraph` keeps your code clean and avoids writing complex SQL.
- **Selectivity**: You can create custom repository methods with different `@EntityGraph` definitions for different use cases.

> [!IMPORTANT]
> **Performance Checklist**:
> 1. Use `JOIN FETCH` or `@EntityGraph` when you know you'll need child objects.
> 2. Avoid using `EAGER` loading as a permanent fix (it can cause other performance issues).
> 3. Monitor your SQL logs in development to catch N+1 early.
