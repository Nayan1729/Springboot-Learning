# Entity vs DTO Separation

## The Core Concept
Modern Spring Boot applications separate the **database model (Entity)** from the **communication model (DTO)**. This ensures that the way we store data in the database doesn't dictate how we expose it to our API consumers.

### 🏢 What is an Entity?
An Entity is a Java class that maps to a database table. It represents the "Source of Truth" in your application. It's used by the Persistence layer (JPA/Hibernate) to persist and retrieve data.

**Characteristics:**
- Mapped to a database table using `@Entity`.
- Contains database-specific information (e.g., Primary Keys, Foreign Keys).
- May have logic for auditing (`@CreatedBy`, `@LastModifiedDate`).
- Likely contains sensitive data that should never be shown to the user.

### 📦 What is a DTO?
A DTO (Data Transfer Object) is a lightweight object designed specifically for carrying data outside the application. It represents the "Public View" of your data.

**Characteristics:**
- No database mapping.
- Focused on the needs of the client (frontend).
- Immutable (often using Java `record`).
- Contains logic for validation (`@NotBlank`, `@Min`, etc.).

---

## ⚖️ Why Not Expose Entities Directly?

| Feature | Pro/Con | Why? |
| :--- | :--- | :--- |
| **Security** | 🛑 **Cons** | Entities might contain sensitive fields (e.g., `userPassword`, `secretHash`). Directly exposing them risks leaking private information. |
| **Breaking Changes** | 🛑 **Cons** | If you change a database column name, the API JSON changes too. This breaks all clients consuming your API. |
| **Over-fetching** | 🛑 **Cons** | You might send a 5MB object when the frontend only needs the `title` and `id`. DTOs keep responses lean. |
| **Logic Leaks** | 🛑 **Cons** | Internal business logic or JPA-specific annotations (like `@OneToMany` with lazy loading) can cause issues when serialized to JSON. |

---

## 🛠 Real-World Example

### Entity Structure (Database Focus)
```java
@Entity
public class Todo {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean softDeleted = false; // 🛑 Internal tracking field
}
```

### DTO Structure (User Focus)
```java
public record TodoResponseDTO(
    Long id,
    String title,
    boolean completed
) {}
// 💎 This DTO is clean and ONLY contains what the user needs.
```

## 🚀 Key Takeaway
Always keep your Entities and DTOs separate. It might feel like "extra work" at first, but it saves your API from breaking whenever your database schema changes and enhances the security of your system.
