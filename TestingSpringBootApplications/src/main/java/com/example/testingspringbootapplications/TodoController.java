package com.example.testingspringbootapplications;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * REST Controller for Todo management (v1).
 * 
 * This controller supports:
 * - API Versioning (base path: /api/v1/todos)
 * - Request Validation (via Jakarta Bean Validation)
 * - Paging and Sorting (via Spring Data Pageable)
 * - Global Exception Handling (consistent JSON error responses)
 */
@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * Retrieves all todos with built-in paging and sorting.
     * 
     * Default Behavior:
     * - Returns 20 items per page (if 'size' not provided).
     * - Returns the first page (if 'page' not provided).
     * 
     * Query Parameters:
     * @param pageable accepts:
     *   - page: (page number, 0-indexed, e.g., ?page=0)
     *   - size: (items per page, e.g., ?size=10)
     *   - sort: (property,direction, e.g., ?sort=title,desc)
     * 
     * Response Structure (Page object):
     * - content: List of items for the requested page.
     * - totalPages: Total pages available in the database.
     * - totalElements: Total records available in the database.
     * - number: The current page number.
     * 
     * @return a ResponseEntity containing the Page of Todo objects
     */
    @GetMapping
    public ResponseEntity<Page<Todo>> getAllTodos(Pageable pageable) {
        return ResponseEntity.ok(todoService.getAllTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody Todo todoDetails) {
        return todoService.updateTodo(id, todoDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if (todoService.deleteTodo(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
