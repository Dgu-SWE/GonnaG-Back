package swe.gonnag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swe.gonnag.domain.dto.response.TranscriptResponseDto;
import swe.gonnag.service.CourseHistoryService;
import swe.gonnag.util.common.CommonResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseHistoryController {

    private final CourseHistoryService courseHistoryService;

    //수강 과목 조회
    @GetMapping("/history")
    public ResponseEntity<CommonResponseDto<List<TranscriptResponseDto>>> getHistory(
            @RequestAttribute("userId") Long userId
    ) {
        List<TranscriptResponseDto> historyList = courseHistoryService.getCourseHistory(userId);

        return ResponseEntity.ok(CommonResponseDto.ok(historyList));
    }
}