package swe.gonnag.domain.dto.response;

import lombok.Builder;
import swe.gonnag.domain.entity.AnnouncementEntity;

import java.time.LocalDate;
import java.util.List;

@Builder
public record GetCommonNoticeResponseDto(
        List<CommonNotice> recentNotices,
        List<CommonNotice> popularNotices
) {
    @Builder
    public record CommonNotice(
            Long announcementId,
            Long originalId,
            String title,
            String link,
            LocalDate date,
            Integer viewCount,
            String major
    ) {
        public static CommonNotice from(AnnouncementEntity announcementEntity) {
            return CommonNotice.builder()
                    .announcementId(announcementEntity.getAnnouncementId())
                    .originalId(announcementEntity.getOriginalId())
                    .title(announcementEntity.getTitle())
                    .link(announcementEntity.getLink())
                    .date(announcementEntity.getDate())
                    .viewCount(announcementEntity.getViewCount())
                    .major(announcementEntity.getMajor())
                    .build();
        }
    }
}
