package org.cdu.backend.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    public static final String SQL_WILDCARD = "%";

    String getKey();

    Specification<T> getSpecification(String param);
}
