package swe.gonnag.util.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import swe.gonnag.util.common.JwtFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // jwt 필터 주업
    private final JwtFilter jwtFilter;

    // 암화화 계산기 주입
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(configurationSource()))
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 접근 권한
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/mcp/**",
                                "/api/signin",  // 로그인 API
                                "/api/signout" // 로그아웃 API
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // jwFilter 우선 검사
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. 허용할 프론트엔드 주소 (개발용이라 *로 모든 주소 허용)
        // 실무에서는 "http://localhost:3000", "https://mydomain.com" 처럼 특정해야 함
        configuration.setAllowedOriginPatterns(List.of("*"));

        // 2. 허용할 HTTP 메서드 (GET, POST, PUT, DELETE 등 모두 허용)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // 3. 허용할 헤더 (Authorization 등 모든 헤더 허용)
        configuration.setAllowedHeaders(List.of("*"));

        // 4. 자격 증명 허용 (쿠키, 세션 등 인증 정보 포함 허용)
        configuration.setAllowCredentials(true);

        // 5. 모든 경로(/**)에 대해 위 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}