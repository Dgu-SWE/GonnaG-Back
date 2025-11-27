package swe.gonnag.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder; // Builder 추가

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Program")
public class ProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String departmentName; // 단과대 or 학부 (예: 공과대학, AI소프트웨어융합학부)

    @Column(name = "major", nullable = false)
    private String major;          // 전공 (예: 컴퓨터공학전공, 컴퓨터·AI학부)

    @Column(name = "program_name", nullable = false)
    private String programName;    // 과정/트랙 (예: 심화과정)

    @Column(name = "base_year", nullable = false)
    private Integer baseYear;      // 기준연도

    @Builder
    public ProgramEntity(String departmentName, String major, String programName, Integer baseYear) {
        this.departmentName = departmentName;
        this.major = major;
        this.programName = programName;
        this.baseYear = baseYear;
    }
}