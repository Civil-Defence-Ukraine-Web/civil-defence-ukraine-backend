package org.cdu.backend.repository.team.member;

import org.cdu.backend.model.TeamMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Page<TeamMember> findAll(Pageable pageable);
}
