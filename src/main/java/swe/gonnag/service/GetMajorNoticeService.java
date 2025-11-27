package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.gonnag.domain.dto.response.GetMajorNoticeResponseDto;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.repository.AnnouncementRepository;
import swe.gonnag.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMajorNoticeService {

    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    @Transactional
    public GetMajorNoticeResponseDto getMajorNotice(Long userId) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));

        String userMajor = userEntity.getMajor();

        List<String> targetMajors = getMajorAliases(userMajor);

        List<GetMajorNoticeResponseDto.RecentNoticesWrapper.MajorNotice> noticeList
                = announcementRepository.findTop10ByMajorInOrderByDateDesc(targetMajors)
                .stream()
                .map(GetMajorNoticeResponseDto.RecentNoticesWrapper.MajorNotice::from)
                .toList();

        return GetMajorNoticeResponseDto.builder()
                .recentNotices(
                        GetMajorNoticeResponseDto.RecentNoticesWrapper.builder()
                                .content(noticeList)
                                .build()
                ).build();
    }

    private List<String> getMajorAliases(String major) {
        List<String> aliases = new ArrayList<>();
        aliases.add(major);

        // 같은 전공의 다른 이름들 모두 추가
        if(major.equals("컴퓨터공학전공") || major.equals("컴퓨터·AI학부") || major.equals("AI소프트웨어융합학부")) {
            aliases.add("컴퓨터공학전공");
            aliases.add("컴퓨터·AI학부");
            aliases.add("AI소프트웨어융합학부");
        }

        // 중복 제거 후 return
        return aliases.stream().distinct().toList();
    }
}
