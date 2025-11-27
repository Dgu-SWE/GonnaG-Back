package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.gonnag.domain.dto.response.GetCommonNoticeResponseDto;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.repository.AnnouncementRepository;
import swe.gonnag.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCommonNoticeService {

    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    @Transactional
    public GetCommonNoticeResponseDto getCommonNotice(Long userId) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 최신 전체 공지 10개
        List<GetCommonNoticeResponseDto.CommonNotice> recentNoticesList
                = announcementRepository.findTop10ByMajorOrderByDateDesc("common")
                .stream()
                .map(GetCommonNoticeResponseDto.CommonNotice::from)
                .toList();

        // 인기 전체 공지 5개
        List<GetCommonNoticeResponseDto.CommonNotice> popularNoticesList
                = announcementRepository.findTop5ByMajorOrderByViewCountDesc("common")
                .stream()
                .map(GetCommonNoticeResponseDto.CommonNotice::from)
                .toList();

        return GetCommonNoticeResponseDto.builder()
                .recentNotices(recentNoticesList)
                .popularNotices(popularNoticesList)
                .build();
    }
}
