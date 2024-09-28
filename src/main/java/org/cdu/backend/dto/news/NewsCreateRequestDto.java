package org.cdu.backend.dto.news;

import jakarta.validation.constraints.NotBlank;
import lombok.experimental.Accessors;
import org.cdu.backend.model.News;

public record NewsCreateRequestDto(@NotBlank String title, @NotBlank String text,
                                   String image, @NotBlank News.NewsType type) {
}
