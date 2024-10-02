package org.cdu.backend.dto.team.member;

public record TeamMemberResponseDto(Long id, String name, String position, String description,
                                    String image) {
}
