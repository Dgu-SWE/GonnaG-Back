package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swe.gonnag.domain.dto.MCP.DefaultResponseDto;
import swe.gonnag.domain.dto.MCP.UserInfoRequestDTO;
import swe.gonnag.domain.dto.response.UserResponseDto;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MCPService {

    private final UserRepository userRepository;

    public DefaultResponseDto defaultMCP() {
        return new DefaultResponseDto("2025.11.12 생성 함수");
    }

    public UserResponseDto userInfoMCP(UserInfoRequestDTO request) {
        UserEntity user = userRepository.findById(request.id()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDto.from(user);
    }
}
