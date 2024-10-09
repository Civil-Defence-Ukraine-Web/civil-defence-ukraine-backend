package org.cdu.backend.repository.news;

import java.time.LocalDateTime;
import java.util.List;
import org.cdu.backend.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAll(Specification<News> specification);

    Page<News> findAll(Pageable pageable);

    Page<News> findNewsByPublicationDateAfter(LocalDateTime date, Pageable pageable);
}
