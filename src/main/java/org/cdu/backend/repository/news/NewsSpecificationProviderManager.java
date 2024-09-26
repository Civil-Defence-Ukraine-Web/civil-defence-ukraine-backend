package org.cdu.backend.repository.news;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.exception.KeyNotFoundException;
import org.cdu.backend.model.News;
import org.cdu.backend.repository.SpecificationProvider;
import org.cdu.backend.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NewsSpecificationProviderManager implements SpecificationProviderManager<News> {
    private final List<SpecificationProvider<News>> newsSpecificationProviders;

    @Override
    public SpecificationProvider<News> getSpecificationProvider(String key) {
        return newsSpecificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new KeyNotFoundException("Can't find correct specification "
                        + "provider with key " + key));
    }
}
