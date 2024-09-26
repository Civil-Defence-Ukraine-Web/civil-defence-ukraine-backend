package org.cdu.backend.dto.news;

import org.cdu.backend.model.News;

import java.time.LocalDateTime;

public record NewsResponseDto(Long id, String title, String text, String image,
                              LocalDateTime date, News.NewsType type) {
}