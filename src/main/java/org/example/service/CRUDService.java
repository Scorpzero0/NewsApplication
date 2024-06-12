package org.example.service;

import org.example.entity.News;
import org.springframework.http.ResponseEntity;

public interface CRUDService<T> {

    ResponseEntity<T> getById(Long id);
    ResponseEntity<T> getAll();
    ResponseEntity<T> create(T item);
    ResponseEntity<T> update(Long id, T item);
    ResponseEntity<T> deleteById(Long id);

}
