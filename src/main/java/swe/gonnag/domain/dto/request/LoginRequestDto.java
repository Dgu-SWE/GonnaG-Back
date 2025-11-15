package swe.gonnag.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(

        // id는 NN
        @NotNull(message = "학번(id)은 필수 입력값입니다.")
        Long id,

        // pwd는 NN, 유효성 검사를 위해 NotBlank 사용
        @NotBlank(message = "비밀번호(pwd)는 필수 입력값입니다.")
        String pwd
) {}