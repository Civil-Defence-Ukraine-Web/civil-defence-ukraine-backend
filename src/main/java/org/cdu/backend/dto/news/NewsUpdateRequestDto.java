package org.cdu.backend.dto.news;

import java.time.LocalDateTime;
import org.cdu.backend.model.News;

public record NewsUpdateRequestDto(String title, String text,
                                   String image, News.NewsType type,
                                   LocalDateTime publicationDate) {
}
