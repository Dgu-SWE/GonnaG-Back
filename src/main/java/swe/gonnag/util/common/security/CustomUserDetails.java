package swe.gonnag.util.common.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import swe.gonnag.domain.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    // 권한 반환 (Role이 없다면 기본값 설정)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    // ★ 중요: 여기서 반환하는 값이 식별자(username)가 됩니다.
    // 당신의 경우 loginId를 사용하므로 이를 반환합니다.
    @Override
    public String getUsername() {
        return user.getName();
    }

    public Long getUserId(){
        return user.getId();
    }

    // 계정 만료/잠김 여부 등 (사용 안 하면 true로 설정)
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}