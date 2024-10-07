package org.cdu.backend.repository.fundraising;

import org.cdu.backend.model.Fundraising;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundraisingRepository extends JpaRepository<Fundraising, Long> {
}
