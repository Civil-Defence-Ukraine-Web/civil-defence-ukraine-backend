package org.cdu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.fundraising.FundraisingResponseDto;
import org.cdu.backend.service.FundraisingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Controller for fundraising image management",
        description = "In that controller you can change fundraising image")
@RequiredArgsConstructor
@RestController
@RequestMapping("/fundraising")
public class FundraisingController {
    private final FundraisingService fundraisingService;

    @Operation(summary = "Get actual fundraising", description = "Returns actual fundraising")
    @GetMapping
    public FundraisingResponseDto getActual() {
        return fundraisingService.getActual();
    }

    @Operation(summary = "Replace actual fundraising",
            description = "Replaces actual fundraising image")
    @PostMapping
    public FundraisingResponseDto replaceActual(@RequestPart("image") MultipartFile image) {
        return fundraisingService.replaceActual(image);
    }
}
