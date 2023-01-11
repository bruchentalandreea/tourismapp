package com.example.springdemo.services;

import com.example.springdemo.dto.CategoryDTO;
import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.entities.Category;
import com.example.springdemo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

import java.util.List;

@Service

public class CategoryService {


    private final CategoryRepository categoryRepository;

    private CategoryDTO categoryDTO;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    public List<CategoryDTO> showAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::mapFromCategoryToDto).collect(toList());
    }

    private CategoryDTO mapFromCategoryToDto(Category category) {
        CategoryDTO categoryDTO=new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategory(category.getCategory());

        return categoryDTO;
    }

}
