package org.cdu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsSearchParameters;
import org.cdu.backend.service.NewsService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "News management", description = "Endpoint for news management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/public/news")
public class NewsController {
    private final NewsService newsService;

    @Operation(summary = "Find all news", description = "Returns list of all news with or without"
            + " sorting and pagination")
    @GetMapping
    public List<NewsResponseDto> findAll(Pageable pageable) {
        if (pageable.isUnpaged()) {
            return newsService.findAll();
        }
        return newsService.findAll(pageable);
    }

    @Operation(summary = "Find news by id", description = "Returns news with required id")
    @GetMapping("/{id}")
    public NewsResponseDto findById(@PathVariable Long id) {
        return newsService.findById(id);
    }

    @Operation(summary = "Find last month news", description = "Returns news that were posted in "
            + "last month")
    @GetMapping("/last-month")
    public List<NewsResponseDto> findLastMonth(Pageable pageable) {
        return newsService.findLastMonth(pageable);
    }

    @Operation(summary = "Search news with params", description = "Search news with params: "
            + "title or type")
    @GetMapping("/search")
    public List<NewsResponseDto> search(NewsSearchParameters searchParameters) {
        return newsService.search(searchParameters);
    }
}
