package swe.gonnag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import swe.gonnag.domain.dto.request.LoginRequestDto;
import swe.gonnag.domain.dto.request.UserMsgRequestDto;
import swe.gonnag.domain.dto.response.ChatHistoryResponseDto;
import swe.gonnag.domain.dto.response.GraduationProgressResponseDto;
import swe.gonnag.domain.dto.response.LoginResponseDto;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.service.ChatService;
import swe.gonnag.service.GetUserInfoService;
import swe.gonnag.service.UserLoginService;

import swe.gonnag.util.common.CommonResponseDto;
import jakarta.validation.Valid;
import swe.gonnag.util.common.security.CustomUserDetails;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserLoginService userLoginService;
    private final GetUserInfoService getUserInfoService;
    private final ChatService chatService;

    // 로그인
    @PostMapping("/signin")
    public CommonResponseDto<LoginResponseDto> signIn(
            @Valid @RequestBody LoginRequestDto requestDto
    ) {
        LoginResponseDto responseDto = userLoginService.login(requestDto);
        return CommonResponseDto.ok(responseDto);
    }

    // 로그아웃
    @GetMapping("/signout")
    public CommonResponseDto<Object> signOut() {
        Map<String, String> message = Map.of("message", "Logout successful");
        return CommonResponseDto.ok(message);
    }


    // 유저 정보 조회
    @GetMapping("/user")
    public CommonResponseDto<?> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        if (user == null) {
            throw new CustomException(ErrorCode.FAILURE_LOGIN);
        }
        return CommonResponseDto.ok(getUserInfoService.getUserInfo(user.getUserId()));
    }

    @GetMapping("/progress")
    public  CommonResponseDto<GraduationProgressResponseDto> getGraduationProgress(@AuthenticationPrincipal CustomUserDetails user){
        return CommonResponseDto.ok(getUserInfoService.getGraduationProgress(user.getUserId()));
    }

    @PostMapping("/chat")
    public CommonResponseDto<ChatHistoryResponseDto> sendMsg(@AuthenticationPrincipal CustomUserDetails user, @RequestBody UserMsgRequestDto request){
        return CommonResponseDto.ok(chatService.sendMsg(user.getUserId(), request.msg()));
    }

    @GetMapping("/chat/history")
    public CommonResponseDto<ChatHistoryResponseDto> chatHistory(@AuthenticationPrincipal CustomUserDetails user){
        return CommonResponseDto.ok(chatService.chatHistory(user.getUserId()));
    }
}