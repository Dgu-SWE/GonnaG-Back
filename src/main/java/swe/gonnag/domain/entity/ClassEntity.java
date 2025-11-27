package swe.gonnag.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Class")
public class ClassEntity {

    @Id
    @Column(name = "cid", nullable = false)
    private String id; // 학수번호

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "credit", nullable = false)
    private Integer credit;

    @Column(name = "course", nullable = false)
    private Integer courseType; // 1:전필, 2:전선, 3:공교, 5:BSM

    @Column(name = "is_english", nullable = false)
    private Boolean isEnglish;

    // 선이수 과목의 학수번호 (화살표 꼬리가 시작되는 곳)
    @Column(name = "prerequisite_cid")
    private String prerequisite;

    @Builder
    public ClassEntity(String id, String name, Integer credit, Integer courseType, Boolean isEnglish, String prerequisite) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.courseType = courseType;
        this.isEnglish = isEnglish;
        this.prerequisite = prerequisite;
    }
}
