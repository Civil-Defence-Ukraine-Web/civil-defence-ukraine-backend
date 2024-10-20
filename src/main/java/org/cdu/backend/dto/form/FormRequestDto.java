package org.cdu.backend.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FormRequestDto(@NotBlank @Email String email, @NotBlank String subject,
                             @NotBlank String message, @NotNull boolean isVolunteer) {
}
