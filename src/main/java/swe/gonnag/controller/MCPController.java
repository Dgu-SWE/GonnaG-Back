package swe.gonnag.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import swe.gonnag.domain.dto.response.MCP.DefaultResponseDto;
import swe.gonnag.service.MCPService;

@RestController
public class MCPController {

    @Autowired
    private MCPService mcpService;

    @GetMapping("/mcp")
    public DefaultResponseDto defaultMCP () {
        return mcpService.defaultMCP();
    }

}
