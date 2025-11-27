package swe.gonnag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe.gonnag.domain.entity.ChatMessageEntity;
import swe.gonnag.domain.entity.UserEntity;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    // 특정 유저의 메시지를 생성된 순서대로 조회 (과거 -> 현재)
    List<ChatMessageEntity> findAllByUserOrderByCreatedAtAsc(UserEntity user);
}