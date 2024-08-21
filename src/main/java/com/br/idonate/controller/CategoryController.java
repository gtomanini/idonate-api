package com.br.idonate.controller;


import com.br.idonate.model.Category;
import com.br.idonate.model.Item;
import com.br.idonate.searchCriteria.CategorySearchCriteria;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import com.br.idonate.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> index(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        CategorySearchCriteria criteria = new CategorySearchCriteria();
        return ResponseEntity.ok(categoryService.findCategories(criteria, pageNumber, pageSize));
    }

    @PostMapping
    private Category create(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping
    private Category update(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @DeleteMapping
    private void delete(@RequestBody Category category) {
        categoryService.delete(category);
    }
}
