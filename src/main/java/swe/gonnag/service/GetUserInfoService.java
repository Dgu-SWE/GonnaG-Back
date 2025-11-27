package swe.gonnag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swe.gonnag.domain.dto.response.UserInfoResponseDto;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.exception.CustomException;
import swe.gonnag.exception.ErrorCode;
import swe.gonnag.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class GetUserInfoService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserInfo(Long userId) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserInfoResponseDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getYear(),
                userEntity.getStatus(),
                userEntity.getEarnedCredits(),
                userEntity.getDepartment(),
                userEntity.getMajor(),
                userEntity.getTrack()
        );
    }
}
