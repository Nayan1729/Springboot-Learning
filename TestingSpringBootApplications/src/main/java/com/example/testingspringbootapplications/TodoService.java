package com.example.testingspringbootapplications;

import com.example.testingspringbootapplications.dto.TodoRequestDTO;
import com.example.testingspringbootapplications.dto.TodoResponseDTO;
import com.example.testingspringbootapplications.mapper.TodoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service layer for managing Todo business logic.
 * Integrates with TodoRepository for persistence.
 */
@Service
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;
    private final TodoMapper todoMapper;

    public TodoService(TodoRepository todoRepository, CategoryRepository categoryRepository, TodoMapper todoMapper) {
        this.todoRepository = todoRepository;
        this.categoryRepository = categoryRepository;
        this.todoMapper = todoMapper;
    }

    /**
     * Retrieves a paginated list of todos.
     * @param pageable pagination and sorting information
     * @return a page of todos
     */
    public Page<TodoResponseDTO> getAllTodos(Pageable pageable) {
        log.debug("Fetching all todos with pageable: {}", pageable);
        return todoRepository.findAll(pageable).map(todoMapper::toDto);
    }

    /**
     * Finds a specific todo by its unique ID.
     * @param id the ID of the todo to find
     * @return a TodoResponseDTO
     * @throws ResourceNotFoundException if not found
     */
    public TodoResponseDTO getTodoById(Long id) {
        log.debug("Fetching todo with id: {}", id);
        return todoRepository.findById(id)
                .map(todoMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
    }

    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {
        log.info("Creating a new todo: {}", todoRequestDTO.getTitle());
        Todo todo = todoMapper.toEntity(todoRequestDTO);
        Todo savedTodo = todoRepository.save(todo);
        log.info("Todo created with ID: {}", savedTodo.getId());
        return todoMapper.toDto(savedTodo);
    }

    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO todoDetails) {
        log.info("Updating todo with id: {}", id);
        return todoRepository.findById(id).map(todo -> {
            todo.setTitle(todoDetails.getTitle());
            todo.setCompleted(todoDetails.isCompleted());
            return todoMapper.toDto(todoRepository.save(todo));
        }).orElseThrow(() -> new ResourceNotFoundException("Todo not found for update with id: " + id));
    }

    public TodoResponseDTO patchTodo(Long id, TodoRequestDTO todoDetails) {
        log.info("Patching todo with id: {}", id);
        return todoRepository.findById(id).map(todo -> {
            todoMapper.updateEntityFromDto(todoDetails, todo);
            return todoMapper.toDto(todoRepository.save(todo));
        }).orElseThrow(() -> new ResourceNotFoundException("Todo not found for patching with id: " + id));
    }

    public void deleteTodo(Long id) {
        log.info("Deleting todo with id: {}", id);
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found for deletion with id: " + id));
        todoRepository.delete(todo);
        log.info("Todo with ID {} deleted successfully", id);
    }

    public TodoResponseDTO addCategoryToTodo(Long todoId, String categoryName) {
        log.info("Adding category '{}' to todo ID: {}", categoryName, todoId);
        return todoRepository.findById(todoId).map(todo -> {
            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> {
                        log.debug("Category '{}' not found, creating new one.", categoryName);
                        return categoryRepository.save(Category.builder().name(categoryName).build());
                    });
            todo.getCategories().add(category);
            return todoMapper.toDto(todoRepository.save(todo));
        }).orElseThrow(() -> new ResourceNotFoundException("Todo not found to add category, id: " + todoId));
    }
}
