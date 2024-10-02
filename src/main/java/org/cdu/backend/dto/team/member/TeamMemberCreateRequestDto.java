package org.cdu.backend.dto.team.member;

import jakarta.validation.constraints.NotBlank;

public record TeamMemberCreateRequestDto(@NotBlank String name, @NotBlank String position,
                                         String description, @NotBlank String image) {
}
