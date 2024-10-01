package org.cdu.backend.service;

import org.cdu.backend.dto.form.FormRequestDto;

public interface FormService {
    void sendForm(FormRequestDto requestDto);
}
