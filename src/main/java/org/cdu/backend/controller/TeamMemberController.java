package org.cdu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.cdu.backend.service.TeamMemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping
    TeamMemberResponseDto save(@RequestBody TeamMemberCreateRequestDto requestDto) {
        return teamMemberService.save(requestDto);
    }

    @Operation(summary = "Update existing member", description = "Updates existing member by id")
    @PutMapping("/{id}")
    TeamMemberResponseDto update(@PathVariable Long id,
                                 @RequestBody TeamMemberUpdateRequestDto requestDto) {
        return teamMemberService.update(id,requestDto);
    }

    @Operation(summary = "Delete member", description = "Deletes member by id")
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        teamMemberService.deleteById(id);
    }
}
