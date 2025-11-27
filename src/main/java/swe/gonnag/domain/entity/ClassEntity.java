package swe.gonnag.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Class")
public class ClassEntity {

    @Id
    @Column(name = "cid", nullable = false)
    private String id; // 학수번호 (PK)

    @Column(name = "name", nullable = false)
    private String name; // 과목명

    @Column(name = "credit", nullable = false)
    private Integer credit; // 학점

    // 추후 Enum으로 관리하여 Requirement와 매핑할 필드
    @Column(name = "course", nullable = false)
    private Integer courseType; // 교과과정 구분 (예: 1=전필, 2=일교)

    @Column(name = "is_english", nullable = false)
    private Boolean isEnglish; // 영어강의 여부
}