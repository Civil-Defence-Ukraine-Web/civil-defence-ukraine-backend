package org.cdu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.news.NewsCreateRequestDto;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsSearchParameters;
import org.cdu.backend.dto.news.NewsUpdateRequestDto;
import org.cdu.backend.service.NewsService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "News management", description = "Endpoint for news management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @Operation(summary = "Find all news", description = "Returns list of all news with or without"
            + " sorting and pagination")
    @GetMapping
    public List<NewsResponseDto> findAll(Pageable pageable) {
        return newsService.findAll(pageable);
    }

    @Operation(summary = "Find news by id", description = "Returns news with required id")
    @GetMapping("/{id}")
    public NewsResponseDto findById(@PathVariable Long id) {
        return newsService.findById(id);
    }

    @Operation(summary = "Search news with params", description = "Search news with params: "
            + "title or type")
    @GetMapping("/search")
    public List<NewsResponseDto> search(NewsSearchParameters searchParameters) {
        return newsService.search(searchParameters);
    }

    @Operation(summary = "Save news to database", description = "Save news to database")
    @PostMapping
    public NewsResponseDto save(
            @RequestPart("requestDto") @Valid NewsCreateRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return newsService.save(requestDto, image);
    }

    @Operation(summary = "Update news", description = "Update news by id and update "
            + "request body")
    @PutMapping("/{id}")
    public NewsResponseDto update(
            @RequestPart("requestDto") NewsUpdateRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable Long id) {
        return newsService.update(id, requestDto, image);
    }

    @Operation(summary = "Delete news", description = "Delete news by id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        newsService.deleteById(id);
    }
}
