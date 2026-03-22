# API Versioning with DTOs

## What is API Versioning?
API versioning is the process of managing changes to an API in a way that allows older clients to continue using the API while newer clients can take advantage of the latest features.

### Why Use API Versioning?
API changes (e.g., adding/renaming fields, changing data formats) can break existing clients. Versioning ensures that you can introduce changes without breaking older versions.

---

## 🔄 Before vs. After API Versioning

### 🛑 Before: No Versioning
In this approach, every change you make to an API can potentially break older clients.

```java
// No Versioning
GET /todos
{
    "id": 1,
    "title": "Old Title",
    "description": "Old Description"
}

// After a change
GET /todos
{
    "id": 1,
    "todo_title": "Old Title", // 🛑 Broken!
    "todo_description": "Old Description"
}
```

### ✅ After: Using API Versioning
Now, you provide multiple versions of your API.

```java
// Version 1 (V1)
GET /v1/todos
{
    "id": 1,
    "title": "Old Title",
    "description": "Old Description"
}

// Version 2 (V2)
GET /v2/todos
{
    "id": 1,
    "todo_title": "Old Title",
    "todo_description": "Old Description"
}
```

---

## 🛠 Implementing API Versioning with DTOs
There are several ways to implement API versioning in Spring Boot. One common way is to use separate DTOs for each version:

### Method 1: Separate DTOs for Each Version
```java
// V1 DTO
public record TodoV1Dto(Long id, String title) {}

// V2 DTO
public record TodoV2Dto(Long id, String todoTitle, String description) {}

// V1 Controller
@GetMapping("/v1/todos")
public List<TodoV1Dto> getTodosV1() {
    // 1. Fetch Todos
    // 2. Map to V1 DTOs
}

// V2 Controller
@GetMapping("/v2/todos")
public List<TodoV2Dto> getTodosV2() {
    // 1. Fetch Todos
    // 2. Map to V2 DTOs
}
```

---

## 🛠 Methods for API Versioning
1.  **URL Versioning**: `/v1/todos`, `/v2/todos` (Most common)
2.  **Request Parameter Versioning**: `/todos?v=1`
3.  **Header Versioning**: `Accept: application/vnd.company.v1+json`

---

## 🚀 Key Takeaway
API versioning is essential for maintaining a stable and backwards-compatible API. Using separate DTOs for each version allows you to evolve your API without breaking existing clients. Always plan for versioning in your API design.
