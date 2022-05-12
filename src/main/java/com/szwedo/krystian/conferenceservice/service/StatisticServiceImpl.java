package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.dao.LectureRepository;
import com.szwedo.krystian.conferenceservice.dao.ReservationRepository;
import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import com.szwedo.krystian.conferenceservice.model.LectureStatistic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService{

  private final LectureRepository lectureRepository;
  private final ReservationRepository reservationRepository;

  @Override
  public List<LectureStatistic> getLectureStatistics() {
    List<LectureEntity>entities = lectureRepository.findAll();
    return mapToLectureStatistic(entities);
  }

  private List<LectureStatistic> mapToLectureStatistic(List<LectureEntity> entities) {
    return entities.stream()
        .map(e->LectureStatistic.builder()
            .subjectLecture(e.getSubject())
            .interest(countInterest(e.getId()))
            .build())
        .toList();
  }

  private String countInterest(UUID lectureId) {

    List<ReservationEntity>reservationEntities =reservationRepository.findAll();
    long numberOfLecture =reservationEntities
        .stream()
        .filter(e->e.getLectureId().equals(lectureId))
        .count();
    return (((double)numberOfLecture/reservationEntities.size())*100)+" %";
  }

}
