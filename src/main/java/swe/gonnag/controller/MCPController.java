package swe.gonnag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swe.gonnag.domain.dto.MCP.AnnouncementsResponseDto;
import swe.gonnag.domain.dto.MCP.ClassesInfoResponseDto;
import swe.gonnag.domain.dto.MCP.DefaultResponseDto;
import swe.gonnag.domain.dto.MCP.UserInfoRequestDto;
import swe.gonnag.domain.dto.response.CurriculumGuideResponseDto;
import swe.gonnag.domain.dto.MCP.MCPRequestDto;
import swe.gonnag.domain.dto.response.UserResponseDto;
import swe.gonnag.service.MCPService;

import java.util.List;

@RestController
@RequestMapping("/mcp")
@RequiredArgsConstructor
public class MCPController {

    private final MCPService mcpService;

    @GetMapping
    public DefaultResponseDto defaultMCP () {
        return mcpService.defaultMCP();
    }


    @PostMapping("/user-info")
    public UserResponseDto userInfoMCP(@RequestBody MCPRequestDto request) {
        return mcpService.userInfoMCP(request);
    }

    // 수업 정보 조회
    @GetMapping("/classes")
    public ClassesInfoResponseDto classesInfoMCP() {
        return mcpService.getClassesInfoMCP();
    }

    @PostMapping("/guide")
    public CurriculumGuideResponseDto guideMCP(@RequestBody UserInfoRequestDto request) {
        return mcpService.getCurriculumGuideMCP(request);
    }

    @GetMapping("/announcements")
    public List<AnnouncementsResponseDto> announcemetsMCP() {
        return mcpService.getAnnouncementsMCP();
    }
}
