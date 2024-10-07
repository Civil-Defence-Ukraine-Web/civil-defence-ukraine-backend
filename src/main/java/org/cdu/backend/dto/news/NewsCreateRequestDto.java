package org.cdu.backend.dto.news;

import jakarta.validation.constraints.NotBlank;
import org.cdu.backend.model.News.NewsType;

public record NewsCreateRequestDto(@NotBlank String title, @NotBlank String text,
                                   @NotBlank NewsType type) {
}
