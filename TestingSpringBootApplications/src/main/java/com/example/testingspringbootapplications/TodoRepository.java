package com.example.testingspringbootapplications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Override
    @EntityGraph(attributePaths = {"categories"})
    List<Todo> findAll();

    @Override
    @EntityGraph(attributePaths = {"categories"})
    Page<Todo> findAll(Pageable pageable);
}
