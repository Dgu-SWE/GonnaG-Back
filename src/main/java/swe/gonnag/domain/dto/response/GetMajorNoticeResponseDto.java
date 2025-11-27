package swe.gonnag.domain.dto.response;

import lombok.Builder;
import java.util.List;
import swe.gonnag.domain.entity.AnnouncementEntity;

import java.time.LocalDate;

@Builder
public record GetMajorNoticeResponseDto(
        RecentNoticesWrapper recentNotices
) {
    @Builder
    public record RecentNoticesWrapper(
            List<MajorNotice> content
    ) {
        @Builder
        public record MajorNotice(
                Long announcementId,
                Long originalId,
                String title,
                String link,
                LocalDate date,
                Integer viewCount,
                String major
        ) {
            public static MajorNotice from(AnnouncementEntity announcementEntity) {
                return MajorNotice.builder()
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
}
