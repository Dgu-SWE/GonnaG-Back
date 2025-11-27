package swe.gonnag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.gonnag.domain.entity.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity, String> {
    // 학수번호(CID)로 수업 찾기 (기본 제공됨)
}