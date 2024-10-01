package org.cdu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.form.FormRequestDto;
import org.cdu.backend.service.FormService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/form")
public class FormController {
    private final FormService formService;

    @Operation(summary = "Send form", description = "Send form to corporate mail")
    @PostMapping
    public void save(@RequestBody @Valid FormRequestDto requestDto) {
        formService.sendForm(requestDto);
    }
}
