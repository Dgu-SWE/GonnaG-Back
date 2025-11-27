package swe.gonnag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import swe.gonnag.domain.dto.request.LoginRequestDto;
import swe.gonnag.domain.dto.response.LoginResponseDto;
import swe.gonnag.service.UserLoginService;

import swe.gonnag.util.common.CommonResponseDto;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserLoginService userLoginService;

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
}