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
        // [추가] 수강한 과목 리스트
        List<TakenCourseInfo> takenCourses
) {
    // 내부 DTO: 수강한 강의 정보 요약
    public record TakenCourseInfo(
            String cid,        // 학수번호
            String name,       // 과목명
            Integer credit,    // 학점
            Integer courseType // 이수구분 (1:전필, 2:전선 등)
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

    // Entity -> DTO 변환 메서드
    public static MCPUserInfoResponseDto from(UserEntity user) {
        // 유저가 들은 수업(Transcript) 목록을 순회하며 DTO 리스트로 변환
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
                courses // 변환된 리스트 주입
        );
    }
}