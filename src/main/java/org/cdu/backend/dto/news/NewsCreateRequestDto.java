package org.cdu.backend.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.cdu.backend.model.News.NewsType;

public record NewsCreateRequestDto(@NotBlank String title, @NotBlank String text,
                                   @NotNull NewsType type, LocalDateTime publicationDate) {
}
