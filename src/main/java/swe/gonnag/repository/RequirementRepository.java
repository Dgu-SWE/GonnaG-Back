package swe.gonnag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.gonnag.domain.entity.ProgramEntity;
import swe.gonnag.domain.entity.RequirementEntity;
import swe.gonnag.domain.entity.UserEntity;

import java.util.List;

public interface RequirementRepository extends JpaRepository<RequirementEntity, Long> {

    // "이 프로그램(졸업가이드)에 해당하는 요건 다 가져와!"
    List<RequirementEntity> findAllByProgram(ProgramEntity program);

    List<RequirementEntity> findAllByProgramId(Long programId);
}