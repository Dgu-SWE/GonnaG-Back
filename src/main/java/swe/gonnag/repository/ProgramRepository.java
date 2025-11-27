package swe.gonnag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swe.gonnag.domain.entity.ProgramEntity;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {

    // 메서드 이름이 findByDMBP 이든 findByKimchi 이든 상관없습니다. @Query가 우선입니다.

    @Query("SELECT p FROM ProgramEntity p " +
            "WHERE p.departmentName = :dept " +
            "AND p.major = :major " +
            "AND p.baseYear = :year " +
            "AND p.programName = :track")
    Optional<ProgramEntity> findByDMBP(
            @Param("dept") String departmentName,
            @Param("major") String major,
            @Param("year") Integer baseYear,
            @Param("track") String programName
    );
}