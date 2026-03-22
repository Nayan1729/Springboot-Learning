# Exercise Implementation: DTOs, MapStruct, and PATCH

## Overview
In this exercise, I refactored the existing Todo API to use modern best practices. This involved introducing DTOs, using MapStruct for mapping, and implementing partial updates with PATCH.

## 🛠 What was done?

### 1. Introduced DTOs
I created two DTOs to separate the API contract from the database entity:
- **`TodoRequestDTO`**: Used for incoming requests (Create/Update). It contains validation rules.
- **`TodoResponseDTO`**: Used for outgoing responses. It only exposes safe fields to the client.

### 2. Implemented MapStruct Mapper
Instead of manual mapping, I used MapStruct to generate the mapping logic. This is defined in the `TodoMapper` interface.
- It handles conversion between `Todo` (Entity) and the DTOs.
- It also handles partial updates using the `@MappingTarget` and `NullValuePropertyMappingStrategy.IGNORE`.

### 3. Refactored Service and Controller
- **Service**: Now accepts and returns DTOs. It uses the `TodoMapper` to perform conversions.
- **Controller**: Now uses the `v1` path and accepts/returns DTOs. It also includes a new `PATCH` endpoint.

### 4. Implemented PATCH (Partial Update)
The `PATCH` endpoint allows updating only specific fields. For example, to mark a Todo as completed without changing its title:
```http
PATCH /api/v1/todos/1
Content-Type: application/json

{
    "completed": true
}
```

---

## 🔄 Summary: Before vs. After

### 🛑 Before (The Traditional Way)
- **Entities** were exposed directly in the Controller.
- **Validation** was placed directly on Entity fields.
- **Manual Mapping** (if any) was done in the Service layer.
- **PUT** was mostly used for all updates (requiring the full object).

### ✅ After (The Modern Spring Boot Way)
- **DTOs** shield the Internal Entity from the external world.
- **Validation** is handled at the API boundaries (DTOs).
- **MapStruct** automates the mapping, reducing boilerplate.
- **PATCH** allows for efficient, partial resource updates.

---

## 🚀 How to Run/Test
1.  Run the application using `./mvnw spring-boot:run`.
2.  Use Postman or curl to test the endpoints:
    - `POST /api/v1/todos`: Create a new Todo using `TodoRequestDTO`.
    - `PATCH /api/v1/todos/{id}`: Partially update a Todo.
    - `GET /api/v1/todos`: See the clean `TodoResponseDTO` output.
