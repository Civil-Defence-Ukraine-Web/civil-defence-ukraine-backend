package org.cdu.backend.service;

import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamMemberService {
    TeamMemberResponseDto save(TeamMemberCreateRequestDto requestDto);

    TeamMemberResponseDto update(Long id, TeamMemberUpdateRequestDto requestDto);

    TeamMemberResponseDto findById(Long id);

    List<TeamMemberResponseDto> findAll(Pageable pageable);

    void deleteById(Long id);
}
