package org.cdu.backend.dto.news;

import jakarta.validation.constraints.NotBlank;
import org.cdu.backend.model.News;

public record NewsUpdateRequestDto(String title, String text,
                                   String image, News.NewsType type) {
}
