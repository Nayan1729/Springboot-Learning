package com.example.testingspringbootapplications.mapper;

import com.example.testingspringbootapplications.Todo;
import com.example.testingspringbootapplications.dto.TodoRequestDTO;
import com.example.testingspringbootapplications.dto.TodoResponseDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface TodoMapper {

    TodoResponseDTO toDto(Todo todo);

    Todo toEntity(TodoRequestDTO todoRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TodoRequestDTO todoRequestDTO, @MappingTarget Todo entity);
}
