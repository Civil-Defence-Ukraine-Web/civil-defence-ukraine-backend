package org.cdu.backend.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.news.NewsCreateRequestDto;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsSearchParameters;
import org.cdu.backend.dto.news.NewsUpdateRequestDto;
import org.cdu.backend.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public List<NewsResponseDto> findAll() {
        return newsService.findAll();
    }

    @GetMapping("/{id}")
    public NewsResponseDto findById(@PathVariable Long id) {
        return newsService.findById(id);
    }

    @GetMapping("/search")
    public List<NewsResponseDto> search(NewsSearchParameters searchParameters) {
        return newsService.search(searchParameters);
    }

    @PostMapping
    public NewsResponseDto save(@RequestBody @Valid NewsCreateRequestDto requestDto) {
        return newsService.save(requestDto);
    }

    @PutMapping("/{id}")
    public NewsResponseDto update(@RequestBody @Valid NewsUpdateRequestDto requestDto,
                                  @PathVariable Long id) {
        return newsService.update(id,requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        newsService.deleteById(id);
    }
}
