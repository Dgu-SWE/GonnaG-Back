package swe.gonnag.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Requirement")
public class RequirementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Long id; // PK

    // N:1 관계 - Program (졸업 가이드)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_pid", nullable = false)
    private ProgramEntity program;

    @Column(name = "category", nullable = false)
    private String category; // 카테고리 (예: 전공필수, 공통교양)

    @Column(name = "value", nullable = false)
    private String value; // 요구값 (예: "84"(학점) or "CSI101"(특정과목))

    @Column(name = "type", nullable = false)
    private String type; // 값 유형 (예: "CREDIT", "COURSE") - 나중에 파싱 로직에 사용

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 상세 설명
}