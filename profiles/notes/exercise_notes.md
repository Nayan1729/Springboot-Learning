# Spring Boot Configuration Exercise Notes

This document describes the steps taken to implement environment-specific configuration and profiles in the `profiles` project.

## 1. Creating Multiple Profiles

We created three environment-specific configuration files in `src/main/resources`:
- `application-dev.yaml`: Development settings (H2 in-memory DB, debug logging).
- `application-test.yaml`: Testing settings (H2 in-memory DB, info logging).
- `application-prod.yaml`: Production settings (PostgreSQL DB, error logging, placeholders for environment variables).

By default, the active profile is set to `dev` in the main `application.yaml`:
```yaml
spring:
  profiles:
    active: dev
```

## 2. Externalizing Configuration

We used placeholders in `application-prod.yaml` to allow sensitive information to be provided via environment variables:
```yaml
spring:
  datasource:
    username: ${PROD_DB_USER:admin}
    password: ${PROD_DB_PASSWORD:secret}
app:
  api-key: ${PROD_API_KEY:DEFAULT_PROD_KEY}
```
If `PROD_API_KEY` is set as an environment variable, it will override the default `DEFAULT_PROD_KEY`.

## 3. Type-Safe Configuration with `@ConfigurationProperties`

Instead of using individual `@Value` annotations, we created a central configuration bean `AppConfig` to group related properties.

```java
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String apiKey;
    private List<String> features;
    private LoggingConfig logging;

    @Data
    public static class LoggingConfig {
        private String level;
    }
}
```

The `@ConfigurationPropertiesScan` annotation in `ProfilesApplication` ensures this class is automatically detected and populated.

## 4. Profile-Specific Beans

We demonstrated how to create different bean implementations based on the active profile by defining a `GreetingService` interface and multiple implementations marked with `@Profile`.

| Profile | Implementation | Output |
| :--- | :--- | :--- |
| `dev` | `DevGreetingService` | "Hello from the DEV environment!" |
| `prod` | `ProdGreetingService` | "Hello from the PROD environment! Data is secured." |
| `test` | `TestGreetingService` | "Unit testing in progress..." |

## 5. Testing and Verification

To verify the configuration, we created a `ProfileController` with an `/info` endpoint. This endpoint returns:
- The greeting from the active profile's `GreetingService`.
- The database URL being used.
- All properties mapped into `AppConfig`.

### Running with a Specific Profile
To run the application with the production profile, use the following command:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

To override properties using environment variables:
```bash
$env:PROD_API_KEY="SECRET-KEY-999"; mvn spring-boot:run -Dspring-boot.run.profiles=prod
```
(Note: The above syntax is for PowerShell on Windows).
