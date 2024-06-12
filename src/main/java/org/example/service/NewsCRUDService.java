package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.NewsDto;
import org.example.entity.Category;
import org.example.entity.News;
import org.example.repositories.CategoryRepository;
import org.example.repositories.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCRUDService implements CRUDService<NewsDto> {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<NewsDto> getById(Long id) {
        log.info("Get news by id " + id);
        if (newsRepository.existsById(id)) {
            return new ResponseEntity<>(mapToDto(newsRepository.findById(id).orElseThrow()), HttpStatus.OK);
        } else {
            return new ResponseEntity("{\n\t \"message\" : \"Новость с ID " + id + " не найдена.\"\n}",
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<NewsDto> getAll() {
        log.info("Get all news");
        return new ResponseEntity(newsRepository.findAll().stream()
                .map(NewsCRUDService::mapToDto)
                .toList(),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NewsDto> create(NewsDto newsDto) {
        log.info("Create new news");
        News news = mapToEntity(newsDto);
        Long id = newsRepository.count() != 0 ? newsRepository.count() + 1 : 1;
        news.setId(id);
        news.setDate(Instant.now());

        Long categoryId = newsDto.getCategoryId();
        if (categoryRepository.existsById(categoryId)) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            news.setCategory(category);
            newsRepository.save(news);
            return new ResponseEntity<>(mapToDto(news), HttpStatus.CREATED);
        } else {
            return new ResponseEntity("{\n\t \"message\" : \"Категория с ID " + categoryId + " не найдена.\"\n}",
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<NewsDto> update(Long id, NewsDto newsDto) {
        log.info("Update news by id " + id);
        if (newsRepository.existsById(id)) {
            News news = mapToEntity(newsDto);
            news.setId(id);
            news.setDate(newsRepository.findById(id).get().getDate());
            Long categoryId = newsDto.getCategoryId();
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            news.setCategory(category);
            newsRepository.save(news);
            return new ResponseEntity<>(mapToDto(news),HttpStatus.OK);
        } else {
            return new ResponseEntity("{\n\t \"message\" : \"Новость с ID " + id + " не найдена.\"\n}",
                    HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<NewsDto> deleteById(Long id) {
        log.info("Delete news by id " + id);
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity("{\n\t \"message\" : \"Новость с ID " + id + " не найдена.\"\n}",
                    HttpStatus.NOT_FOUND);
        }
    }

    public static News mapToEntity(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        news.setDate(newsDto.getDate());
        return news;
    }

    public static NewsDto mapToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setText(news.getText());
        newsDto.setDate(news.getDate());
        newsDto.setCategoryId(news.getCategory().getId());
        return newsDto;
    }
}
