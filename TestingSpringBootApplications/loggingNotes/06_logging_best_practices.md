# Logging Best Practices

Logging is a delicate balance of providing enough information to diagnose problems without leaking sensitive data or overwhelming your system.

## 1. Do Not Log Sensitive Data (PII/Secret)
One of the most common and dangerous logging mistakes is logging sensitive data.
-   **Bad Way (PII Security Risk)**: `log.info("Processing order for user: {}, password: {}", username, password);`
-   **Bad Way (PII Security Risk)**: `log.info("User address: {}", user.getAddress());` (Logging Personally Identifiable Information).

### What NOT to log:
-   Passwords, Secrets, API Keys.
-   Credit card numbers.
-   Social Security numbers (SSN) or equivalent.
-   Sensitive personal data.

**The Current Way**: If you MUST log a user ID or order ID, use its non-sensitive primary key. Mask sensitive data if it's absolutely necessary to log for debugging.

## 2. Use Correlation IDs (MDC)
In a big application or microservice environment, one request might go through many different services. 
A **Correlation ID** (Request ID) is a unique ID generated at the start of a request and passed through every service.

> [!NOTE]
> For a more detailed implementation of MDC with real-world examples (filters, async tasks), check **[08_mdc_logging.md](./08_mdc_logging.md)**.

## 3. Don't Log "Success" at ERROR Level
-   **Bad Way**: `log.error("Successfully saved the user!");` (This will trigger fake alerts for operations teams).
-   **Good Way**: Only use the appropriate level. INFO for success; ERROR for unexpected failures.

## 4. Log Exceptions Properly
Always include the exception object (`ex`) as the LAST argument. SLF4J is smart enough to print the stack trace if you do this.
-   **Bad Way**: `log.error("Failed to save user: " + ex.getMessage());` (You lose the stack trace!).
-   **Current Way**: `log.error("Failed to save user", ex);` (SLF4J will log the message AND the full stack trace).

## 5. Be Contextual
Logs should answer the "Who", "What", "Where", and "How" of an issue.
-   **Useless Log**: `log.error("Something went wrong");`
-   **Useful Log**: `log.error("Failed to update status for user ID: {}. Error: {}", userId, ex.getMessage());`

## 6. Key Takeaways
-   **Security first**: Never log secrets or PII.
-   **Context is king**: Use placeholders `{}` and include identifiers.
-   **Traceability**: Use MDC for correlation IDs to track requests across threads/services.
-   **Appropriate levels**: Don't use ERROR for non-error conditions.
