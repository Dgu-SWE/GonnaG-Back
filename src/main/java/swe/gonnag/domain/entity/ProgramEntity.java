package swe.gonnag.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Program")
public class ProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private Long id; // PK

    @Column(name = "department_name", nullable = false)
    private String departmentName; // 학과명 (예: 컴퓨터·AI학부)

    @Column(name = "program_name", nullable = false)
    private String programName; // 과정명 (예: 심화과정, 일반과정)

    @Column(name = "base_year", nullable = false)
    private Integer baseYear; // 기준연도 (입학년도 기준)
}