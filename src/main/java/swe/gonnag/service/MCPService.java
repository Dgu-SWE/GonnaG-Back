package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swe.gonnag.domain.dto.MCP.ClassesInfoResponseDto;
import swe.gonnag.domain.dto.MCP.DefaultResponseDto;
import swe.gonnag.domain.dto.MCP.UserInfoRequestDto;
import swe.gonnag.domain.dto.response.UserResponseDto;
import swe.gonnag.domain.entity.ClassEntity;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.repository.ClassRepository;
import swe.gonnag.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MCPService {

    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    public DefaultResponseDto defaultMCP() {
        return new DefaultResponseDto("2025.11.12 생성 함수");
    }

    public UserResponseDto userInfoMCP(UserInfoRequestDto request) {
        UserEntity user = userRepository.findById(request.id()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDto.from(user);
    }

    // 수업 정보 요청
    public ClassesInfoResponseDto getClassesInfoMCP() {

        // 모든 수업 조회
        List<ClassEntity> allClasses = classRepository.findAll();

        List<ClassesInfoResponseDto.ClassInfo> allClassesInfo = allClasses
                .stream()
                .map(ClassesInfoResponseDto.ClassInfo::from)
                .toList();

        return ClassesInfoResponseDto.builder()
                .classInfoList(allClassesInfo).build();
    }
}
