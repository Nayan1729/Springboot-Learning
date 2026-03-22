# PATCH for Partial Updates

## What is PATCH?
The HTTP PATCH method is used to perform a **partial update** on a resource. Unlike PUT, which replaces the entire resource, PATCH only updates the fields provided in the request body.

### Why Use PATCH?
PATCH is more efficient than PUT when updating large resources because you only send the fields that changed. This reduces data transfer and improves performance.

---

## 🔄 Before vs. After PATCH

### 🛑 Before: PUT for Updates
In this approach, you send the full object back to the server to update it.

```java
// PUT Request (Updates entire object)
PUT /todos/1
{
    "title": "New Title",
    "description": "New Description",
    "completed": true
}
```

### ✅ After: PATCH for Partial Updates
Now, you only send the fields you want to update.

```java
// PATCH Request (Partial Update)
PATCH /todos/1
{
    "completed": true
}
```

---

## 🛠 Implementing PATCH in Spring Boot
There are several ways to implement PATCH in Spring Boot. One common way is to use a Map or a separate DTO:

### Method 1: Using a Map
```java
// Controller
@PatchMapping("/{id}")
public ResponseEntity<TodoDto> patchTodo(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
    // 1. Find existing Todo
    // 2. Map fields to Todo entity
    // 3. Save updated Todo
    // 4. Return updated TodoDto
}
```

### Method 2: Using Reflection (Common Library like BeanUtils)
```java
// Controller
@PatchMapping("/{id}")
public ResponseEntity<TodoDto> patchTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
    // 1. Find existing Todo
    // 2. Use BeanUtils to copy non-null values from DTO to Entity
    // 3. Save updated Todo
}
```

---

## 🛠 Methods for Mapping Partial Updates (DTOs)
The most common way to implement PATCH with DTOs is to use a DTO where all fields are optional (e.g., using `Optional` or just checking for non-null values).

---

## 🚀 Key Takeaway
PATCH is a more efficient and flexible way to update resources. It's especially useful when dealing with large entities or when updating only a few fields of a resource. Always prefer PATCH for partial updates.
