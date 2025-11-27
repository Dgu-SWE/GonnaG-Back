package swe.gonnag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.gonnag.domain.entity.TranscriptEntity;
import swe.gonnag.domain.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface TranscriptRepository extends JpaRepository<TranscriptEntity, UUID> {
    // "이 학생(User)의 수강기록 다 가져와!"
    // N+1 문제 방지를 위해 나중에 @EntityGraph를 붙일 수도 있지만, 지금은 기본으로 갑니다.
    List<TranscriptEntity> findAllByUser(UserEntity user);
}