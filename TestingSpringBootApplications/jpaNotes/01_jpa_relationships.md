# JPA Relationships (OneToOne, OneToMany, ManyToMany)

In JPA (Java Persistence API), relationships describe how your entity classes (Java objects) are connected in the same way your database tables are connected via Foreign Keys.

## 1. What was used BEFORE?
Before JPA and modern ORMs, developers used **JDBC (Java Database Connectivity)**.
- **Manual Mapping**: You had to manually map database results (`ResultSet`) to Java objects.
- **Manual Foreign Keys**: You stored the `long user_id` in your Java `Order` object and manually queried the `User` table when you needed the user info.
- **Complex Joins**: Every time you needed related data, you had to write complex SQL `JOIN` statements.
- **Consistency Issues**: If a parent record was deleted, you had to manually handle the deletion of child records in the code or depend solely on DB-level `ON DELETE CASCADE`.

## 2. What is used NOW?
JPA uses **Annotations** to define relationships. It treats related tables as **Objects** rather than just IDs.

### A. One-to-One (@OneToOne)
One record in Table A is associated with exactly one record in Table B.
- **Example**: A `User` has one `UserProfile`.

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile profile;
}

@Entity
public class UserProfile {
    @Id
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id") // This creates the Foreign Key column
    private User user;
}
```

### B. One-to-Many / Many-to-One (@OneToMany / @ManyToOne)
This is the most common relationship. One record in Table A can have many records in Table B.
- **Example**: One `User` can have multiple `Todo` items.

```java
@Entity
public class User {
    @OneToMany(mappedBy = "user") // Bidirectional: "user" refers to field in Todo
    private List<Todo> todos;
}

@Entity
public class Todo {
    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign Key is in the 'many' side
    private User user;
}
```

### C. Many-to-Many (@ManyToMany)
Many records in Table A are associated with many records in Table B. This requires a **Join Table**.
- **Example**: A `Todo` can have many `Categories`, and a `Category` can belong to many `Todos`.

```java
@Entity
public class Todo {
    @ManyToMany
    @JoinTable(
        name = "todo_category",
        joinColumns = @JoinColumn(name = "todo_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
}

@Entity
public class Category {
    @ManyToMany(mappedBy = "categories")
    private Set<Todo> todos;
}
```

## 3. Key Concepts
- **Owning Side**: The side that holds the Foreign Key (usually the `@ManyToOne` side or the one without `mappedBy`).
- **Cascade**: Determines if operations (save, delete) should propagate to related entities (e.g., `CascadeType.ALL`, `REMOVE`).
- **Orphan Removal**: If a child is removed from the parent's collection, it is also deleted from the database.

> [!TIP]
> Always use `Set` instead of `List` for `@ManyToMany` to avoid performance issues during removal and to ensure uniqueness.
