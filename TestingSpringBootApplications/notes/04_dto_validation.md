# Validation on DTOs

## What is Validation?
Validation is the process of ensuring that data entered into the system meets specific rules (e.g., a field is not blank, a number is within a certain range). In Spring Boot, we use **Bean Validation (Hibernate Validator)** for this.

### Why Use Validation on DTOs?
Validation on DTOs allows you to catch errors at the API level before they reach your business logic or database. This is a common best practice in modern Spring Boot applications.

---

## 🔄 Before vs. After Validation on DTOs

### 🛑 Before: Validation in Business Logic
In this approach, you manually check each field in your service or controller.

```java
// Manual Validation in TodoService
if (todo.getTitle() == null || todo.getTitle().isEmpty()) {
    throw new IllegalArgumentException("Title cannot be empty!");
}
```

### ✅ After: Using Bean Validation on DTOs
Now, you use annotations like `@NotBlank`, `@Size`, etc., directly on your DTO.

```java
// TodoDto (Safe and Validated)
public record TodoDto(
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2, max = 100)
    String title,

    @NotNull(message = "Completion status is required")
    boolean completed
) {}

// Controller
@PostMapping
public TodoDto createTodo(@Valid @RequestBody TodoDto todoDto) {
    // 💎 Spring will validate the DTO before even calling this method!
}
```

---

## 🛠 Common Validation Annotations
1.  **@NotNull**: Ensures the field is not null.
2.  **@NotBlank**: Ensures a string is not null and contains at least one non-whitespace character.
3.  **@Size**: Ensures a string or collection has a size within a certain range.
4.  **@Email**: Ensures a string is a valid email address.
5.  **@Min/@Max**: Ensures a number is within a certain range.

---

## 🛠 Handling Validation Errors
When a validation fails, Spring throws a `MethodArgumentNotValidException`. You can catch this globally using a `@ControllerAdvice` to return a clean error response to the client.

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Return a customized error message (e.g., Map<String, String> of field errors)
    }
}
```

---

## 🚀 Key Takeaway
Validation on DTOs is the first line of defense in your application. It ensures that only clean and valid data enters your system, reducing the chance of bugs and keeping your business logic focused on business rules.
