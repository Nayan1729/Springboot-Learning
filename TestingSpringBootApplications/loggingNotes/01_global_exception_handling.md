# Global Exception Handling in Spring Boot

Centralized exception handling is a crucial aspect of building robust REST APIs. It ensures that your application provides consistent, user-friendly error responses instead of exposing internal stack traces or technical details.

## 1. Why use Global Exception Handling?
Before centralized handling, developers would often:
-   **Old Way**: Use `try-catch` blocks in every controller method.
-   **Old Way**: Catch generic `Exception` and return manual response objects.
-   **Result**: Inconsistent error formats across the API, repetitive code, and difficult maintenance.

**The Modern Way**: Use `@ControllerAdvice` (or `@RestControllerAdvice`) to handle exceptions across the entire application in one place.

## 2. Key Annotations

### `@RestControllerAdvice`
This annotation is a specialization of `@Component` that allows you to write centralized exception-handling code. It combines `@ControllerAdvice` and `@ResponseBody`.
-   **Before**: You had to annotate each error handler with `@ResponseBody` if you wanted to return a JSON response.
-   **Now**: `@RestControllerAdvice` automatically handles JSON serialization for all methods within it.

### `@ExceptionHandler`
This annotation is used on methods within a `@RestControllerAdvice` class to specify which exception type it handles.
```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
    // ... logic
}
```

## 3. Creating a Consistent Error Response
It is best practice to always return a structured object so that the client (frontend, mobile app, etc.) knows exactly how to parse the error.

### Example: `ErrorResponse` DTO
```java
@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> fieldErrors; // For validation failures
}
```

## 4. Implementation Details

### Handling Validation Errors (`MethodArgumentNotValidException`)
When a request body fails `@Valid` constraints, Spring throws `MethodArgumentNotValidException`. 
We can extract field-specific errors and return them to the client.

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorResponse> handleValidationExceptions(
        MethodArgumentNotValidException ex, WebRequest request) {
    
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });

    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Bad Request")
            .message("Validation failed")
            .fieldErrors(errors)
            .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
}
```

## 5. Benefits of This Approach
-   **Separation of Concerns**: Controllers focus on business logic, not error handling.
-   **Consistency**: Every error response follows the same structure.
-   **Maintainability**: Adding support for a new exception type only requires one new method in the global handler.
