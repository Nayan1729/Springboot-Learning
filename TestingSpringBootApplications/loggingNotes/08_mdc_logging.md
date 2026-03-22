# Mastering MDC (Mapped Diagnostic Context) in Logging

MDC is a powerful feature of SLF4J and Logback that allows you to attach contextual information (like User ID or Request ID) to every log message in a thread-safe way.

## 1. What is MDC?
-   **Mapped**: It acts as a `Map<String, String>` where you can store keys and values.
-   **Diagnostic**: It helps diagnose issues by providing "Context".
-   **Context**: It stays with the thread as it moves through different classes and methods.

### How it works:
Instead of passing `requestId` as a parameter to 10 different methods just to log it, you set it ONCE at the start of the request, and SLF4J automatically includes it in EVERY log entry for that thread.

## 2. Using MDC inside a Filter/Interceptor
The best place to set MDC values is in a `Filter` or `Interceptor` to capture the `trace-id` or `user-id` from the incoming request.

```java
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    String correlationId = UUID.randomUUID().toString();
    
    // 1. Put value into MDC
    MDC.put("correlationId", correlationId);
    
    try {
        chain.doFilter(request, response);
    } finally {
        // 2. CRITICAL: Always clear it! 
        // MDC is ThreadLocal, so if threads are reused (like in Tomcat), 
        // the old data could leak to the next request.
        MDC.clear(); 
    }
}
```

## 3. Configuring Logback to show MDC values
Even if you put data in MDC, it won't show up in your logs unless you tell Logback where to display it using the `%X{keyName}` placeholder.

### In `logback-spring.xml`:
```xml
<pattern>%d{HH:mm:ss} [%thread] %-5level %logger{36} [%X{correlationId}] - %msg%n</pattern>
```

**Output example**:
`12:00:01 [http-nio-8080-exec-1] INFO OrderService [a1b2-c3d4-e5f6] - Processing order...`

## 4. Why MDC is "The Modern Way"?
| Method | Complexity | Maintainability |
| :--- | :--- | :--- |
| **Manual Parameters (Old)** | High (Every method needs the ID) | Poor (Breaks if signature changes) |
| **MDC (Current)** | Low (Set once per request) | High (Invisible to business logic) |

## 5. Important: MDC and Asynchronous Tasks (`@Async`)
Since MDC is based on `ThreadLocal`, it is **NOT** automatically transferred to child threads when you use `@Async` or `ExecutorService`.

**Solution**: You must use a "Task Decorator" or manually copy the MDC map to the new thread.
```java
Map<String, String> contextMap = MDC.getCopyOfContextMap();
executor.submit(() -> {
    MDC.setContextMap(contextMap);
    try {
        // Business logic here
    } finally {
        MDC.clear();
    }
});
```

## 6. Real-World Use Cases
1.  **Distributed Tracing**: Carrying a `trace-id` (Zipkin/Brave) across multiple microservices.
2.  **User Identity**: Tracking which `userId` performed which actions during a session.
3.  **Tenant ID**: In multi-tenant applications, logging the `tenantId` to isolate logs.
