package swe.gonnag.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CourseType {

    // 1. 여기에 우리 학교의 규칙을 정의합니다. (DB에 저장된 숫자, 요건 이름)
    MAJOR_REQUIRED(1, "전공필수"),
    MAJOR_SELECTIVE(2, "전공선택"), // 혹은 "전공"
    GENERAL_REQUIRED(3, "필수교양"), // 혹은 "공통교양"
    GENERAL_SELECTIVE(4, "일반교양"),
    BSM(5, "BSM"); // 기초과학(수학/과학 등)

    private final int code;      // Class 테이블의 'course' (Integer) 값
    private final String label;  // Requirement 테이블의 'category' (String) 값

    // 2. 숫자를 넣으면 "전공필수" 같은 문자를 찾아주는 메서드 (통역 기능)
    public static String getLabelByCode(int code) {
        return Arrays.stream(CourseType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .map(CourseType::getLabel)
                .orElse("기타"); // 매칭되는 게 없으면 '기타' 처리
    }

    // 3. 반대로 문자를 넣으면 숫자 코드를 찾아주는 메서드
    public static CourseType fromLabel(String label) {
        return Arrays.stream(CourseType.values())
                .filter(type -> type.getLabel().equals(label))
                .findFirst()
                .orElse(null);
    }
}