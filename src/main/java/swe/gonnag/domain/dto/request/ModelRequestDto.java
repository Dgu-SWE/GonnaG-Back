package swe.gonnag.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import java.util.List;

@Builder
public record ModelRequestDto(
        @JsonProperty("user_id")
        Long userId,
                              String model,
                              List<MessageDto> messages,
                              double temperature
) {
    @Builder
    public record MessageDto(String role, String content) {}
}
