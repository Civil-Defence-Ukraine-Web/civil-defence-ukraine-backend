package org.cdu.backend.util;

import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.cdu.backend.model.TeamMember;

import java.util.List;

public class TeamMemberUtil {
    public static final Long FIRST_MEMBER_ID = 1L;
    public static final String FIRST_MEMBER_NAME = "John Doe";
    public static final String FIRST_MEMBER_POSITION = "CEO";
    public static final String FIRST_MEMBER_DESCRIPTION = "Description for member 1";
    public static final String FIRST_MEMBER_IMAGE = "team_image_1.jpg";

    public static final Long SECOND_MEMBER_ID = 2L;
    public static final String SECOND_MEMBER_NAME = "Jane Smith";
    public static final String SECOND_MEMBER_POSITION = "Marketing Manager";
    public static final String SECOND_MEMBER_DESCRIPTION = "Description for member 2";
    public static final String SECOND_MEMBER_IMAGE = "team_image_2.jpg";

    public static final Long THIRD_MEMBER_ID = 3L;
    public static final String THIRD_MEMBER_NAME = "Michael Johnson";
    public static final String THIRD_MEMBER_POSITION = "Software Engineer";
    public static final String THIRD_MEMBER_DESCRIPTION = "Description for member 3";
    public static final String THIRD_MEMBER_IMAGE = "team_image_3.jpg";

    public static TeamMemberCreateRequestDto createFirstMemberCreateRequestDto() {
        return new TeamMemberCreateRequestDto(FIRST_MEMBER_NAME, FIRST_MEMBER_POSITION,
                FIRST_MEMBER_DESCRIPTION, FIRST_MEMBER_IMAGE);
    }

    public static TeamMemberUpdateRequestDto createFirstMemberUpdateRequestDto() {
        return new TeamMemberUpdateRequestDto(FIRST_MEMBER_NAME, FIRST_MEMBER_POSITION,
                FIRST_MEMBER_DESCRIPTION, FIRST_MEMBER_IMAGE);
    }

    public static TeamMember createFirstTeamMember() {
        return new TeamMember().setId(FIRST_MEMBER_ID)
                .setName(FIRST_MEMBER_NAME).setPosition(FIRST_MEMBER_POSITION)
                .setDescription(FIRST_MEMBER_DESCRIPTION).setImage(FIRST_MEMBER_IMAGE);
    }

    public static TeamMember createSecondTeamMember() {
        return new TeamMember().setId(SECOND_MEMBER_ID)
                .setName(SECOND_MEMBER_NAME).setPosition(SECOND_MEMBER_POSITION)
                .setDescription(SECOND_MEMBER_DESCRIPTION).setImage(SECOND_MEMBER_IMAGE);
    }

    public static TeamMember createThirdTeamMember() {
        return new TeamMember().setId(THIRD_MEMBER_ID)
                .setName(THIRD_MEMBER_NAME).setPosition(THIRD_MEMBER_POSITION)
                .setDescription(THIRD_MEMBER_DESCRIPTION).setImage(THIRD_MEMBER_IMAGE);
    }

    public static TeamMemberResponseDto createFirstMemberResponseDto() {
        return new TeamMemberResponseDto(FIRST_MEMBER_ID, FIRST_MEMBER_NAME, FIRST_MEMBER_POSITION,
                FIRST_MEMBER_DESCRIPTION, FIRST_MEMBER_IMAGE);
    }

    public static TeamMemberResponseDto createSecondMemberResponseDto() {
        return new TeamMemberResponseDto(SECOND_MEMBER_ID, SECOND_MEMBER_NAME,
                SECOND_MEMBER_POSITION, SECOND_MEMBER_DESCRIPTION, SECOND_MEMBER_IMAGE);
    }

    public static TeamMemberResponseDto createThirdMemberResponseDto() {
        return new TeamMemberResponseDto(THIRD_MEMBER_ID, THIRD_MEMBER_NAME,
                THIRD_MEMBER_POSITION, THIRD_MEMBER_DESCRIPTION, THIRD_MEMBER_IMAGE);
    }

    public static List<TeamMemberResponseDto> createTeamMemberResponseDtoList() {
        return List.of(createFirstMemberResponseDto(), createSecondMemberResponseDto(),
                createThirdMemberResponseDto());
    }
}
