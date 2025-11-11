package swe.gonnag.util.common;

import lombok.Getter;
import swe.gonnag.exception.ErrorCode;

@Getter
public class ExceptionDto {

    private final String code;
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}
