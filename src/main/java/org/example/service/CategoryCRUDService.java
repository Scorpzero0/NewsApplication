package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.CategoryDto;
import org.example.entity.Category;
import org.example.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryCRUDService implements CRUDService<CategoryDto> {

    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<CategoryDto> getById(Long id) {
        log.info("Get category by id " + id);
        if (categoryRepository.existsById(id)) {
            return new ResponseEntity<>(mapToDto(categoryRepository.findById(id).orElseThrow()), HttpStatus.OK);
        } else {
            return new ResponseEntity("{\n\t \"message\" : \"Категория с ID " + id + " не найдена.\"\n}",
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<CategoryDto> getAll() {
        log.info("Get all categories");
        return new ResponseEntity(categoryRepository.findAll().stream()
                .map(CategoryCRUDService::mapToDto)
                .toList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryDto> create(CategoryDto categoryDto) {
        log.info("Create new category");
        Long id = categoryRepository.count() != 0 ? categoryRepository.count() + 1 : 1;
        categoryDto.setId(id);
        categoryRepository.save(mapToEntity(categoryDto));
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CategoryDto> update(Long id, CategoryDto categoryDto) {
        log.info("Update category by id " + id);
        if (categoryRepository.existsById(id)) {
            categoryRepository.save(mapToEntity(categoryDto));
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        } else {
            return new ResponseEntity("{\n\t \"message\" : \"Категория с ID " + id + " не найдена.\"\n}",
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<CategoryDto> deleteById(Long id) {
        log.info("Delete category by id " + id);
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity("{\n\t \"message\" : \"Категория с ID " + id + " не найдена.\"\n}",
                    HttpStatus.NOT_FOUND);
        }
    }

    public static Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        category.setNewsList(
                categoryDto.getNewsList()
                        .stream()
                        .map(NewsCRUDService::mapToEntity).toList());
        return category;
    }

    public static CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setNewsList(
                category.getNewsList()
                        .stream()
                        .map(NewsCRUDService::mapToDto).toList());
        return categoryDto;
    }
}
