package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.gonnag.domain.dto.MCP.*;
import swe.gonnag.domain.dto.MCP.DefaultResponseDto;
import swe.gonnag.domain.dto.response.CurriculumGuideResponseDto;
import swe.gonnag.domain.dto.response.GraduationProgressResponseDto;
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
import swe.gonnag.service.GetUserInfoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MCPService {

    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final RequirementRepository requirementRepository;
    private final AnnouncementRepository announcementRepository;
    private final GetUserInfoService getUserInfoService;



    public DefaultResponseDto defaultMCP() {
        return new DefaultResponseDto("2025.11.12 생성 함수");
    }

    @Transactional(readOnly = true)
    public MCPUserInfoResponseDto userInfoMCP(MCPRequestDto request) {
        // 1. 유저 조회
        UserEntity user = userRepository.findById(request.id())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 2. 졸업 요건 계산 로직 호출 (수강 기록을 바탕으로 계산)
        GraduationProgressResponseDto progress = getUserInfoService.getGraduationProgress(request.id());

        // 3. 유저 정보 + 수강 목록 + 계산 결과를 한 번에 묶어서 반환
        return MCPUserInfoResponseDto.of(user, progress);
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