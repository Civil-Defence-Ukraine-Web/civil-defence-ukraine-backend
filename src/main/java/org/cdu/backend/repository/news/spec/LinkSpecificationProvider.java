package org.cdu.backend.repository.news.spec;

import org.cdu.backend.model.News;
import org.cdu.backend.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class LinkSpecificationProvider implements SpecificationProvider<News> {
    public static final String KEY_LINK = "link";

    @Override
    public String getKey() {
        return KEY_LINK;
    }

    @Override
    public Specification<News> getSpecification(String param) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(KEY_LINK)),
                        param.toLowerCase());
    }
}
