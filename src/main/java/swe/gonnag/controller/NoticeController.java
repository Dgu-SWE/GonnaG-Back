package swe.gonnag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swe.gonnag.domain.dto.response.GetMajorNoticeResponseDto;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.service.GetCommonNoticeService;
import swe.gonnag.service.GetMajorNoticeService;
import swe.gonnag.util.common.CommonResponseDto;
import swe.gonnag.util.common.security.CustomUserDetails;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {

    private final GetMajorNoticeService getMajorNoticeService;
    private final GetCommonNoticeService getCommonNoticeService;

    // 학과 공지 조회
    @GetMapping("/notice")
    public CommonResponseDto<?> getMajorNotice(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        if (user == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
        return CommonResponseDto.ok(getMajorNoticeService.getMajorNotice(user.getUserId()));
    }

    // 전체 공지 조회
    @GetMapping("/notice/all")
    public CommonResponseDto<?> getCommonNoticeAll(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        if (user == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);
        return CommonResponseDto.ok(getCommonNoticeService.getCommonNotice(user.getUserId()));
    }
}
