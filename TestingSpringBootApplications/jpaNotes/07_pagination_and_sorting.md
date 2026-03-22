# Pagination and Sorting with JPA

Pagination is about splitting your data into manageable "pages" (e.g., showing only 10 items at a time). Sorting is about ordering the data (e.g., newest first).

## 1. What was used BEFORE?
Before modern JPA, pagination was a nightmare with raw **SQL/JDBC**.
- **Manual Calculations**: You had to manually calculate your page offset (`(page - 1) * size`).
- **DB Specific Syntax**: MySQL used `LIMIT`, Oracle used `ROWNUM`, and PostgreSQL used `OFFSET/LIMIT`.
- **Boilerplate**: You had to write two queries for every search: one for the data and one for the total count.

## 2. What is used NOW?
Spring Data JPA comes with built-in support for **Pageable** and **Sort** interfaces.

### A. How it works
JPA Repository methods can accept a `Pageable` object.

```java
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    // Spring automatically adds LIMIT/OFFSET to the SQL
    Page<Todo> findByStatus(String status, Pageable pageable);
}
```

### B. Using Pageable in your Service/Controller
In your Controller, you can just accept `page` and `size` from the request.

```java
@GetMapping("/todos")
public Page<Todo> getTodos(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id") String sortBy
) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
    return todoService.findAll(pageable);
}
```

## 3. Difference between Page vs Slice

| Return Type | Usage | Performance |
| :--- | :--- | :--- |
| **List<T>** | No pagination info. | Fast (No count query). |
| **Page<T>** | Total elements, total pages, current page. | Slower (Runs 2 queries: fetch data + count total). |
| **Slice<T>** | "Has next" page only. No total count. | Faster (Runs 1 query: fetch size+1). |

## 4. Why use Slice?
Use **Slice** for **Infinite Scrolls** (like Facebook or Instagram). You only need to know if there's more data, not how much total data exists.

## 5. Sorting
Sorting can be done separately if you don't need pagination.

```java
List<Todo> todos = todoRepository.findAll(Sort.by("title").ascending().and(Sort.by("dueDate").descending()));
```

## 6. Benefits for our App
- **Memory Efficiency**: Never load 10,000 todos into the application's memory. Load only the 10 you need for the UI.
- **Improved Performance**: Database queries are much faster when limited to a few records.
- **Better UX**: Users get a clearer, faster experience when data is paginated.

> [!IMPORTANT]
> Always use **Pageable** for potential large datasets. Even if you don't expect many records now, it's a best practice to protect your application from data overflow later.
