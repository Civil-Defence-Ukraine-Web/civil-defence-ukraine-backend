package org.cdu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.service.TeamMemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Team members management", description = "Endpoint for team members management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/public/team")
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
}
