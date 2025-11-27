package swe.gonnag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swe.gonnag.domain.dto.response.GetMajorNoticeResponseDto;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.service.GetMajorNoticeService;
import swe.gonnag.util.common.CommonResponseDto;
import swe.gonnag.util.common.security.CustomUserDetails;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {

    private final GetMajorNoticeService getMajorNoticeService;

    // 학과 공지 조회
    @GetMapping("/notice")
    public CommonResponseDto<?> getMajorNotice(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        if (user == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
        return CommonResponseDto.ok(getMajorNoticeService.getMajorNotice(user.getUserId()));
    }
}
