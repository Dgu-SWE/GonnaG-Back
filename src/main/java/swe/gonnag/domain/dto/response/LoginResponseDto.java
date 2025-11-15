package swe.gonnag.domain.dto.response;

// 응답으로 받는 JWT 토큰
public record LoginResponseDto(
        String accessToken
) { }
