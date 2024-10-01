package org.cdu.backend.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FormRequestDto(@NotBlank @Email String email, @NotBlank String subject,
                             @NotBlank String message, @NotBlank boolean isVolunteer) {
}
