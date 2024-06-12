package org.example.repositories;

import org.example.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    boolean existsById(Long id);
}
