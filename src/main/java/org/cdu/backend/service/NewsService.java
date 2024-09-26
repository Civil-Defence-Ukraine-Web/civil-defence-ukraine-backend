package org.cdu.backend.service;

import java.util.List;
import org.cdu.backend.dto.news.NewsCreateRequestDto;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsSearchParameters;
import org.cdu.backend.dto.news.NewsUpdateRequestDto;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    NewsResponseDto save(NewsCreateRequestDto requestDto);

    NewsResponseDto findById(Long id);

    List<NewsResponseDto> findAll(Pageable pageable);

    List<NewsResponseDto> search(NewsSearchParameters searchParameters);

    NewsResponseDto update(Long id, NewsUpdateRequestDto requestDto);

    void deleteById(Long id);
}
