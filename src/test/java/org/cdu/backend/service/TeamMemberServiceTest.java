package org.cdu.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.cdu.backend.exception.EntityNotFoundException;
import org.cdu.backend.mapper.TeamMemberMapper;
import org.cdu.backend.model.TeamMember;
import org.cdu.backend.repository.team.member.TeamMemberRepository;
import org.cdu.backend.service.impl.TeamMemberServiceImpl;
import org.cdu.backend.util.TeamMemberUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class TeamMemberServiceTest {
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private TeamMemberMapper teamMemberMapper;

    @InjectMocks
    private TeamMemberServiceImpl teamMemberService;

    @DisplayName("""
            Verify save() method works correctly and returns correct DTO after saving
            """)
    @Test
    void save_WithValidCreateRequestDto_ShouldReturnValidDto() {
        TeamMemberCreateRequestDto requestDto = TeamMemberUtil.createFirstMemberCreateRequestDto();
        TeamMember teamMember = TeamMemberUtil.createFirstTeamMember();
        TeamMemberResponseDto expected = TeamMemberUtil.createFirstMemberResponseDto();

        when(teamMemberMapper.toModel(requestDto)).thenReturn(teamMember);
        when(teamMemberRepository.save(teamMember)).thenReturn(teamMember);
        when(teamMemberMapper.toResponseDto(teamMember)).thenReturn(expected);

        TeamMemberResponseDto result = teamMemberService.save(requestDto);

        assertEquals(expected, result);
        verify(teamMemberRepository, times(1)).save(any());
        verify(teamMemberMapper, times(1)).toResponseDto(any());
        verify(teamMemberMapper, times(1)).toModel(any());
    }

    @DisplayName("""
            Verify update() method works correctly and returns correct DTO after updating
            """)
    @Test
    void update_WithValidUpdateRequestDto_ShouldReturnValidDto() {
        TeamMemberUpdateRequestDto requestDto = TeamMemberUtil.createFirstMemberUpdateRequestDto();
        TeamMember secondTeamMember = TeamMemberUtil.createSecondTeamMember();
        TeamMemberResponseDto expected = TeamMemberUtil.createSecondMemberResponseDto();

        when(teamMemberRepository.findById(secondTeamMember.getId()))
                .thenReturn(Optional.of(secondTeamMember));
        doAnswer(invocationOnMock -> {
            TeamMemberUpdateRequestDto invocationUpdateDto =
                    (TeamMemberUpdateRequestDto) invocationOnMock.getArguments()[0];
            TeamMember invocationTeamMember = (TeamMember) invocationOnMock.getArguments()[1];

            invocationTeamMember.setName(invocationUpdateDto.name());
            invocationTeamMember.setPosition(invocationUpdateDto.position());
            invocationTeamMember.setDescription(invocationUpdateDto.description());
            invocationTeamMember.setImage(invocationUpdateDto.image());

            return null;
        }).when(teamMemberMapper).updateTeamMemberFromRequestDto(requestDto, secondTeamMember);
        when(teamMemberRepository.save(secondTeamMember)).thenReturn(secondTeamMember);
        when(teamMemberMapper.toResponseDto(secondTeamMember)).thenReturn(expected);

        TeamMemberResponseDto result =
                teamMemberService.update(secondTeamMember.getId(), requestDto);
        assertEquals(expected, result);
        verify(teamMemberRepository, times(1)).findById(any());
        verify(teamMemberMapper, times(1))
                .updateTeamMemberFromRequestDto(any(), any());
        verify(teamMemberRepository, times(1)).save(any());
        verify(teamMemberMapper, times(1)).toResponseDto(any());
    }

    @DisplayName("""
            Verify findById() method works correctly and returns correct DTO
            """)
    @Test
    void findById_WithValidId_ShouldReturnValidDto() {
        TeamMember teamMember = TeamMemberUtil.createFirstTeamMember();
        TeamMemberResponseDto expected = TeamMemberUtil.createFirstMemberResponseDto();

        when(teamMemberRepository.findById(teamMember.getId())).thenReturn(Optional.of(teamMember));
        when(teamMemberMapper.toResponseDto(teamMember)).thenReturn(expected);

        TeamMemberResponseDto result = teamMemberService.findById(teamMember.getId());
        assertEquals(expected, result);
        verify(teamMemberRepository, times(1))
                .findById(teamMember.getId());
        verify(teamMemberMapper, times(1)).toResponseDto(any());
    }

    @DisplayName("""
            Verify findById() method works correctly and throws exception if id is invalid
            """)
    @Test
    void findById_WithInvalidId_ShouldThrowException() {
        Long id = TeamMemberUtil.FIRST_MEMBER_ID;

        when(teamMemberRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teamMemberService.findById(id));
        verify(teamMemberRepository, times(1)).findById(any());
        verify(teamMemberMapper, never()).toResponseDto(any());
    }

    @DisplayName("""
            Verify findAll() method works correctly and returns correct DTO list
            """)
    @Test
    void findAll_WithThreeMembersInDatabase_ShouldReturnThreeMembers() {
        TeamMember firstTeamMember = TeamMemberUtil.createFirstTeamMember();
        TeamMember secondTeamMember = TeamMemberUtil.createSecondTeamMember();
        TeamMember thirdTeamMember = TeamMemberUtil.createThirdTeamMember();

        List<TeamMember> teamMemberList =
                List.of(firstTeamMember, secondTeamMember, thirdTeamMember);

        TeamMemberResponseDto firstTeamMemberResponseDto
                = TeamMemberUtil.createFirstMemberResponseDto();
        TeamMemberResponseDto secondTeamMemberResponseDto
                = TeamMemberUtil.createSecondMemberResponseDto();
        TeamMemberResponseDto thirdTeamMemberResponseDto
                = TeamMemberUtil.createThirdMemberResponseDto();

        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<TeamMember> teamMemberPage = new PageImpl<>(teamMemberList);

        when(teamMemberRepository.findAll(pageable)).thenReturn(teamMemberPage);
        when(teamMemberMapper.toResponseDto(firstTeamMember))
                .thenReturn(firstTeamMemberResponseDto);
        when(teamMemberMapper.toResponseDto(secondTeamMember))
                .thenReturn(secondTeamMemberResponseDto);
        when(teamMemberMapper.toResponseDto(thirdTeamMember))
                .thenReturn(thirdTeamMemberResponseDto);

        List<TeamMemberResponseDto> expected = List.of(firstTeamMemberResponseDto,
                secondTeamMemberResponseDto, thirdTeamMemberResponseDto);

        List<TeamMemberResponseDto> result = teamMemberService.findAll(pageable);
        assertEquals(expected, result);
        verify(teamMemberRepository, times(1)).findAll(pageable);
        verify(teamMemberMapper, times(expected.size())).toResponseDto(any());
    }
}
