package swe.gonnag.domain.dto.MCP;

import swe.gonnag.domain.entity.AnnouncementEntity;

import java.time.LocalDate;

public record AnnouncementsResponseDto(
    Long announcementId,
    Long originalId,
    String title,
    String link,
    LocalDate date,
    int viewCount,
    String major
) {
    public static AnnouncementsResponseDto from(AnnouncementEntity entity) {
        return new AnnouncementsResponseDto(
                entity.getAnnouncementId(),
                entity.getOriginalId(),
                entity.getTitle(),
                entity.getLink(),
                entity.getDate(),
                entity.getViewCount(),
                entity.getMajor()
        );
    }
}
