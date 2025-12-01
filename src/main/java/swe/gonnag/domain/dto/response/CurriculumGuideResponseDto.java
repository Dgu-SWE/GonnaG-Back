package swe.gonnag.domain.dto.response;

import lombok.Builder;
import swe.gonnag.domain.entity.RequirementEntity;

import java.util.List;

@Builder
public record CurriculumGuideResponseDto(
        Long programId,
        List<RequirementDto> requirements

) {
    public static CurriculumGuideResponseDto of(Long programId, List<RequirementDto> requirements) {
        return CurriculumGuideResponseDto.builder()
                .programId(programId)
                .requirements(requirements)
                .build();
    }


    @Builder
    public record RequirementDto(
            Long requirementId,
            String category,
            String value,
            String type,
            String description
    ) {
        public static RequirementDto from(RequirementEntity entity) {
            return RequirementDto.builder()
                    .requirementId(entity.getId())
                    .category(entity.getCategory())
                    .value(entity.getValue())
                    .type(entity.getType())
                    .description(entity.getDescription())
                    .build();
        }
    }

}
