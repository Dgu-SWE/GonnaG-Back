package swe.gonnag.domain.dto.response;

import lombok.Builder;
import swe.gonnag.domain.entity.ChatMessageEntity;
import swe.gonnag.domain.enums.ChatRole;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ChatHistoryResponseDto(
        List<ChatMessageDto> history
) {
    // Entity List -> DTO List 변환용 팩토리 메서드
    public static ChatHistoryResponseDto from(List<ChatMessageEntity> entities) {
        List<ChatMessageDto> dtos = entities.stream()
                .map(ChatMessageDto::from)
                .toList();
        return new ChatHistoryResponseDto(dtos);
    }

    // 내부 DTO (메시지 단건)
    @Builder
    public record ChatMessageDto(
            Long messageId,
            ChatRole role,   // "USER" or "ASSISTANT"
            String content,  // 내용
            LocalDateTime createdAt // 생성일시
    ) {
        public static ChatMessageDto from(ChatMessageEntity entity) {
            return ChatMessageDto.builder()
                    .messageId(entity.getId())
                    .role(entity.getRole())
                    .content(entity.getContent())
                    .createdAt(entity.getCreatedAt())
                    .build();
        }
    }
}