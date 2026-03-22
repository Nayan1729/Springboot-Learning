# @EntityGraph for Query Optimization

This is a modern way to dynamically control which entities should be fetched along with the parent object. It effectively solves the **N+1 query problem**.

## 1. What was used BEFORE?
Before `@EntityGraph` was introduced in JPA 2.1, developers had to:
- Use **JPQL JOIN FETCH**: Writing long JPQL strings just to fetch a related field.
- Use **Criteria API**: Very verbose and complex code.
- Use **Hibernate Fetch Profiles**: A legacy Hibernate-specific feature.

## 2. What is used NOW?
JPA's **Entity Graphs** let you define groups of related entities to be fetched in a single query.

### A. Dynamic Fetching using @EntityGraph
The simplest way to use it in Spring Data JPA is on your Repository methods.

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Fetch the 'todos' and 'profile' fields in the same query.
    @EntityGraph(attributePaths = {"todos", "profile"})
    List<User> findAll();
}
```

### B. Why is it better than JPQL?
- **Cleaner Code**: No need to write complex SQL/JPQL. Just a simple annotation.
- **Dynamic Usage**: You can have one method that fetches everything and another method that fetches only the parent entity.

### C. Different Types of Fetching
There are two "modes" for Entity Graphs:
1. **FETCH** (Default): All attributes listed in the graph are treated as `EAGER`, and all others as `LAZY`.
2. **LOAD**: Attributes in the graph are `EAGER`, and others maintain their default fetch type.

## 3. Benefits of Query optimization with @EntityGraph
- **Selective Fetching**: Load only what's needed for a specific business use case.
- **Single SQL Query**: Replaces N+1 queries with a single `LEFT JOIN` query.
- **Readable Logic**: The logic of what data you need is clearly separated from how the query is written.

### Example: Optimizing Todo API
If you want to list Todos along with their Categories and the User who created them:

```java
@EntityGraph(attributePaths = {"categories", "user"})
Optional<Todo> findWithAssociationsById(Long id);
```

## 4. Summary
Entity Graphs are the **industry standard** for handling lazy relations. It allows you to keep your base entity fields as `LAZY` (best for performance) and only force an `EAGER` load when you actually need it.

> [!TIP]
> **Pro Tip**: Use `@EntityGraph` as your primary tool for solving N+1. It's much safer than `@Query("JOIN FETCH ...")` because JPA will handle the join logic and deduplication for you.
