package org.cdu.backend.service;

import static org.cdu.backend.service.impl.DropboxImageServiceImpl.ImageType.NEWS_IMAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.cdu.backend.dto.news.NewsCreateRequestDto;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsSearchParameters;
import org.cdu.backend.dto.news.NewsUpdateRequestDto;
import org.cdu.backend.exception.EntityNotFoundException;
import org.cdu.backend.mapper.NewsMapper;
import org.cdu.backend.model.News;
import org.cdu.backend.repository.news.NewsRepository;
import org.cdu.backend.repository.news.NewsSpecificationBuilder;
import org.cdu.backend.service.impl.DropboxImageServiceImpl;
import org.cdu.backend.service.impl.NewsServiceImpl;
import org.cdu.backend.util.NewsUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsMapper newsMapper;
    @Mock
    private NewsSpecificationBuilder newsSpecificationBuilder;
    @Mock
    private DropboxImageServiceImpl dropboxImageService;

    @InjectMocks
    private NewsServiceImpl newsService;

    @DisplayName("""
            Verify save() method works correctly and returns correct DTO after saving
            """)
    @Test
    public void save_WithValidCreateNewsDto_ShouldReturnValidNewsDto() {
        NewsCreateRequestDto createFirstRequestDto = NewsUtil.createFirstNewsCreateRequestDto();
        News firstNews = NewsUtil.createFirstNews();
        NewsResponseDto firstNewsResponseDto = NewsUtil.createFirstNewsResponseDto();
        MultipartFile image = new MockMultipartFile("image", (byte[]) null);

        when(newsMapper.toModel(createFirstRequestDto)).thenReturn(firstNews);
        when(newsRepository.save(firstNews)).thenReturn(firstNews);
        when(newsMapper.toResponseDto(firstNews)).thenReturn(firstNewsResponseDto);
        when(dropboxImageService.save(image, NEWS_IMAGE)).thenReturn(firstNewsResponseDto.image());

        NewsResponseDto result = newsService.save(createFirstRequestDto, image);

        assertEquals(firstNewsResponseDto, result);
        verify(newsMapper, times(1)).toModel(any());
        verify(newsRepository, times(1)).save(any());
        verify(newsMapper, times(1)).toResponseDto(any());
        verify(dropboxImageService, times(1)).save(any(), NEWS_IMAGE);
    }

    @DisplayName("""
            Verify update() method works correctly and returns correct DTO after updating
            """)
    @Test
    public void update_WithValidUpdateNewsDto_ShouldReturnValidNewsDto() {
        NewsUpdateRequestDto updateFirstRequestDto = NewsUtil.createUpdateToFirstNewsRequestDto();
        News secondNews = NewsUtil.createSecondNews();
        NewsResponseDto secondNewsResponseDto = NewsUtil.createSecondNewsResponseDto();
        MultipartFile image = new MockMultipartFile("image", (byte[]) null);

        when(newsRepository.findById(secondNews.getId())).thenReturn(Optional.of(secondNews));
        doAnswer(invocationOnMock -> {
            NewsUpdateRequestDto invocationUpdateDto =
                    (NewsUpdateRequestDto) invocationOnMock.getArguments()[0];
            News invocationNews = (News) invocationOnMock.getArguments()[1];

            invocationNews.setTitle(invocationUpdateDto.title());
            invocationNews.setText(invocationUpdateDto.text());
            invocationNews.setType(invocationUpdateDto.type());

            return null;
        }).when(newsMapper).updateNewsFromRequestDto(updateFirstRequestDto, secondNews);
        when(newsRepository.save(secondNews)).thenReturn(secondNews);
        when(newsMapper.toResponseDto(secondNews)).thenReturn(secondNewsResponseDto);

        NewsResponseDto result = newsService.update(secondNews.getId(), updateFirstRequestDto,
                image);

        assertEquals(secondNewsResponseDto, result);
        verify(newsRepository, times(1)).findById(any());
        verify(newsMapper, times(1)).updateNewsFromRequestDto(any(), any());
        verify(newsRepository, times(1)).save(any());
        verify(newsMapper, times(1)).toResponseDto(any());
    }

    @DisplayName("""
            Verify findById() method works correctly and returns correct DTO after finding
            """)
    @Test
    public void findById_WithValidId_ShouldReturnValidNewsDto() {
        News firstNews = NewsUtil.createFirstNews();
        NewsResponseDto firstNewsResponseDto = NewsUtil.createFirstNewsResponseDto();

        when(newsRepository.findById(firstNews.getId()))
                .thenReturn(Optional.of(firstNews));
        when(newsMapper.toResponseDto(firstNews)).thenReturn(firstNewsResponseDto);

        NewsResponseDto result = newsService.findById(firstNews.getId());

        assertEquals(firstNewsResponseDto, result);
        verify(newsRepository, times(1)).findById(any());
        verify(newsMapper, times(1)).toResponseDto(any());
    }

    @DisplayName("""
            Verify findById() method works correctly and throws exception when news not found
            """)
    @Test
    public void findById_WithInvalidId_ShouldThrowException() {
        Long id = NewsUtil.FIRST_NEWS_ID;

        when(newsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> newsService.findById(id));
        verify(newsRepository, times(1)).findById(id);
        verify(newsMapper, never()).toResponseDto(any());
    }

    @DisplayName("""
            Verify findAll() method works correctly and returns correct DTO list after finding
            """)
    @Test
    public void findAll_WithThreeNewsInDatabase_ShouldReturnThreeNews() {
        News firstNews = NewsUtil.createFirstNews();
        News secondNews = NewsUtil.createSecondNews();
        News thirdNews = NewsUtil.createThirdNews();
        List<News> newsList = List.of(firstNews, secondNews, thirdNews);

        NewsResponseDto firstNewsResponseDto = NewsUtil.createFirstNewsResponseDto();
        NewsResponseDto secondNewsResponseDto = NewsUtil.createSecondNewsResponseDto();
        NewsResponseDto thirdNewsResponseDto = NewsUtil.createThirdNewsResponseDto();

        Pageable pageable = PageRequest.of(0, 10);
        Page<News> newsPage = new PageImpl<>(newsList);

        when(newsRepository.findAll(pageable)).thenReturn(newsPage);
        when(newsMapper.toResponseDto(firstNews)).thenReturn(firstNewsResponseDto);
        when(newsMapper.toResponseDto(secondNews)).thenReturn(secondNewsResponseDto);
        when(newsMapper.toResponseDto(thirdNews)).thenReturn(thirdNewsResponseDto);

        List<NewsResponseDto> expected = List.of(firstNewsResponseDto,
                secondNewsResponseDto,
                thirdNewsResponseDto);

        List<NewsResponseDto> result = newsService.findAll(pageable);
        assertEquals(expected, result);
        verify(newsRepository, times(1)).findAll(any(Pageable.class));
        verify(newsMapper, times(expected.size())).toResponseDto(any());
    }

    @DisplayName("""
            Verify search() method works correctly and returns needed DTO list after searching
            """)
    @Test
    public void search_WithTwoSearchParamsNewsInDatabase_ShouldReturnOneNews() {
        News firstNews = NewsUtil.createFirstNews();
        NewsResponseDto firstNewsResponseDto = NewsUtil.createFirstNewsResponseDto();

        NewsSearchParameters newsSearchParameters = new NewsSearchParameters("title",
                News.NewsType.NEWS);
        Specification<News> newsSpecification = mock(Specification.class);
        List<News> resultNewsList = List.of(firstNews);
        List<NewsResponseDto> expected = List.of(firstNewsResponseDto);

        when(newsSpecificationBuilder.build(newsSearchParameters)).thenReturn(newsSpecification);
        when(newsRepository.findAll(newsSpecification)).thenReturn(resultNewsList);
        when(newsMapper.toResponseDtoList(resultNewsList))
                .thenReturn(expected);

        List<NewsResponseDto> actual = newsService.search(newsSearchParameters);

        assertEquals(expected, actual);
        verify(newsSpecificationBuilder, times(1))
                .build(any());
        verify(newsRepository, times(1)).findAll(any(Specification.class));
        verify(newsMapper, times(expected.size())).toResponseDtoList(any());
    }
}
