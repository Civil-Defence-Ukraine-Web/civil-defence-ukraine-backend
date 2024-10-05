package org.cdu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.cdu.backend.service.TeamMemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Team members management", description = "Endpoint for team members management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/team")
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    @Operation(summary = "Find all members", description = "Returns all members")
    @GetMapping
    List<TeamMemberResponseDto> findAll(Pageable pageable) {
        return teamMemberService.findAll(pageable);
    }

    @Operation(summary = "Find member by id", description = "Returns member with needed id")
    @GetMapping("/{id}")
    TeamMemberResponseDto findById(@PathVariable Long id) {
        return teamMemberService.findById(id);
    }

    @Operation(summary = "Save new member", description = "Saves new member to database")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TeamMemberResponseDto save(
            @RequestPart("requestDto") @Valid TeamMemberCreateRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return teamMemberService.save(requestDto, image);
    }

    @Operation(summary = "Update existing member", description = "Updates existing member by id")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TeamMemberResponseDto update(
            @PathVariable Long id,
            @RequestPart("requestDto") TeamMemberUpdateRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return teamMemberService.update(id, requestDto, image);
    }

    @Operation(summary = "Delete member", description = "Deletes member by id")
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        teamMemberService.deleteById(id);
    }
}
