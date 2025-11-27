package swe.gonnag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.gonnag.domain.entity.ProgramEntity;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {

    // 학과, 전공, 트랙, 기준연도 4가지 조건으로 조회 Department + Major + ProgramName + BaseYear
    Optional<ProgramEntity> findByDMPB(
            String departmentName,
            String major,
            String programName,
            Integer baseYear
    );
}