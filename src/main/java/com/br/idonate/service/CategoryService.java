package com.br.idonate.service;

import com.br.idonate.dto.CategoryDTO;
import com.br.idonate.mapper.CategoryMapper;
import com.br.idonate.model.Category;
import com.br.idonate.model.Item;
import com.br.idonate.repository.CategoryRepository;
import com.br.idonate.searchCriteria.CategorySearchCriteria;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<CategoryDTO> findCategories(CategorySearchCriteria criteria, int pageNumber, int pageSize){
        Specification<Category> spec = Specification.where(null);

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Page<Category> categories = categoryRepository.findAll(spec, PageRequest.of(pageNumber, pageSize, sort));

        List<CategoryDTO> categoryDTOS = categories.getContent().stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, categories.getPageable(), categories.getTotalElements());
//        return categoryRepository.findAll(spec, PageRequest.of(pageNumber, pageSize, sort));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category delete(Category category) {
        categoryRepository.delete(category);
        return category;
    }
}
