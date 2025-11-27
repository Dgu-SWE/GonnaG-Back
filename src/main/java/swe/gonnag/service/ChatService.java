package swe.gonnag.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import swe.gonnag.domain.dto.request.ModelRequestDto;
import swe.gonnag.domain.dto.response.ChatHistoryResponseDto;
import swe.gonnag.domain.dto.response.ModelResponseDto;
import swe.gonnag.domain.entity.ChatMessageEntity;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.domain.enums.ChatRole;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.repository.ChatMessageRepository;
import swe.gonnag.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    // 모델 서버 URL
    private static final String MODEL_SERVER_URL = "https://port-0-gonnag-chat-mihqm6p4c9febe90.sel3.cloudtype.app/api/chat";

    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public ChatHistoryResponseDto sendMsg(Long userId, String msg) {

        // 1. 유저 조회
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 2. 사용자 메시지 DB 저장
        ChatMessageEntity userMessage = ChatMessageEntity.builder()
                .user(user)
                .role(ChatRole.USER)
                .content(msg)
                .build();
        chatMessageRepository.save(userMessage);

        // 3. 전체 대화 기록 조회 (Context 구성용)
        List<ChatMessageEntity> chatHistory = chatMessageRepository.findAllByUserIdOrderByCreatedAtAsc(userId);

        // 4. 모델 서버 요청 데이터(DTO) 생성
        List<ModelRequestDto.MessageDto> messagesPayload = chatHistory.stream()
                .map(message -> new ModelRequestDto.MessageDto(
                        message.getRole().name().toLowerCase(),
                        message.getContent()
                ))
                .toList();

        ModelRequestDto requestPayload = ModelRequestDto.builder()
                .userId(user.getId())
                .model("gpt-4o")
                .messages(messagesPayload)
                .temperature(0.7)
                .build();

        // 5. [디버깅] 전송 직전 JSON 데이터 로그 출력
        try {
            String jsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestPayload);
            log.info("🚀 [Spring -> ModelServer] Request Body:\n{}", jsonBody);
        } catch (JsonProcessingException e) {
            log.error("⚠️ JSON 변환 에러", e);
        }

        // 6. 모델 서버 호출 및 응답 처리
        try {
            RestClient restClient = RestClient.create();
            ModelResponseDto modelResponse = restClient.post()
                    .uri(MODEL_SERVER_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestPayload)
                    .retrieve()
                    .body(ModelResponseDto.class);

            if (modelResponse != null) {
                log.info("✅ [ModelServer Response]: {}", modelResponse.content());

                // 7. 어시스턴트 메시지 DB 저장
                ChatMessageEntity botMessage = ChatMessageEntity.builder()
                        .user(user)
                        .role(ChatRole.ASSISTANT)
                        .content(modelResponse.content())
                        .build();
                chatMessageRepository.save(botMessage);

                // 반환할 리스트에 추가 (DB 재조회 방지)
                chatHistory.add(botMessage);
            }

        } catch (HttpClientErrorException e) {
            log.error("🚨 모델 서버 에러 (HTTP {}): {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CustomException(ErrorCode.SERVER_ERROR);
        } catch (Exception e) {
            log.error("🚨 내부 시스템 에러", e);
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }

        // 8. 최종 채팅 기록 반환
        return ChatHistoryResponseDto.from(chatHistory);
    }
}