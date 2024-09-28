package org.cdu.backend.dto.news;

import java.time.LocalDateTime;
import org.cdu.backend.model.News;

public record NewsResponseDto(Long id, String title, String text, String image,
                              LocalDateTime publicationDate, News.NewsType type) {
}
