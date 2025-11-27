package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.gonnag.domain.dto.response.GraduationProgressResponseDto;
import swe.gonnag.domain.dto.response.UserInfoResponseDto;
import swe.gonnag.domain.entity.ProgramEntity;
import swe.gonnag.domain.entity.RequirementEntity;
import swe.gonnag.domain.entity.TranscriptEntity;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.domain.enums.CourseType;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.repository.ProgramRepository;
import swe.gonnag.repository.RequirementRepository;
import swe.gonnag.repository.TranscriptRepository;
import swe.gonnag.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetUserInfoService {

    private final UserRepository userRepository;
    private final TranscriptRepository transcriptRepository;
    private final ProgramRepository programRepository;
    private final RequirementRepository requirementRepository;

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserInfo(Long userId) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserInfoResponseDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getYear(),
                userEntity.getStatus(),
                userEntity.getEarnedCredits(),
                userEntity.getDepartment(),
                userEntity.getMajor(),
                userEntity.getTrack()
        );
    }

    public GraduationProgressResponseDto getGraduationProgress(Long userId) {
        // 1. 유저 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 2. 수강 기록 조회
        List<TranscriptEntity> transcripts = transcriptRepository.findAllByUser(user);

        // 3. 내 취득 학점 계산 (earnedMap 구성)
        Map<String, Integer> earnedMap = new HashMap<>();
        int totalEarned = 0;

        for (TranscriptEntity t : transcripts) {
            if (t.getClassInfo() != null) {
                int credits = t.getClassInfo().getCredit();
                int courseCode = t.getClassInfo().getCourseType();

                // Enum을 통해 라벨 변환 ("전공필수", "전공선택", "필수교양", "일반교양" 등)
                String label = CourseType.getLabelByCode(courseCode);

                earnedMap.put(label, earnedMap.getOrDefault(label, 0) + credits);
                totalEarned += credits;
            }
        }

        // 4. 목표 기준(Requirement) 가져오기
        int entryYear = Integer.parseInt(String.valueOf(user.getId()).substring(0, 4));

        ProgramEntity program = programRepository.findByDepartmentNameAndProgramNameAndBaseYear(
                user.getDepartment(),
                user.getTrack(),
                entryYear
        ).orElseThrow(() -> new IllegalArgumentException("해당하는 졸업 요건 프로그램이 없습니다."));

        List<RequirementEntity> requirements = requirementRepository.findAllByProgram(program);

        // ---------------------------------------------------------
        // [핵심 수정] 5. 목표 학점 추출 및 '일반교양' 잔여량 계산
        // ---------------------------------------------------------

        // 5-1. DB에 명시된 필수 목표 학점들 가져오기
        int targetTotal = getTargetCredit(requirements, "취득학점");
        if (targetTotal == 0) targetTotal = 140; // 기본값 안전장치

        int targetMajor = getTargetCredit(requirements, "전공");     // 예: 84 or 78
        int targetCore = getTargetCredit(requirements, "필수교양");  // 예: 16
        int targetBSM = getTargetCredit(requirements, "BSM");       // 예: 18 (계산엔 필요하지만 출력은 안함)

        // 5-2. 일반교양 목표 = 전체 - (전공 + 필수교양 + BSM)
        // (DB에 '일반교양' Row가 없어도 자동으로 계산됨)
        int targetGeneral = targetTotal - (targetMajor + targetCore + targetBSM);
        if (targetGeneral < 0) targetGeneral = 0; // 음수 방지

        // 6. DTO 조립
        return GraduationProgressResponseDto.builder()
                .totalCredits(new GraduationProgressResponseDto.CreditInfo("취득학점", totalEarned, targetTotal))

                // 전공 (내 전공필수 + 전공선택 합산)
                .major(new GraduationProgressResponseDto.CreditInfo("전공",
                        earnedMap.getOrDefault("전공필수", 0) + earnedMap.getOrDefault("전공선택", 0),
                        targetMajor))

                // 필수교양
                .core(new GraduationProgressResponseDto.CreditInfo("필수교양",
                        earnedMap.getOrDefault("필수교양", 0),
                        targetCore))

                // [변경] 계산된 targetGeneral 변수 사용
                .general(new GraduationProgressResponseDto.CreditInfo("일반교양",
                        earnedMap.getOrDefault("일반교양", 0),
                        targetGeneral))

                .build();
    }

    // 헬퍼 메서드: 목표 학점 찾기
    private int getTargetCredit(List<RequirementEntity> reqs, String category) {
        return reqs.stream()
                .filter(r -> r.getCategory().equals(category) && "CREDIT".equals(r.getType()))
                .findFirst()
                .map(r -> Integer.parseInt(r.getValue()))
                .orElse(0);
    }

    // 헬퍼 메서드: CreditInfo 객체 생성 (목표 vs 획득)
    private GraduationProgressResponseDto.CreditInfo createCreditInfo(List<RequirementEntity> reqs, Map<String, Integer> earnedMap, String category) {
        int target = getTargetCredit(reqs, category);
        int earned = earnedMap.getOrDefault(category, 0);
        return new GraduationProgressResponseDto.CreditInfo(category, earned, target);
    }
}
