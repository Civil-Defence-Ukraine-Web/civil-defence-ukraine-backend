package org.cdu.backend.util;

import java.time.LocalDateTime;
import java.util.List;
import org.cdu.backend.dto.news.NewsCreateRequestDto;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsUpdateRequestDto;
import org.cdu.backend.model.News;
import org.cdu.backend.model.News.NewsType;

public class NewsUtil {
    public static final Long FIRST_NEWS_ID = 1L;
    public static final String FIRST_NEWS_TITLE = "First news title";
    public static final String FIRST_NEWS_TEXT = "First news text";
    public static final LocalDateTime FIRST_NEWS_PUBLICATION_DATE =
            LocalDateTime.parse("2024-09-28T12:00:00");
    public static final String FIRST_NEWS_IMAGE = "image_1.jpg";
    public static final NewsType FIRST_NEWS_TYPE = NewsType.NEWS;

    public static final Long SECOND_NEWS_ID = 2L;
    public static final String SECOND_NEWS_TITLE = "Second news title";
    public static final String SECOND_NEWS_TEXT = "Second news text";
    public static final LocalDateTime SECOND_NEWS_PUBLICATION_DATE =
            LocalDateTime.parse("2024-09-28T12:00:00");
    public static final String SECOND_NEWS_IMAGE = "image_2.jpg";
    public static final NewsType SECOND_NEWS_TYPE = NewsType.REPORT;

    public static final Long THIRD_NEWS_ID = 3L;
    public static final String THIRD_NEWS_TITLE = "Third news title";
    public static final String THIRD_NEWS_TEXT = "Third news text";
    public static final LocalDateTime THIRD_NEWS_PUBLICATION_DATE =
            LocalDateTime.parse("2024-09-28T12:00:00");
    public static final String THIRD_NEWS_IMAGE = "image_3.jpg";
    public static final NewsType THIRD_NEWS_TYPE = NewsType.EVENT;

    public static NewsCreateRequestDto createFirstNewsCreateRequestDto() {
        return new NewsCreateRequestDto(FIRST_NEWS_TITLE, FIRST_NEWS_TEXT, FIRST_NEWS_TYPE,
                FIRST_NEWS_PUBLICATION_DATE);
    }

    public static NewsUpdateRequestDto createUpdateToFirstNewsRequestDto() {
        return new NewsUpdateRequestDto(FIRST_NEWS_TITLE, FIRST_NEWS_TEXT, FIRST_NEWS_TYPE,
                FIRST_NEWS_PUBLICATION_DATE);
    }

    public static News createFirstNews() {
        return new News().setId(FIRST_NEWS_ID)
                .setTitle(FIRST_NEWS_TITLE).setText(FIRST_NEWS_TEXT)
                .setType(FIRST_NEWS_TYPE).setImage(FIRST_NEWS_IMAGE)
                .setPublicationDate(FIRST_NEWS_PUBLICATION_DATE);
    }

    public static News createSecondNews() {
        return new News().setId(SECOND_NEWS_ID)
                .setTitle(SECOND_NEWS_TITLE).setText(SECOND_NEWS_TEXT)
                .setType(SECOND_NEWS_TYPE).setImage(SECOND_NEWS_IMAGE)
                .setPublicationDate(SECOND_NEWS_PUBLICATION_DATE);
    }

    public static News createThirdNews() {
        return new News().setId(THIRD_NEWS_ID)
                .setTitle(THIRD_NEWS_TITLE).setText(THIRD_NEWS_TEXT)
                .setType(THIRD_NEWS_TYPE).setImage(THIRD_NEWS_IMAGE)
                .setPublicationDate(THIRD_NEWS_PUBLICATION_DATE);
    }

    public static NewsResponseDto createFirstNewsResponseDto() {
        return new NewsResponseDto(FIRST_NEWS_ID, FIRST_NEWS_TITLE, FIRST_NEWS_TEXT,
                FIRST_NEWS_IMAGE, FIRST_NEWS_PUBLICATION_DATE, FIRST_NEWS_TYPE);
    }

    public static NewsResponseDto createSecondNewsResponseDto() {
        return new NewsResponseDto(SECOND_NEWS_ID, SECOND_NEWS_TITLE, SECOND_NEWS_TEXT,
                SECOND_NEWS_IMAGE, SECOND_NEWS_PUBLICATION_DATE, SECOND_NEWS_TYPE);
    }

    public static NewsResponseDto createThirdNewsResponseDto() {
        return new NewsResponseDto(THIRD_NEWS_ID, THIRD_NEWS_TITLE, THIRD_NEWS_TEXT,
                THIRD_NEWS_IMAGE, THIRD_NEWS_PUBLICATION_DATE, THIRD_NEWS_TYPE);
    }

    public static List<NewsResponseDto> createThreeNewsDtoList() {
        return List.of(createFirstNewsResponseDto(), createSecondNewsResponseDto(),
                createThirdNewsResponseDto());
    }
}
