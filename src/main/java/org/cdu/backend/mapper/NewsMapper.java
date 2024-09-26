package org.cdu.backend.mapper;

import java.util.List;
import org.cdu.backend.config.MapperConfig;
import org.cdu.backend.dto.news.NewsCreateRequestDto;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsUpdateRequestDto;
import org.cdu.backend.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface NewsMapper {
    News toModel(NewsCreateRequestDto requestDto);

    NewsResponseDto toResponseDto(News news);

    List<NewsResponseDto> toResponseDtoList(List<News> newsList);

    void updateNewsFromRequestDto(NewsUpdateRequestDto requestDto, @MappingTarget News news);
}
