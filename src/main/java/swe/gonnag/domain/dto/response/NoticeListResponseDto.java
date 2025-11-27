package swe.gonnag.domain.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;
import swe.gonnag.domain.entity.AnnouncementEntity;

import java.time.LocalDate;
import java.util.List;

@Builder
public record NoticeListResponseDto(
        Page<NoticeDto> recentNotices,
        List<NoticeDto> popularWeeklyNotices
) {
    @Builder
    public record NoticeDto(
            Long id,
            Long originalId,
            String title,
            String link,
            LocalDate date,
            int viewCount
    ) {
        public static NoticeDto from(AnnouncementEntity entity) {
            return NoticeDto.builder()
                    .id(entity.getAnnouncementId())
                    .originalId(entity.getOriginalId())
                    .title(entity.getTitle())
                    .link(entity.getLink())
                    .date(entity.getDate())
                    .viewCount(entity.getViewCount())
                    .build();
        }
    }
}