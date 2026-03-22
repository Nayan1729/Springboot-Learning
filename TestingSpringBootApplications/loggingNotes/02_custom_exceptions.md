# Custom Business Exceptions in Spring Boot

While Java and Spring provide many built-in exceptions, creating custom, domain-specific exceptions makes your code cleaner, more readable, and easier to handle in a centralized way.

## 1. Why Create Custom Exceptions?
-   **Old Way (General Exceptions)**: Throwing generic exceptions like `RuntimeException`, `IllegalArgumentException`, or `NullPointerException` with a manual message.
-   **Current Way (Domain-Specific)**: Throwing `ResourceNotFoundException`, `InsufficientFundsException`, or `DuplicateResourceException`.

### Benefits:
-   **Meaning**: They indicate exactly what went wrong in terms of the business domain.
-   **Handling**: You can catch specific custom exceptions in the global exception handler (e.g., all `ResourceNotFoundException` return a 404).
-   **Cleanliness**: No need to parse error messages via `ex.getMessage().contains("User not found")`.

## 2. Using `@ResponseStatus` vs Centralized Handling

### `@ResponseStatus` (Static, Simple)
You can annotate a custom exception with `@ResponseStatus` to automatically map it to an HTTP code.
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```
**Limitation**: This approach can't return a custom error body (like the `ErrorResponse` DTO we discussed earlier). It only sets the status code.

### Centralized Exception Handler (Flexible, Recommended)
This is the approach we implemented in `GlobalExceptionHandler`. We define our custom exception and then add a specific handler for it.

```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
        ResourceNotFoundException ex, WebRequest request) {

    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error("Resource Not Found")
            .message(ex.getMessage())
            .path(request.getDescription(false))
            .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
}
```

## 3. Best Practices for Custom Exceptions
-   **Extend `RuntimeException`**: Most business exceptions are unchecked. This keeps method signatures clean by not requiring `throws` clauses everywhere.
-   **Provide Multiple Constructors**: It's good practice to provide constructors that accept a message and/or a cause.
-   **Immutability**: Exceptions should generally be immutable.

## 4. Exercise Task: Implementation in our App
We will create a `ResourceNotFoundException` and use it in our `TodoService` when a requested Todo ID doesn't exist.

### `ResourceNotFoundException.java`
```java
package com.example.testingspringbootapplications;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```
***
***
*(Notes for next topics: Logging intro, Levels, etc.)*
