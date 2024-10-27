package org.cdu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.fundraising.FundraisingResponseDto;
import org.cdu.backend.dto.news.NewsCreateRequestDto;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsUpdateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.cdu.backend.service.FundraisingService;
import org.cdu.backend.service.NewsService;
import org.cdu.backend.service.TeamMemberService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Admin functions", description = "Controller for content management (adding, "
        + "deleting, updating")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final NewsService newsService;
    private final FundraisingService fundraisingService;
    private final TeamMemberService teamMemberService;

    @Operation(summary = "Save news to database", description = "Save news to database")
    @PostMapping(path = "/news", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public NewsResponseDto saveNews(
            @RequestPart("requestDto") @Valid NewsCreateRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {

        return newsService.save(requestDto, image);
    }

    @Operation(summary = "Update news", description = "Update news by id and update "
            + "request body")
    @PutMapping(path = "/news/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public NewsResponseDto updateNews(
            @RequestPart("requestDto") NewsUpdateRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable Long id) {
        return newsService.update(id, requestDto, image);
    }

    @Operation(summary = "Delete news", description = "Delete news by id")
    @DeleteMapping("/news/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @Operation(summary = "Replace actual fundraising",
            description = "Replaces actual fundraising image")
    @PostMapping("/fundraising")
    public FundraisingResponseDto replaceActualFundraising(
            @RequestPart("image") MultipartFile image) {
        return fundraisingService.replaceActual(image);
    }

    @Operation(summary = "Save new member", description = "Saves new member to database")
    @PostMapping(path = "/team", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TeamMemberResponseDto saveTeamMember(
            @RequestPart("requestDto") @Valid TeamMemberCreateRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return teamMemberService.save(requestDto, image);
    }

    @Operation(summary = "Update existing member", description = "Updates existing member by id")
    @PutMapping(value = "/team/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TeamMemberResponseDto updateTeamMember(
            @PathVariable Long id,
            @RequestPart("requestDto") TeamMemberUpdateRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return teamMemberService.update(id, requestDto, image);
    }

    @Operation(summary = "Delete member", description = "Deletes member by id")
    @DeleteMapping("/team/{id}")
    void deleteTeamMember(@PathVariable Long id) {
        teamMemberService.deleteById(id);
    }
}
