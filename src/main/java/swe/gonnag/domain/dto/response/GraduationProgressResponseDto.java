package swe.gonnag.domain.dto.response;

import lombok.Builder;

@Builder
public record GraduationProgressResponseDto(
        CreditInfo totalCredits, // 총 취득학점
        CreditInfo major,        // 전공
        CreditInfo core,         // 필수교양 (BSM 등)
        CreditInfo general       // 일반교양
) {
    @Builder
    public record CreditInfo(
            String label,   // "취득학점", "전공필수" 등
            int earned,     // 내가 딴 점수
            int total       // 목표 점수
    ) {}
}