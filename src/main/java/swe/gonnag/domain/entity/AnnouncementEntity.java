package swe.gonnag.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Announcement", indexes = {
        // 최신순 정렬 조회용 인덱스
        @Index(name = "idx_notice_date", columnList = "date DESC"),
        // 주간 인기글 조회용 복합 인덱스
        @Index(name = "idx_notice_popular", columnList = "date, view_count DESC")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long announcementId; // 내부 관리용 PK

    @Column(name = "original_id", nullable = false, unique = true)
    private Long originalId; // 학교 홈페이지 원본 ID (중복 방지)

    @Column(nullable = false, length = 512)
    private String title;

    @Column(nullable = false, length = 2048)
    private String link;

    @Column(nullable = false)
    private LocalDate date; // 공지 게시일 (YYYY-MM-DD)

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0; // 조회수 (기본값 0)

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 크롤링 수집 시각

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 정보 갱신 시각

    @Column(name = "announcement_major")
    // 어느 학과의 공지인지 설정
    // 전체 공지는 common으로 설정
    private String major;

    // 빌더
    @lombok.Builder
    public AnnouncementEntity(Long originalId, String title, String link, LocalDate date, int viewCount) {
        this.originalId = originalId;
        this.title = title;
        this.link = link;
        this.date = date;
        this.viewCount = viewCount;
    }
}