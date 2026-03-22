# Exercise: Implementing Logging and Exception Handling

In this exercise, we have enhanced our Todo Application by implementing a robust logging strategy and a centralized exception handling mechanism using a custom business exception.

## 1. Task: Custom Business Exception
We created `ResourceNotFoundException` to handle cases where a Todo ID does not exist in the database.

**Why?**
Instead of returning a generic 404 from the controller using `Optional.orElse`, we throw a specific business exception from the service. This allows us to centralize the "Not Found" logic in one place.

## 2. Task: Global Exception Handler Enhancement
We updated `GlobalExceptionHandler` to catch our new `ResourceNotFoundException`.

**Implementation details**:
-   Used `@ExceptionHandler(ResourceNotFoundException.class)`.
-   Returned a consistent `ErrorResponse` DTO.
-   Added **Logging** (`log.warn`) so that every time a resource is not found, it's recorded in our logs for monitoring.

## 3. Task: meaningful Logging throughout Application
We added logging to `TodoService` and `TodoController` using Lombok's `@Slf4j`.

### Service Layer Logging:
-   **INFO Level**: Used for major actions like `createTodo`, `updateTodo`, and `deleteTodo`.
-   **DEBUG Level**: Used for data-retrieval actions like `getAllTodos` and `getTodoById`.
-   **WARN Level**: (In the exception handler) for business violations.

### Controller Layer Logging:
-   Added `log.info` for every entry point to track the flow of REST requests.

## 4. How to Verify
1.  **Try to Get a non-existent Todo**:
    -   Request: `GET /api/v1/todos/999`
    -   Expected Result: 
        -   HTTP Status: 404
        -   Body: Our custom JSON `ErrorResponse`
        -   Logs: `WARN ... Resource not found: Todo not found with id: 999`
2.  **Create a Todo**:
    -   Logs: `INFO ... Creating a new todo: Finish Notes`
    -   Logs: `INFO ... Todo created with ID: 10`

## 5. Summary of Improvements
-   **Traceability**: We can now follow a request's path through the logs.
-   **Professionalism**: The API returns clean, structured errors instead of default Spring error pages.
-   **Maintainability**: Business logic is separated from error formatting logic.
