package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.dao.LectureRepository;
import com.szwedo.krystian.conferenceservice.dao.ReservationRepository;
import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import com.szwedo.krystian.conferenceservice.model.LectureStatistic;
import com.szwedo.krystian.conferenceservice.model.ThematicPathsStatistic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServiceImplTest {

  @InjectMocks
  private StatisticServiceImpl service;
  @Mock
  private LectureRepository lectureRepository;
  @Mock
  private ReservationRepository reservationRepository;

  @Test
  public void should_return_list_of_lecture_statistic() {
    //given
    LectureEntity lecture = LectureEntity.builder().id(UUID.randomUUID()).subject("sub").times("tim").thematicPathsTitle("First").build();
    ReservationEntity reservation = ReservationEntity.builder().id(UUID.randomUUID()).lectureId(lecture.getId()).userId(UUID.randomUUID()).build();
    //when
    when(lectureRepository.findAll()).thenReturn(List.of(lecture));
    when(reservationRepository.findAll()).thenReturn(List.of(reservation));
    //then
    assertEquals(getExpectedResult1().get(0).subjectLecture(), service.getLectureStatistics().get(0).subjectLecture());
    assertEquals(getExpectedResult1().get(0).interest(), service.getLectureStatistics().get(0).interest());
  }

  private List<LectureStatistic> getExpectedResult1() {
    return List.of(LectureStatistic.builder().subjectLecture("sub").interest("100.0 %").build());
  }

  @Test
  void should_return_list_of_thematic_paths_statistic() {
    //given
    LectureEntity lecture = LectureEntity.builder().id(UUID.randomUUID()).subject("sub").times("tim").thematicPathsTitle("First").build();
    ReservationEntity reservation = ReservationEntity.builder().id(UUID.randomUUID()).lectureId(lecture.getId()).userId(UUID.randomUUID()).build();
    //when
    when(lectureRepository.findAll()).thenReturn(List.of(lecture));
    when(reservationRepository.findAll()).thenReturn(List.of(reservation));
    //then
    assertEquals(getExpectedResult2().get(0).thematic_paths_title(), service.getThematicPathsStatistics().get(0).thematic_paths_title());
    assertEquals(getExpectedResult2().get(0).interest(), service.getThematicPathsStatistics().get(0).interest());
  }

  private List<ThematicPathsStatistic> getExpectedResult2() {
    return List.of(ThematicPathsStatistic.builder().thematic_paths_title("First").interest("100.0 %").build());
  }
}