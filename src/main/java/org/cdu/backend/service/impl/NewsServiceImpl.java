package org.cdu.backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
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
import org.cdu.backend.service.ImageService;
import org.cdu.backend.service.NewsService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final NewsSpecificationBuilder specificationBuilder;
    private final ImageService imageService;

    @Override
    public NewsResponseDto save(NewsCreateRequestDto requestDto, MultipartFile image) {
        String imageUrl = null;
        News news = newsMapper.toModel(requestDto);
        if (image != null) {
            imageUrl = imageService.save(image, DropboxImageServiceImpl.ImageType.NEWS_IMAGE);
            news.setImage(imageUrl);
        }
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
    public List<NewsResponseDto> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .stream()
                .map(newsMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<NewsResponseDto> findAll() {
        return newsRepository.findAll()
                .stream()
                .map(newsMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<NewsResponseDto> findLastMonth(Pageable pageable) {
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        return newsRepository.findNewsByPublicationDateAfter(lastMonth, pageable)
                .stream()
                .map(newsMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<NewsResponseDto> search(NewsSearchParameters searchParameters) {
        Specification<News> newsSpecification = specificationBuilder.build(searchParameters);
        List<News> newsList = newsRepository.findAll(newsSpecification);
        return newsMapper.toResponseDtoList(newsList);
    }

    @Override
    public NewsResponseDto update(Long id, NewsUpdateRequestDto requestDto, MultipartFile image) {
        News news = newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "No News found with id: " + id)
        );
        newsMapper.updateNewsFromRequestDto(requestDto, news);
        if (image != null) {
            String imageUrl = imageService.save(image,
                    DropboxImageServiceImpl.ImageType.NEWS_IMAGE);
            news.setImage(imageUrl);
        }
        newsRepository.save(news);
        return newsMapper.toResponseDto(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
