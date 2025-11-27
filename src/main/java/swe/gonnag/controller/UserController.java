package swe.gonnag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import swe.gonnag.domain.dto.request.LoginRequestDto;
import swe.gonnag.domain.dto.response.LoginResponseDto;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.service.GetUserInfoService;
import swe.gonnag.service.UserLoginService;

import swe.gonnag.util.common.CommonResponseDto;
import jakarta.validation.Valid;
import swe.gonnag.util.common.security.CustomUserDetails;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserLoginService userLoginService;
    private final GetUserInfoService getUserInfoService;

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<CommonResponseDto<LoginResponseDto>> signIn(
            @Valid @RequestBody LoginRequestDto requestDto
    ) {
        LoginResponseDto responseDto = userLoginService.login(requestDto);
        CommonResponseDto<LoginResponseDto> response = CommonResponseDto.ok(responseDto);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    // 로그아웃
    @GetMapping("/signout")
    public ResponseEntity<CommonResponseDto<Object>> signOut() {
        Map<String, String> message = Map.of("message", "Logout successful");
        CommonResponseDto<Object> response = CommonResponseDto.ok(message);
        return new ResponseEntity<>(response, response.httpStatus());
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
}