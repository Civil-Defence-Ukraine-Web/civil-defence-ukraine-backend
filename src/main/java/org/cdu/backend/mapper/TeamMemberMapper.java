package org.cdu.backend.mapper;

import org.cdu.backend.config.MapperConfig;
import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.cdu.backend.model.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface TeamMemberMapper {
    TeamMember toModel(TeamMemberCreateRequestDto requestDto);

    TeamMemberResponseDto toResponseDto(TeamMember teamMember);

    void updateTeamMemberFromRequestDto(TeamMemberUpdateRequestDto requestDto,
                                        @MappingTarget TeamMember teamMember);
}
