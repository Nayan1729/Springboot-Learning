# Database Migrations with Flyway or Liquibase

This is about managing changes to your database schema (creating tables, adding columns, changing types) in a versioned and repeatable way.

## 1. What was used BEFORE?
Before Flyway/Liquibase, schema management was handled with **Manual Scripts**.
- **The "SQL File" Problem**: Developers used a single `schema.sql` file or shared `.sql` scripts over email/Teams.
- **Inconsistency**: DB schemes were often different between Development, Testing, and Production environments.
- **Merge Conflicts**: Two developers modifying the same `schema.sql` at the same time.
- **Manual Execution**: You had to manually run each SQL script on the database server.

## 2. What is used NOW?
Modern applications use **Versioned Database Migrations**.

### A. How it works
You store your SQL scripts (migrations) in your code repository (`src/main/resources/db/migration`). Every migration has a version number.
1. When the app starts, Flyway/Liquibase checks the current DB version.
2. It looks for new migration scripts in the code.
3. It executes the missing scripts and updates the `flyway_schema_history` table.

## 3. Flyway vs. Liquibase

| Feature | Flyway | Liquibase |
| :--- | :--- | :--- |
| **Philosophy** | "SQL-first". You write pure SQL. | "XML/YAML/JSON-first". You write DB-independent XML. |
| **Complexity** | Simple, easy to learn. | More complex, higher learning curve. |
| **Flexibility** | Great for simple SQL changes. | Better for complex DB refactors (rename columns, etc.). |
| **Portability** | Lower (pure SQL). | Higher (XML converts to any DB SQL). |

## 4. Why use Migrations?
- **Version Control**: Every schema change is now in your Git history.
- **Automatic Deployment**: No need to manually run SQL on servers.
- **Team Collaboration**: If a teammate adds a new table, you just fetch the code and run the app—Flyway handles the DB update for you.
- **Repeatability**: Guarantee that Dev, Staging, and Prod environments are identical.

## 5. Setting up Flyway
1. Add the Flyway dependency in `pom.xml`.
2. Create migration files in `src/main/resources/db/migration` with the format `V1__init_schema.sql`, `V2__add_category_table.sql`.

## 6. Summary: The Final Evolution
The industry has moved from **Manual DB Administration** to **Database as Code**. We should treat our database schema with the same care and versioning as our Java source code.

> [!TIP]
> **Pro Tip**: Use **Flyway** for most Spring Boot projects. It's much simpler than Liquibase and since most Java developers are comfortable with SQL, it's faster to implement and easier to maintain.
