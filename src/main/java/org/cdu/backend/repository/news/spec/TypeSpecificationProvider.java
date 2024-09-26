package org.cdu.backend.repository.news.spec;

import org.cdu.backend.model.News;
import org.cdu.backend.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TypeSpecificationProvider implements SpecificationProvider<News> {
    public static final String KEY_TYPE = "type";

    @Override
    public String getKey() {
        return KEY_TYPE;
    }

    @Override
    public Specification<News> getSpecification(String param) {
        String pattern = SQL_WILDCARD + param + SQL_WILDCARD;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(KEY_TYPE)),
                        pattern.toLowerCase());
    }
}
