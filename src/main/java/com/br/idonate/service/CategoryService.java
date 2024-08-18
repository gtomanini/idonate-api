package com.br.idonate.service;

import com.br.idonate.model.Category;
import com.br.idonate.model.Item;
import com.br.idonate.repository.CategoryRepository;
import com.br.idonate.searchCriteria.CategorySearchCriteria;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> findCategories(CategorySearchCriteria criteria, int pageNumber, int pageSize){
        Specification<Category> spec = Specification.where(null);

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        return categoryRepository.findAll(spec, PageRequest.of(pageNumber, pageSize, sort));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
