# DTO (Data Transfer Object) Pattern

## What is a DTO?
A **Data Transfer Object (DTO)** is a simple object used to pass data between various processes or layers of an application. In the context of Spring Boot, it's primarily used to transfer data between the **Controller layer** and the **Client (Frontend)**.

### Why Use DTOs?
Before DTOs, developers would often expose their **Entities** directly to the API. This is now considered an anti-pattern for several reasons:

1.  **Security**: Entities often contain sensitive fields (e.g., password, internal IDs, creation timestamps) that shouldn't be exposed.
2.  **Performance**: Large entities can lead to unnecessary data transfer. DTOs allow you to return only the fields the client needs.
3.  **Loose Coupling**: By separating the database schema (Entity) from the API contract (DTO), you can change one without breaking the other.
4.  **UI Flexibility**: Sometimes the frontend needs data in a format different from how it's stored in the database.

---

## 🔄 Before vs. After DTOs

### 🛑 Before: Exposing Entities Directly
In this approach, the `Todo` entity is used in the request and response.

```java
// Entity
@Entity
public class Todo {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private Long internalSystemId; // 🛑 We don't want to expose this!
}

// Controller
@PostMapping
public Todo createTodo(@RequestBody Todo todo) {
    return todoService.save(todo); // 🛑 Exposes entire entity structure
}
```

### ✅ After: Using DTOs
Now, we use a separate DTO for the API.

```java
// TodoDTO (Safe and Minimal)
public record TodoDTO(
    Long id,
    String title,
    boolean completed
) {}

// Controller
@PostMapping
public TodoDTO createTodo(@RequestBody TodoCreateRequest request) {
    // 1. Receive request DTO
    // 2. Map to Entity in Service
    // 3. Save Entity
    // 4. Map saved Entity back to TodoDTO response
}
```

---

## 🛠 Features of a Good DTO
- **Immutable**: Use Java `record` (introduced in Java 14/16) to ensure the DTO cannot be changed once created.
- **Validation**: Place validation annotations (e.g., `@NotBlank`) on the DTO rather than the entity.
- **Serializable**: DTOs should be easily converted to JSON (which Spring does automatically using Jackson).

## 🚀 Key Takeaway
DTOs act as a "Shield" for your backend, ensuring that only the relevant and safe data is communicated to the outside world.
