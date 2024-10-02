package org.cdu.backend.service;

import java.util.List;
import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.springframework.data.domain.Pageable;

public interface TeamMemberService {
    TeamMemberResponseDto save(TeamMemberCreateRequestDto requestDto);

    TeamMemberResponseDto update(Long id, TeamMemberUpdateRequestDto requestDto);

    TeamMemberResponseDto findById(Long id);

    List<TeamMemberResponseDto> findAll(Pageable pageable);

    void deleteById(Long id);
}
