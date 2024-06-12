package org.example.controllers;

import org.example.dto.CategoryDto;
import org.example.service.CategoryCRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryCRUDService categoryService;

    public CategoryController(CategoryCRUDService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public ResponseEntity<CategoryDto> getAllCategories() {
        return categoryService.getAll();
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createNews(@RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteById(id);
    }
}
