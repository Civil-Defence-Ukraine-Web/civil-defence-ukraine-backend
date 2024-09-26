package org.cdu.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.news.NewsCreateRequestDto;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsSearchParameters;
import org.cdu.backend.dto.news.NewsUpdateRequestDto;
import org.cdu.backend.exception.EntityNotFoundException;
import org.cdu.backend.mapper.NewsMapper;
import org.cdu.backend.model.News;
import org.cdu.backend.repository.news.NewsRepository;
import org.cdu.backend.repository.news.NewsSpecificationBuilder;
import org.cdu.backend.service.NewsService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final NewsSpecificationBuilder specificationBuilder;

    @Override
    public NewsResponseDto save(NewsCreateRequestDto requestDto) {
        News news = newsMapper.toModel(requestDto);
        newsRepository.save(news);
        return newsMapper.toResponseDto(news);
    }

    @Override
    public NewsResponseDto findById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "No News found with id: " + id)
        );
        return newsMapper.toResponseDto(news);
    }

    @Override
    public List<NewsResponseDto> findAll() {
        return newsMapper.toResponseDtoList(newsRepository.findAll());
    }

    @Override
    public List<NewsResponseDto> search(NewsSearchParameters searchParameters) {
        Specification<News> newsSpecification = specificationBuilder.build(searchParameters);
        List<News> newsList = newsRepository.findAll(newsSpecification);
        return newsMapper.toResponseDtoList(newsList);

    }

    @Override
    public NewsResponseDto update(Long id, NewsUpdateRequestDto requestDto) {
        News news = newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "No News found with id: " + id)
        );
        newsMapper.updateNewsFromRequestDto(requestDto, news);
        newsRepository.save(news);
        return newsMapper.toResponseDto(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}