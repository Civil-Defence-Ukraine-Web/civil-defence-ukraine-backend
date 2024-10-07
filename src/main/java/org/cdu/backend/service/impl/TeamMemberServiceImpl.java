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
import org.cdu.backend.service.ImageService;
import org.cdu.backend.service.TeamMemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberMapper teamMemberMapper;
    private final ImageService imageService;

    @Override
    public TeamMemberResponseDto save(TeamMemberCreateRequestDto requestDto, MultipartFile image) {
        TeamMember teamMember = teamMemberMapper.toModel(requestDto);
        String url = imageService.save(image, DropboxImageServiceImpl.ImageType.TEAM_MEMBER_IMAGE);
        teamMember.setImage(url);
        teamMemberRepository.save(teamMember);
        return teamMemberMapper.toResponseDto(teamMember);
    }

    @Override
    public TeamMemberResponseDto update(Long id, TeamMemberUpdateRequestDto requestDto,
                                        MultipartFile image) {
        TeamMember teamMember = teamMemberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No team member found with id: " + id));
        teamMemberMapper.updateTeamMemberFromRequestDto(requestDto, teamMember);
        String url = imageService.save(image, DropboxImageServiceImpl.ImageType.TEAM_MEMBER_IMAGE);
        teamMember.setImage(url);
        teamMemberRepository.save(teamMember);
        return teamMemberMapper.toResponseDto(teamMember);
    }

    @Override
    public TeamMemberResponseDto findById(Long id) {
        TeamMember teamMember = teamMemberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No team member found with id: " + id));
        return teamMemberMapper.toResponseDto(teamMember);
    }

    @Override
    public List<TeamMemberResponseDto> findAll(Pageable pageable) {
        return teamMemberRepository.findAll(pageable)
                .stream()
                .map(teamMemberMapper::toResponseDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        teamMemberRepository.deleteById(id);
    }
}
