package swe.gonnag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.gonnag.domain.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 학번(ID)으로 유저 찾기 (로그인, 유저정보조회용)
    Optional<UserEntity> findById(Long id);
}