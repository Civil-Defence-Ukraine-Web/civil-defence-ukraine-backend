package org.cdu.backend.repository;

import org.cdu.backend.dto.news.NewsSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(NewsSearchParameters searchParameters);
}