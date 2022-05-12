package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.model.LectureStatistic;

import java.util.List;


public interface StatisticService {

  List<LectureStatistic> getLectureStatistics();
}
