package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.domain.dto.request.LoginRequestDto;
import swe.gonnag.domain.dto.response.LoginResponseDto;
import swe.gonnag.repository.UserRepository;
import swe.gonnag.util.common.JwtUtil;

import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto requestDto) {

        // 학번조회
        UserEntity user = userRepository.findById(requestDto.id())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.pwd(), user.getPwd())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성 후 dto 변환
        String accessToken = jwtUtil.createJwt(user.getId());
        return new LoginResponseDto(accessToken);
    }
}