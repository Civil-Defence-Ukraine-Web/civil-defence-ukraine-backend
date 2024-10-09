package org.cdu.backend.dto.news;

import static org.cdu.backend.model.News.NewsType;

import java.time.LocalDateTime;

public record NewsUpdateRequestDto(String title, String text, NewsType type,
                                   LocalDateTime publicationDate) {
}
