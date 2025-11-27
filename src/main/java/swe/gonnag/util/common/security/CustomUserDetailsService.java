package swe.gonnag.util.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import swe.gonnag.domain.entity.UserEntity;
import swe.gonnag.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String idString) throws UsernameNotFoundException {

        Long id = Long.parseLong(idString);

        // 1. DB에서 loginId로 회원 조회
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디를 가진 유저를 찾을 수 없습니다: " + id));

        // 2. 찾은 회원 엔티티를 SecurityContext가 이해할 수 있는 UserDetails로 감싸서 반환
        return new CustomUserDetails(user);
    }
}