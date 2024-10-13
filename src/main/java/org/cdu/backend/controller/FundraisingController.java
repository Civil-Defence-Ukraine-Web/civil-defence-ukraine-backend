package org.cdu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.fundraising.FundraisingResponseDto;
import org.cdu.backend.service.FundraisingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Controller for fundraising image management",
        description = "In that controller you can change fundraising image")
@RequiredArgsConstructor
@RestController
@RequestMapping("/public/fundraising")
public class FundraisingController {
    private final FundraisingService fundraisingService;

    @Operation(summary = "Get actual fundraising", description = "Returns actual fundraising")
    @GetMapping
    public FundraisingResponseDto getActual() {
        return fundraisingService.getActual();
    }
}
