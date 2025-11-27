package swe.gonnag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.gonnag.domain.entity.ProgramEntity;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {

    // 💡 핵심 쿼리: "컴퓨터공학과" + "심화과정" + "2021년도" 해당하는 프로그램 찾기
    Optional<ProgramEntity> findByDepartmentNameAndProgramNameAndBaseYear(
            String departmentName,
            String programName,
            Integer baseYear
    );
}