package swe.gonnag.domain.dto.MCP;

import lombok.Builder;
import swe.gonnag.domain.entity.ClassEntity;

import java.util.List;

@Builder
public record ClassesInfoResponseDto(
        List<ClassInfo> classInfoList
) {
    @Builder
    public record ClassInfo(
            String id,
            String name,
            Integer credit,
            Integer courseType,
            Boolean isEnglish,
            String prerequisite
    ) {
        public static ClassInfo from(ClassEntity classEntity) {
            return ClassInfo.builder()
                    .id(classEntity.getId())
                    .name(classEntity.getName())
                    .credit(classEntity.getCredit())
                    .courseType(classEntity.getCourseType())
                    .isEnglish(classEntity.getIsEnglish())
                    .prerequisite(classEntity.getPrerequisite())
                    .build();
        }
    }
}
