package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.gonnag.domain.dto.response.TranscriptResponseDto;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.repository.TranscriptRepository;
import swe.gonnag.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 조회 전용
public class CourseHistoryService {

    private final UserRepository userRepository;
    private final TranscriptRepository transcriptRepository;

    // 수강과목 조회
    public List<TranscriptResponseDto> getCourseHistory(Long userId) {
        // 1. 유저 검증
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 2. 수강 기록 조회 및 변환
        return transcriptRepository.findAllByUser(user).stream()
                .map(TranscriptResponseDto::from)
                .toList();
    }
}