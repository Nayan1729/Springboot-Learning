# Understanding Log Levels (TRACE, DEBUG, INFO, WARN, ERROR)

Logging levels are used to categorize and filter log messages based on their severity or importance. 
By setting a "Global" or "Package-Specific" log level, you can filter what's outputted to the console or logs.

## 1. The Log Hierarchy
The log levels follow an order of severity (from least to most critical):
-   **TRACE** (Lowest)
-   **DEBUG**
-   **INFO** (Default in Spring Boot)
-   **WARN**
-   **ERROR** (Highest)

**The Higher the Level**, the less noise you get. If you set the level to `INFO`, you get `INFO`, `WARN`, and `ERROR` logs. You won't see `DEBUG` and `TRACE`.

## 2. When to Use Each Level?

### TRACE (The "Micro" View)
-   Used for the most detailed information (e.g., individual SQL queries, method entry/exit).
-   **Usually only enabled** when you're debugging a very difficult issue in a specific package.
-   **Example**: `log.trace("Entering method calculateTotal with args: {}, {}", a, b);`

### DEBUG (The "Developer" View)
-   Used by developers to diagnose issues during development.
-   Contains high-level implementation details but NOT everything that TRACE does.
-   **Example**: `log.debug("Found {} users in database", users.size());`

### INFO (The "Operations" View)
-   The default level for most applications.
-   Used for significant application milestones (e.g., "Service started", "User logged in", "Job completed").
-   **Example**: `log.info("Finished processing order with ID: {}", orderId);`

### WARN (The "Warning" View)
-   Used for unusual or unexpected events that aren't errors but could lead to issues.
-   The application still works, but something weird happened.
-   **Example**: `log.warn("Retrying the connection to the external API... attempt 2");`

### ERROR (The "System failure" View)
-   Used when something actually goes wrong and code cannot proceed correctly.
-   Exceptions are typically logged at this level.
-   **Example**: `log.error("Failed to process transaction. Error: {}", ex.getMessage(), ex);`

## 3. Configuring Log Levels in Spring Boot

### In `application.properties`:
```properties
logging.level.root=INFO
logging.level.com.example.testingspringbootapplications=DEBUG
```

### In `application.yml`:
```yaml
logging:
  level:
    root: INFO
    com.example.testingspringbootapplications: DEBUG
```

## 4. Why Levels Matter?
-   **Old Way**: Not using levels results in "Log Pollution" where your console is filled with useless info, making it hard to find real errors.
-   **Current Way**: Using levels allows you to keep logs clean in production (`INFO`) while being able to dive into details (`DEBUG`) when needed, without changing code.
