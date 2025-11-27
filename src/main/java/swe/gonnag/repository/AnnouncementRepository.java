package swe.gonnag.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import swe.gonnag.domain.entity.AnnouncementEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {

    // 1. 크롤링 중복 방지 (원본 ID로 찾기)
    Optional<AnnouncementEntity> findByOriginalId(Long originalId);

    // 2. 최신 공지 조회 (API의 page 파라미터 처리용)
    // 날짜 최신순 정렬은 Pageable 객체에 Sort 조건을 담아서 보낼 예정
    Page<AnnouncementEntity> findAll(Pageable pageable);

    // 3. 주간 인기 공지 (API 요구사항: 최근 7일 내 작성된 글 중 조회수 높은 순 Top 5)
    // 서비스에서 date 파라미터에 '오늘 - 7일' 날짜를 넣어주면 됨
    List<AnnouncementEntity> findTop5ByDateAfterOrderByViewCountDesc(LocalDate date);

    // 해당 학과의 전공 조회
    List<AnnouncementEntity> findTop10ByMajorInOrderByDateDesc(List<String> majors);
}