package org.example.controllers;

import org.example.dto.NewsDto;
import org.example.entity.News;
import org.example.service.NewsCRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsCRUDService newsService;

    public NewsController(NewsCRUDService newsService) {
        this.newsService = newsService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {
        return newsService.getById(id);
    }

    @GetMapping
    public ResponseEntity<NewsDto> getAllNews() {
        return newsService.getAll();
    }

    @PostMapping
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsDto newsDto) {
        return newsService.create(newsDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(@PathVariable Long id, @RequestBody NewsDto newsDto) {
        newsDto.setId(id);
        return newsService.update(id, newsDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewsDto> deleteNews(@PathVariable Long id) {
        return newsService.deleteById(id);
    }
}
