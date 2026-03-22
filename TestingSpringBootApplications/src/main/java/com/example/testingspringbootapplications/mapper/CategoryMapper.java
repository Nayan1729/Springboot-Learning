package com.example.testingspringbootapplications.mapper;

import com.example.testingspringbootapplications.Category;
import com.example.testingspringbootapplications.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
