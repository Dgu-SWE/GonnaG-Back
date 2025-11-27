package swe.gonnag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.gonnag.domain.entity.UserEntity;

// JpaRepository<[엔티티], [PK 타입]>
public interface UserRepository extends JpaRepository<UserEntity, Long> { }