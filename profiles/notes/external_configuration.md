# External Configuration in Spring Boot

Spring Boot allows you to externalize your configuration so that you can work with the same application code in different environments. You can use properties files, YAML files, environment variables, and command-line arguments to externalize configuration.

## Ways to Externalize Configuration

### 1. `application.properties` and `application.yaml`
By default, Spring Boot picks up configuration from `application.properties` or `application.yaml` located in:
- `/config` subdirectory of the current directory
- The current directory
- A classpath `/config` package
- The classpath root (default `src/main/resources`)

### 2. `@Value` Annotation
The `@Value` annotation can be used to inject properties directly into your beans.

```java
@Component
public class MyBean {

    @Value("${my.property}")
    private String name;

    @Value("${my.secret:default-secret}")
    private String secret; // uses default value if not found
}
```

### 3. `@ConfigurationProperties` (Type-safe Configuration)
For more complex configuration or to group related properties together, Spring Boot provides `@ConfigurationProperties`.

```java
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private String name;
    private String version;
    private List<String> servers;

    // Getters and Setters are required
}
```

## Spring Profiles

Spring Profiles provide a way to segregate parts of your application configuration and make it only available in certain environments.

### 1. Activating Profiles
Profiles can be activated in several ways:
- In `application.properties`/`yaml`: `spring.profiles.active=dev`
- Via command line: `--spring.profiles.active=prod`
- Via environment variables: `SPRING_PROFILES_ACTIVE=test`

### 2. Profile-Specific Configuration Files
You can define separate files for each profile:
- `application-dev.yaml`
- `application-test.yaml`
- `application-prod.yaml`

Spring Boot will load the default `application.yaml` and then override it with the active profile's configuration.

### 3. Environment-Specific Beans with `@Profile`
You can mark beans to be only created when a specific profile is active.

```java
@Configuration
public class MyConfig {

    @Bean
    @Profile("dev")
    public MyService devService() {
        return new DevService();
    }

    @Bean
    @Profile("prod")
    public MyService prodService() {
        return new ProdService();
    }
}
```

## Configuration Precedence

When properties are defined in multiple locations, Spring Boot follows a specific order of precedence (from highest to lowest):
1.  **Devtools global settings** (in `~/.spring-boot-devtools.properties`).
2.  **`@TestPropertySource`** annotations on your tests.
3.  **`properties` attribute** on your tests.
4.  **Command-line arguments**.
5.  **Properties from `SPRING_APPLICATION_JSON`** (inline JSON embedded in an environment variable or system property).
6.  **`ServletConfig` init parameters**.
7.  **`ServletContext` init parameters**.
8.  **JNDI attributes** from `java:comp/env`.
9.  **Java System properties** (`System.getProperties()`).
10. **OS environment variables**.
11. **`RandomValuePropertySource`** that has properties only in `random.*`.
12. **Profile-specific application properties** outside of your packaged jar (`application-{profile}.properties` and YAML variants).
13. **Profile-specific application properties** packaged inside your jar (`application-{profile}.properties` and YAML variants).
14. **Application properties** outside of your packaged jar (`application.properties` and YAML variants).
15. **Application properties** packaged inside your jar (`application.properties` and YAML variants).
16. **`@PropertySource`** annotations on your `@Configuration` classes.
17. **Default properties** (specified by setting `SpringApplication.setDefaultProperties`).

## Best Practices
- **Use YAML for complex configurations** due to its hierarchical structure.
- **Prefer `@ConfigurationProperties` over `@Value`** as it provides type safety and supports validation.
- **Don't store sensitive information** (like passwords or API keys) in properties files; use environment variables or secret management tools.
- **Use Profiles to switch between environments** (dev, test, prod).
- **Keep common configuration in `application.yaml`** and only profile-specific overrides in `application-{profile}.yaml`.
