package com.example.testingspringbootapplications;

import com.example.testingspringbootapplications.dto.TodoRequestDTO;
import com.example.testingspringbootapplications.dto.TodoResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * REST Controller for Todo management (v1).
 */
@RestController
@RequestMapping("/api/v1/todos")
@Slf4j
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<Page<TodoResponseDTO>> getAllTodos(Pageable pageable) {
        log.info("REST request to get all Todos");
        return ResponseEntity.ok(todoService.getAllTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> getTodoById(@PathVariable Long id) {
        log.info("REST request to get Todo by id: {}", id);
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTodo(@Valid @RequestBody TodoRequestDTO todo) {
        log.info("REST request to create Todo: {}", todo.getTitle());
        TodoResponseDTO createdTodo = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDTO todoDetails) {
        log.info("REST request to update Todo: {}", id);
        return ResponseEntity.ok(todoService.updateTodo(id, todoDetails));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> patchTodo(@PathVariable Long id, @RequestBody TodoRequestDTO todoDetails) {
        log.info("REST request to patch Todo: {}", id);
        return ResponseEntity.ok(todoService.patchTodo(id, todoDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        log.info("REST request to delete Todo: {}", id);
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
