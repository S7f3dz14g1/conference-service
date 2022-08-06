package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.model.LectureStatistic;
import com.szwedo.krystian.conferenceservice.model.ThematicPathsStatistic;
import com.szwedo.krystian.conferenceservice.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/statistic/")
class StatisticController {

    private final StatisticService service;

    @GetMapping("/lectureStatistics")
    List<LectureStatistic> getLectureStatistics() {
        return service.getLectureStatistics();
    }

    @GetMapping("/thematicPathsStatistic")
    List<ThematicPathsStatistic> getThematicPathsStatistics() {
        return service.getThematicPathsStatistics();
    }
}
