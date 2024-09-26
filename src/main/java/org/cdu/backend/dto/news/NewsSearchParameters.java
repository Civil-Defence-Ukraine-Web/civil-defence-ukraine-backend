package org.cdu.backend.dto.news;

import org.cdu.backend.model.News.NewsType;

public record NewsSearchParameters(String title, NewsType type) {
}
