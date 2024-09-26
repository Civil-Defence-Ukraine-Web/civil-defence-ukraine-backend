package org.cdu.backend.repository.news;

import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.news.NewsSearchParameters;
import org.cdu.backend.model.News;
import org.cdu.backend.repository.SpecificationBuilder;
import org.cdu.backend.repository.SpecificationProviderManager;
import org.cdu.backend.repository.news.spec.TitleSpecificationProvider;
import org.cdu.backend.repository.news.spec.TypeSpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NewsSpecificationBuilder implements SpecificationBuilder<News> {
    private final SpecificationProviderManager<News> specificationProviderManager;

    @Override
    public Specification<News> build(NewsSearchParameters searchParameters) {
        Specification<News> specification = Specification.where(null);

        if(searchParameters.title() != null) {
            specification = specification.and(specificationProviderManager
                            .getSpecificationProvider(TitleSpecificationProvider.KEY_TITLE)
                            .getSpecification(searchParameters.title()));
        }
        if(searchParameters.type() != null) {
            specification = specification.and(specificationProviderManager
                            .getSpecificationProvider(TypeSpecificationProvider.KEY_TYPE)
                            .getSpecification(String.valueOf(searchParameters.type())));
        }
        return specification;
    }
}
