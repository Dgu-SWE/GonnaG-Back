package swe.gonnag.service;

import org.springframework.stereotype.Service;
import swe.gonnag.domain.dto.response.MCP.DefaultResponseDto;

@Service
public class MCPService {
    public DefaultResponseDto defaultMCP() {
        return new DefaultResponseDto("2025.11.12 생성 함수");
    }
}
