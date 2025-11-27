package swe.gonnag.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Users") // 예약어 충돌 방지
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id; // 학번 (PK) - 입력값을 그대로 사용

    @Column(name = "pwd", nullable = false)
    private String pwd;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "year")
    private Integer year; // 학년

    @Column(name = "status")
    private String status; // 재학여부 (재학/휴학 등)

    @Column(name = "earned_credits")
    private Integer earnedCredits; // 총 취득 학점

    @Column(name = "department")
    private String department; // 소속 학부

    @Column(name = "major")
    private String major; // 소속 전공

    @Column(name = "track")
    private String track; // 트랙 (추가됨: 심화/일반 등)

    // --- 양방향 매핑 (DB 컬럼 아님, 자바에서 조회 편의성 제공) ---
    // 유저가 삭제되면 수강기록도 같이 삭제됨 (CascadeType.ALL)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TranscriptEntity> transcriptList = new ArrayList<>();

    // 테스트 및 회원가입용 빌더 (필요 시 사용)
    @lombok.Builder
    public UserEntity(Long id, String pwd, String name, Integer year, String status, Integer earnedCredits, String department, String major, String track) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.year = year;
        this.status = status;
        this.earnedCredits = earnedCredits;
        this.department = department;
        this.major = major;
        this.track = track;
    }
}