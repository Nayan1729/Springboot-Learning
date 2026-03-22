# JPQL (Java Persistence Query Language)

JPQL is a database-independent query language that is used by JPA. It is very similar to SQL, but it operates on **Objects/Entities** rather than **Tables**.

## 1. What was used BEFORE?
Before JPQL, developers used **Statement** or `PreparedStatement` in **JDBC**.
- **DB Specific**: SQL was bound to a specific database (PostgreSQL, MySQL, etc.). If you shifted, you had to rewrite queries.
- **Manual Mapping**: Results had to be manually iterated through and mapped to object fields.
- **Boilerplate**: A simple query involved 10-20 lines of Java code just for setup and error handling.

## 2. What is used NOW?
JPQL is the standard for Spring Data JPA and Hibernate. It feels like SQL but is **Object-Oriented**.

### A. JPQL Syntax Example
```sql
-- SQL
SELECT * FROM users WHERE name = 'John';

-- JPQL
SELECT u FROM User u WHERE u.name = 'John';
```
In JPQL, you select the entity `u`, not `*` columns.

### B. Using @Query annotation
In Spring Boot, you can write JPQL queries directly in your Repository interfaces.

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Selecting based on object field names
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);
    
    // Aggregations works like SQL
    @Query("SELECT COUNT(u) FROM User u")
    long countAllUsers();
}
```

## 3. Benefits of JPQL
- **Database Independent**: Since JPQL is parsed by Hibernate/JPA, it will adapt the generated SQL for whichever database you are using (PostgreSQL, MySQL, Oracle).
- **Type Safety**: It maps your database directly to your Java classes.
- **Joins in JPQL**: You don't join on tables. You join on the relationship defined in your classes.

```java
// Joining Todos with User object
@Query("SELECT t FROM Todo t JOIN t.user u WHERE u.name = :userName")
List<Todo> findTodosByUserName(@Param("userName") String userName);
```

## 4. Derived Query Methods
Spring Data JPA can even "guess" the query based on method names. This is the **most modern** way of writing simple queries.

```java
// No SQL/JPQL needed! Spring generates it for you.
List<Todo> findByStatusAndTitleContaining(String status, String title);
```

> [!IMPORTANT]
> Use **Derived Query Methods** for simple queries. Switch to **JPQL** only when the logic gets complex (e.g., joins, multiple conditions). Use **Native Queries** only for database-specific features.
