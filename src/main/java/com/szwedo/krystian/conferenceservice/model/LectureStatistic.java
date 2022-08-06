package com.szwedo.krystian.conferenceservice.model;

import lombok.Builder;

public record LectureStatistic(String subjectLecture,
                               String interest) {
    @Builder
    public LectureStatistic {

    }
}
