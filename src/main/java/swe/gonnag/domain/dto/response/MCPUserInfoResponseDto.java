package swe.gonnag.domain.dto.response;

import swe.gonnag.domain.entity.ClassEntity;
import swe.gonnag.domain.entity.UserEntity;

import java.util.Collections;
import java.util.List;

public record MCPUserInfoResponseDto(
        Long id,
        String name,
        Integer year,
        String status,
        Integer earnedCredits,
        String department,
        String major,
        String track,
        Long programId,
        List<TakenCourseInfo> takenCourses,
        GraduationProgressResponseDto progress
) {
    // 내부 DTO
    public record TakenCourseInfo(
            String cid,        // 학수번호 (중복 추천 방지 핵심)
            String name,       // 과목명
            Integer credit,
            Integer courseType
    ) {
        public static TakenCourseInfo from(ClassEntity classEntity) {
            return new TakenCourseInfo(
                    classEntity.getId(),
                    classEntity.getName(),
                    classEntity.getCredit(),
                    classEntity.getCourseType()
            );
        }
    }

    // 팩토리 메서드 수정
    public static MCPUserInfoResponseDto of(UserEntity user, GraduationProgressResponseDto progress) {
        // 유저의 수강 기록(Transcript)을 DTO 리스트로 변환
        List<TakenCourseInfo> courses = user.getTranscriptList() == null
                ? Collections.emptyList()
                : user.getTranscriptList().stream()
                .map(transcript -> TakenCourseInfo.from(transcript.getClassInfo()))
                .toList();

        return new MCPUserInfoResponseDto(
                user.getId(),
                user.getName(),
                user.getYear(),
                user.getStatus(),
                user.getEarnedCredits(),
                user.getDepartment(),
                user.getMajor(),
                user.getTrack(),
                user.getProgramId(),
                courses,  // 상세 내역 주입
                progress  // 계산 결과 주입
        );
    }
}