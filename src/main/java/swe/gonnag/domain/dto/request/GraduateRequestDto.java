package swe.gonnag.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record GraduateRequestDto(
        @NotNull Long userId // 챗봇이 누구 정보를 조회할지 넘겨줌
) {}