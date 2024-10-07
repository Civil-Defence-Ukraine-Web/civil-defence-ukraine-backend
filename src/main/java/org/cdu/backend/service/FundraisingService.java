package org.cdu.backend.service;

import org.cdu.backend.dto.fundraising.FundraisingResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface FundraisingService {
    FundraisingResponseDto getActual();

    FundraisingResponseDto replaceActual(MultipartFile image);
}
