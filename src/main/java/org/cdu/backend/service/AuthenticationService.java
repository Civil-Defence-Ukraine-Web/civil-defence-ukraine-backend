package org.cdu.backend.service;

import org.cdu.backend.dto.user.UserLoginRequestDto;
import org.cdu.backend.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);
}
