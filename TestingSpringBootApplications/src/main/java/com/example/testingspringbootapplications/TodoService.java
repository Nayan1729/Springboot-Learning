package com.example.testingspringbootapplications;

import com.example.testingspringbootapplications.dto.TodoRequestDTO;
import com.example.testingspringbootapplications.dto.TodoResponseDTO;
import com.example.testingspringbootapplications.mapper.TodoMapper;
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
    private final TodoMapper todoMapper;

    public TodoService(TodoRepository todoRepository, TodoMapper todoMapper) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
    }

    /**
     * Retrieves a paginated list of todos.
     * @param pageable pagination and sorting information
     * @return a page of todos
     */
    public Page<TodoResponseDTO> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable).map(todoMapper::toDto);
    }

    /**
     * Finds a specific todo by its unique ID.
     * @param id the ID of the todo to find
     * @return an Optional containing the found todo, or empty if not found
     */
    public Optional<TodoResponseDTO> getTodoById(Long id) {
        return todoRepository.findById(id).map(todoMapper::toDto);
    }

    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {
        Todo todo = todoMapper.toEntity(todoRequestDTO);
        Todo savedTodo = todoRepository.save(todo);
        return todoMapper.toDto(savedTodo);
    }

    public Optional<TodoResponseDTO> updateTodo(Long id, TodoRequestDTO todoDetails) {
        return todoRepository.findById(id).map(todo -> {
            todo.setTitle(todoDetails.getTitle());
            todo.setCompleted(todoDetails.isCompleted());
            return todoMapper.toDto(todoRepository.save(todo));
        });
    }

    public Optional<TodoResponseDTO> patchTodo(Long id, TodoRequestDTO todoDetails) {
        return todoRepository.findById(id).map(todo -> {
            todoMapper.updateEntityFromDto(todoDetails, todo);
            return todoMapper.toDto(todoRepository.save(todo));
        });
    }

    public boolean deleteTodo(Long id) {
        return todoRepository.findById(id).map(todo -> {
            todoRepository.delete(todo);
            return true;
        }).orElse(false);
    }
}
