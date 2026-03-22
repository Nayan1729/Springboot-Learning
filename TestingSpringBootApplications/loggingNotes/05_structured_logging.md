# Structured Logging for Production

Structured logging is about formatting log messages so they're easy to search, filter, and analyze, especially in distributed systems and production environments.

## 1. Why use JSON for Logging?
-   **Old Way (Plain Text)**: `2024-03-24 10:00:00 [INFO] Order created for user ID 1001`
    -   *Problem*: To find orders for user 1001, you have to use regex or grep. If the message format changes slightly (e.g., "Order for user 1001 created"), your script breaks.
-   **Current Way (JSON)**:
    ```json
    {
      "timestamp": "2024-03-24T10:00:00.123Z",
      "level": "INFO",
      "logger": "com.example.OrderService",
      "message": "Order created",
      "userId": 1001,
      "orderId": "ORD-7788",
      "traceId": "a1b2c3d4"
    }
    ```
    -   *Solution*: The logging system treats this as a database. You can query: `userId: 1001` or `level: ERROR`.

## 2. JSON Libraries in Spring Boot
To output JSON logs, you typically need the **Logstash Logback Encoder** library.

### Maven Dependency:
```xml
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>7.4</version>
</dependency>
```

### Logback Configuration for JSON:
In your `logback-spring.xml`, you would define a `LogstashEncoder`:

```xml
<appender name="JSON_CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <encoder class="net.logstash.logback.encoder.LogstashEncoder">
        <!-- You can customize which fields are included -->
        <includeContext>false</includeContext>
        <timestampPattern>yyyy-MM-dd'T'HH:mm:ss.SSSZ</timestampPattern>
    </encoder>
</appender>
```

Note: This usually requires adding a dependency to your `pom.xml`.

## 4. Why JSON Logging is "The New Standard"
-   **Old Way**: Using `grep` and `awk` commands to search through thousands of text log lines.
-   **Current Way**: Forwarding JSON logs to a centralized log management platform, allowing real-time monitoring and alerting.

## 5. Key Points
-   Use **Console** for local development (human-readable).
-   Use **JSON/File** for production (machine-readable).
-   Logback's `MDC` (Mapped Diagnostic Context) is useful for adding fields like `correlation-id` to every log line without having to pass it manually through every method.
