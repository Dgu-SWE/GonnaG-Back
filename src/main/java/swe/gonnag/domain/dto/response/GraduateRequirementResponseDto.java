package swe.gonnag.domain.dto.response;

import lombok.Builder;
import swe.gonnag.domain.entity.ProgramEntity;
import swe.gonnag.domain.entity.RequirementEntity;

import java.util.List;

@Builder
public record GraduateRequirementResponseDto(
        Long pid,
        String departmentName,
        String programName,
        Integer baseYear,
        List<RequirementDetailDto> requirements
) {
    public static GraduateRequirementResponseDto of(ProgramEntity program, List<RequirementDetailDto> requirements) {
        return GraduateRequirementResponseDto.builder()
                .pid(program.getId())
                .departmentName(program.getDepartmentName())
                .programName(program.getProgramName())
                .baseYear(program.getBaseYear())
                .requirements(requirements)
                .build();
    }

    @Builder
    public record RequirementDetailDto(
            Long rid,
            String category,
            String value, // 학점(84) or 과목명
            String type,  // CREDIT or COURSE
            String description
    ) {
        public static RequirementDetailDto from(RequirementEntity req) {
            return RequirementDetailDto.builder()
                    .rid(req.getId())
                    .category(req.getCategory())
                    .value(req.getValue())
                    .type(req.getType())
                    .description(req.getDescription())
                    .build();
        }
    }
}