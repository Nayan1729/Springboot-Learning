# MapStruct for Object Mapping

## What is MapStruct?
MapStruct is an open-source Java annotation processor that generates code for Java bean mappings. It's used in Spring Boot applications to easily and efficiently convert between **Entities** and **DTOs**.

### Why Use MapStruct?
Before MapStruct, developers would often write manual conversion code (Boilerplate!), which is tedious and error-prone.

---

## 🔄 Before vs. After MapStruct

### 🛑 Before: Manual Mapping Logic
In this approach, you manually map each field from an entity to a DTO in a service or mapper class.

```java
// Manual Conversion in TodoService
public TodoDto toDto(Todo todo) {
    if (todo == null) return null;
    TodoDto dto = new TodoDto();
    dto.setId(todo.getId());
    dto.setTitle(todo.getTitle());
    dto.setCompleted(todo.isCompleted());
    return dto; // 🛑 Tedious, error-prone, and gets worse as fields grow!
}
```

### ✅ After: Using MapStruct
Now, you just define an interface and MapStruct generates the implementation for you at compile time.

```java
// TodoMapper Interface
@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    TodoDto toDto(Todo todo);
    Todo toEntity(TodoDto todoDto);
}

// MapStruct will generate the implementation in the target folder.
```

---

## 🛠 Features of MapStruct
1.  **Fast Execution**: Generated code is pure Java and doesn't use reflection, making it extremely fast.
2.  **Type Safety**: MapStruct performs compile-time checks, ensuring that common errors (like type mismatches) are caught early.
3.  **Clean Code**: Moves mapping logic out of your business services, keeping them clean and focused.
4.  **Flexible Mapping**: You can map fields with different names using `@Mapping`.

---

## 🛠 MapStruct Example: Different Field Names
Sometimes an entity has different field names than a DTO. MapStruct handles this easily:

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "firstName", target = "fName") // 💎 Different names? No problem!
    UserDto toDto(User user);
}
```

---

## 🚀 Key Takeaway
MapStruct is the industry standard for mapping between Entities and DTOs in Spring Boot. It reduces boilerplate code, improves performance, and ensures type-safe conversions. Always prefer MapStruct over manual mapping.
