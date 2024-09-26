package org.cdu.backend.repository.news;

import org.cdu.backend.model.News;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAll(Specification<News> specification);
}
