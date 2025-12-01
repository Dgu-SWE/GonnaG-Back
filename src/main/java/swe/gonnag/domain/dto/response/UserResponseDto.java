package swe.gonnag.domain.dto.response;

import lombok.Builder;
import swe.gonnag.domain.entity.UserEntity;

@Builder
public record UserResponseDto(
        Long id,
        String name,
        Integer year,
        String department,
        String major,
        String track, // 심화/일반 트랙 정보 포함
        Long programId
) {
    public static UserResponseDto from(UserEntity user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .year(user.getYear())
                .department(user.getDepartment())
                .major(user.getMajor())
                .track(user.getTrack())
                .programId(user.getProgramId())
                .build();
    }
}