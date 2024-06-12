package org.example.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryDto {

    private Long id;
    private String title;
    private List<NewsDto> newsList = new ArrayList<>();
}
