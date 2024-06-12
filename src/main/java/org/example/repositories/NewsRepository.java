package org.example.repositories;

import org.example.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Override
    boolean existsById(Long id);
}
