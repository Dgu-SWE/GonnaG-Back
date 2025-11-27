package swe.gonnag.domain.dto.response;

import lombok.Builder;
import swe.gonnag.domain.entity.TranscriptEntity;

import java.util.UUID;

@Builder
public record TranscriptResponseDto(
        UUID transcriptId,
        ClassInfoDto classInfo
) {
    public static TranscriptResponseDto from(TranscriptEntity transcript) {
        return TranscriptResponseDto.builder()
                .transcriptId(transcript.getId())
                .classInfo(ClassInfoDto.from(transcript.getClassInfo()))
                .build();
    }

    // 내부 DTO (수업 정보)
    @Builder
    public record ClassInfoDto(
            String cid,
            String name,
            Integer credit,
            Integer courseType,
            Boolean isEnglish
    ) {
        public static ClassInfoDto from(swe.gonnag.domain.entity.ClassEntity classEntity) {
            return ClassInfoDto.builder()
                    .cid(classEntity.getId())
                    .name(classEntity.getName())
                    .credit(classEntity.getCredit())
                    .courseType(classEntity.getCourseType())
                    .isEnglish(classEntity.getIsEnglish())
                    .build();
        }
    }
}