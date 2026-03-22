# Native Queries

Sometimes, despite how powerful JPQL is, you still need to write **pure SQL**. This is what JPA refers to as a **Native Query**.

## 1. What was used BEFORE?
In manual **JDBC**, developers used absolute pure **SQL**.
- **DB Specific**: SQL was bound to a specific database (PostgreSQL, MySQL, etc.). If you shifted, you had to rewrite queries.
- **Manual Mapping**: Results had to be manually iterated through and mapped to object fields.

## 2. What is used NOW?
In Spring Data JPA, you can use Native SQL queries by setting `nativeQuery = true` in the `@Query` annotation.

### A. When to use Native Queries?
- **Database Specific Functions**: Using a function like `to_tsvector` in PostgreSQL (for full-text search) which doesn't exist in JPQL.
- **Performance**: Some complex reporting queries might perform better in raw SQL.
- **Complex Joins**: If the JPQL join syntax is too limited for your needs.

### B. Example of a Native Query
```java
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query(value = "SELECT * FROM todos WHERE created_at < NOW() - INTERVAL '1 day'", nativeQuery = true)
    List<Todo> findOldTodos();
}
```

Wait, the code above is specific to PostgreSQL's `INTERVAL`. If you move to H2, it'll break.

## 3. Major Differences: JPQL vs Native

| Feature | JPQL | Native Query |
| :--- | :--- | :--- |
| **Object Names** | Uses Java class names (`Todo`). | Uses DB table names (`todos`). |
| **Field Names** | Uses Java field names (`dueDate`). | Uses DB column names (`due_date`). |
| **Paging/Sorting** | Full support. | Limited support (Manual SQL needed). |
| **Portability** | High (Works on any DB). | Low (Locked to one DB). |

## 4. Key Considerations
- **SQL Injection**: Always use parameters (`:name` and `@Param`) to avoid security issues.
- **Result Mapping**: Native queries return `Object[]`. You can map them back to entities, but it's more fragile.
- **Database Portability**: Use Native queries only as a last resort. If you plan correctly, 95% of your queries should be either **Derived Methods** or **JPQL**.

> [!WARNING]
> Native queries don't check for correctness during build time. If you make a typo in a column name, you'll only find out at **runtime** when the query fails.
