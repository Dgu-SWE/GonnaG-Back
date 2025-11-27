package swe.gonnag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import swe.gonnag.domain.dto.MCP.DefaultResponseDto;
import swe.gonnag.domain.dto.MCP.UserInfoRequestDTO;
import swe.gonnag.domain.dto.response.UserResponseDto;
import swe.gonnag.service.MCPService;

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
    public UserResponseDto userInfoMCP(@RequestBody UserInfoRequestDTO request) {
        return mcpService.userInfoMCP(request);
    }

}
