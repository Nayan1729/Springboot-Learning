package com.example.testingspringbootapplications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Todo business logic.
 * Integrates with TodoRepository for persistence.
 */
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Retrieves a paginated list of todos.
     * @param pageable pagination and sorting information
     * @return a page of todos
     */
    public Page<Todo> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    /**
     * Finds a specific todo by its unique ID.
     * @param id the ID of the todo to find
     * @return an Optional containing the found todo, or empty if not found
     */
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> updateTodo(Long id, Todo todoDetails) {
        return todoRepository.findById(id).map(todo -> {
            todo.setTitle(todoDetails.getTitle());
            todo.setCompleted(todoDetails.isCompleted());
            return todoRepository.save(todo);
        });
    }

    public boolean deleteTodo(Long id) {
        return todoRepository.findById(id).map(todo -> {
            todoRepository.delete(todo);
            return true;
        }).orElse(false);
    }
}
