package org.example.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class NewsDto {

    private Long id;
    private String title;
    private String text;
    private Instant date;
    private Long categoryId;
}
