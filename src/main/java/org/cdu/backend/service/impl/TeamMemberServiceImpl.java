package org.cdu.backend.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.cdu.backend.exception.EntityNotFoundException;
import org.cdu.backend.mapper.TeamMemberMapper;
import org.cdu.backend.model.TeamMember;
import org.cdu.backend.repository.team.member.TeamMemberRepository;
import org.cdu.backend.service.TeamMemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberMapper teamMemberMapper;

    @Override
    public TeamMemberResponseDto save(TeamMemberCreateRequestDto requestDto) {
        TeamMember teamMember = teamMemberMapper.toModel(requestDto);
        teamMemberRepository.save(teamMember);
        return teamMemberMapper.toDto(teamMember);
    }

    @Override
    public TeamMemberResponseDto update(Long id, TeamMemberUpdateRequestDto requestDto) {
        TeamMember teamMember = teamMemberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No team member found with id: " + id));
        teamMemberMapper.updateTeamMemberFromRequestDto(requestDto, teamMember);
        teamMemberRepository.save(teamMember);
        return teamMemberMapper.toDto(teamMember);
    }

    @Override
    public TeamMemberResponseDto findById(Long id) {
        TeamMember teamMember = teamMemberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No team member found with id: " + id));
        return teamMemberMapper.toDto(teamMember);
    }

    @Override
    public List<TeamMemberResponseDto> findAll(Pageable pageable) {
        return teamMemberRepository.findAll(pageable)
                .stream()
                .map(teamMemberMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        teamMemberRepository.deleteById(id);
    }
}
