package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.gonnag.domain.dto.MCP.*;
import swe.gonnag.domain.dto.MCP.DefaultResponseDto;
import swe.gonnag.domain.dto.response.CurriculumGuideResponseDto;
import swe.gonnag.domain.dto.response.MCPUserInfoResponseDto;
import swe.gonnag.domain.entity.AnnouncementEntity;
import swe.gonnag.domain.entity.ClassEntity;
import swe.gonnag.domain.entity.RequirementEntity;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.repository.AnnouncementRepository;
import swe.gonnag.repository.ClassRepository;
import swe.gonnag.repository.RequirementRepository;
import swe.gonnag.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MCPService {

    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final RequirementRepository requirementRepository;
    private final AnnouncementRepository announcementRepository;



    public DefaultResponseDto defaultMCP() {
        return new DefaultResponseDto("2025.11.12 생성 함수");
    }

    @Transactional(readOnly = true) // [중요] Lazy Loading을 위해 트랜잭션 유지 필수
    public MCPUserInfoResponseDto userInfoMCP(MCPRequestDto request) {

        // 1. 유저 조회
        UserEntity user = userRepository.findById(request.id())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 2. DTO 변환 (여기서 user.getTranscriptList()가 호출되며 수강 정보를 가져옴)
        return MCPUserInfoResponseDto.from(user);
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

    // 사용자의 전공에 해당하는 학업이수가이드 요청
    public CurriculumGuideResponseDto getCurriculumGuideMCP(MCPRequestDto userDto) {

        // user 정보 찾기
        UserEntity user = userRepository.findById(userDto.id())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Long programId = user.getProgramId();

        List<RequirementEntity> entities = requirementRepository.findAllByProgramId(programId);

        List<CurriculumGuideResponseDto.RequirementDto> dtoList = entities.stream()
                .map(CurriculumGuideResponseDto.RequirementDto::from)
                .toList();

        return CurriculumGuideResponseDto.of(programId, dtoList);
    }

    public List<AnnouncementsResponseDto> announcemetsMCP() {
        List<AnnouncementEntity> announcements = announcementRepository.findAll();

        return announcements.stream()
                .map(AnnouncementsResponseDto::from)
                .toList();
    }
}