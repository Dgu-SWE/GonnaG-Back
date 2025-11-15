package swe.gonnag.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Users") // USER는 예약어
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id; // 학번

    @Column(name = "pwd", nullable = false)
    private String pwd; // 암호화된 비밀번호

    @Column(name = "name", nullable = false)
    private String name; // 이름

    @Column(name = "year")
    private Integer year; // 학년

    @Column(name = "status")
    private String status; // 재학여부

    @Column(name = "earned_credits")
    private Integer earnedCredits; // 학점

    @Column(name = "department")
    private String department; // 학부, 소속 대학

    @Column(name = "major")
    private String major; // 전공
}