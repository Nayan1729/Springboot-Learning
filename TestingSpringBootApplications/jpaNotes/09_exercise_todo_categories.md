# Exercise 1: Implement Todo with Categories (ManyToMany)

This exercise demonstrates how to implement a many-to-many relationship in Spring Boot with JPA.

## Goal
- Create a `Category` entity.
- Map it to the `Todo` entity with a Many-to-Many relationship.
- Create a `todo_categories` join table.
- Implement the logic to save and retrieve categories with todos.

## 1. Before implementation
Understand the concept of a **Join Table**.
- In a Many-to-Many relationship, you cannot store `category_id` in the `todos` table (because one todo can have multiple categories) and you can't store `todo_id` in the `categories` table (because one category can have multiple todos).
- **The Solution**: A third table called `todo_categories` that stores the `todo_id` and `category_id` pairs.

## 2. Java Implementation Details

### A. Category Entity
```java
@Entity
@Table(name = "categories")
@Getter @Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore // Prevent infinite recursion in JSON
    private Set<Todo> todos = new HashSet<>();
}
```

### B. Updating Todo Entity
```java
@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
@JoinTable(
    name = "todo_categories",
    joinColumns = @JoinColumn(name = "todo_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
)
private Set<Category> categories = new HashSet<>();
```

## 3. Step-by-Step Exercise Implementation
1. **Create the Entity**: Add `Category.java`.
2. **Update Todo**: Add the `@ManyToMany` relation in `Todo.java`.
3. **Repository**: Create `CategoryRepository`.
4. **Service Logic**: Implement methods to add a category to a todo.
5. **Testing**: Verify that when you save a Todo with a Category, the join table is correctly populated.

## 4. Key Takeaways
- Use **Set** instead of **List** for better performance with Many-to-Many relations.
- **CascadeType.ALL** might be dangerous here as deleting a Todo would delete shared Categories unexpectedly. Use `PERSIST` and `MERGE` instead.
- Use `@JsonIgnore` or DTOs to avoid Infinite Recursion if you return your entities directly in a REST controller.

> [!TIP]
> **Pro Tip**: In a real production app, it is often better to break a Many-to-Many relationship into two One-to-Many relationships with a Join Entity if you need to store extra information in the join table (like `assigned_at` or `is_primary`).
