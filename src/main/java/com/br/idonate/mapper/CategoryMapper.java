package com.br.idonate.mapper;

import com.br.idonate.dto.CategoryDTO;
import com.br.idonate.dto.ItemDTO;
import com.br.idonate.model.Category;
import com.br.idonate.model.Item;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.id());
        category.setName(categoryDTO.name());
        category.setDescription(categoryDTO.description());
        return category;
    }
}
