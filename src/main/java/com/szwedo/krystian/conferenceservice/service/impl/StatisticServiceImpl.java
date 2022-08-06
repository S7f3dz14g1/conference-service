package com.szwedo.krystian.conferenceservice.service.impl;

import com.szwedo.krystian.conferenceservice.dao.LectureRepository;
import com.szwedo.krystian.conferenceservice.dao.ReservationRepository;
import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import com.szwedo.krystian.conferenceservice.model.LectureStatistic;
import com.szwedo.krystian.conferenceservice.model.ThematicPathsStatistic;
import com.szwedo.krystian.conferenceservice.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class StatisticServiceImpl implements StatisticService {

    private final LectureRepository lectureRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<LectureStatistic> getLectureStatistics() {
        List<LectureEntity> entities = lectureRepository.findAll();
        return mapToLectureStatistic(entities);
    }

    private List<LectureStatistic> mapToLectureStatistic(List<LectureEntity> entities) {
        return entities.stream()
                .map(e -> LectureStatistic.builder()
                        .subjectLecture(e.getSubject())
                        .interest(countInterestLectureById(e.getId()))
                        .build())
                .toList();
    }

    private String countInterestLectureById(UUID lectureId) {
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();
        long numberOfLecture = reservationEntities
                .stream()
                .filter(e -> e.getLectureId().equals(lectureId))
                .count();
        return (((double) numberOfLecture / reservationEntities.size()) * 100) + " %";
    }

    @Override
    public List<ThematicPathsStatistic> getThematicPathsStatistics() {
        List<LectureEntity> entities = lectureRepository.findAll();
        return mapToThematicPathsStatistic(entities);
    }

    private List<ThematicPathsStatistic> mapToThematicPathsStatistic(List<LectureEntity> lectures) {
        Set<String> thematicPathsTitle = lectures
                .stream()
                .map(LectureEntity::getThematicPathsTitle)
                .collect(Collectors.toSet());
        return thematicPathsTitle.stream()
                .map(t -> ThematicPathsStatistic.builder()
                        .thematic_paths_title(t)
                        .interest(countInterestThematicPaths(lectures, t))
                        .build()).toList();
    }

    private String countInterestThematicPaths(List<LectureEntity> lectures, String thematicPathsTitle) {
        List<UUID> lectureEntities = lectures
                .stream()
                .filter(e -> e.getThematicPathsTitle().equals(thematicPathsTitle))
                .map(LectureEntity::getId)
                .toList();
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();
        long numberOfLecture = reservationEntities
                .stream()
                .filter(e -> lectureEntities.contains(e.getLectureId()))
                .count();
        return (((double) numberOfLecture / reservationEntities.size()) * 100) + " %";
    }


}
