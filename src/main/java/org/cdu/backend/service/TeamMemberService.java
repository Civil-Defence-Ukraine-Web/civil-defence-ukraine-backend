package org.cdu.backend.service;

import java.util.List;
import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface TeamMemberService {
    TeamMemberResponseDto save(TeamMemberCreateRequestDto requestDto, MultipartFile image);

    TeamMemberResponseDto update(Long id, TeamMemberUpdateRequestDto requestDto,
                                 MultipartFile image);

    TeamMemberResponseDto findById(Long id);

    List<TeamMemberResponseDto> findAll(Pageable pageable);

    List<TeamMemberResponseDto> findAll();

    void deleteById(Long id);
}
