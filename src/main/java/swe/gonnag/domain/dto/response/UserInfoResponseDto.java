package swe.gonnag.domain.dto.response;

import java.util.List;

public record UserInfoResponseDto(
        Long id,
        String name,
        Integer year,
        String status,
        Integer earnedCredits,
        String department,
        String major,
        String track,
        Long programId
) {
}
