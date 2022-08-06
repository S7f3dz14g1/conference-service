package com.szwedo.krystian.conferenceservice.service.impl;

import com.szwedo.krystian.conferenceservice.dao.LectureRepository;
import com.szwedo.krystian.conferenceservice.dao.ReservationRepository;
import com.szwedo.krystian.conferenceservice.dao.UsersRepository;
import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import com.szwedo.krystian.conferenceservice.entity.UsersEntity;
import com.szwedo.krystian.conferenceservice.exception.UserNotFoundException;
import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import com.szwedo.krystian.conferenceservice.service.impl.LectureServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceImplTest {

  @InjectMocks
  private LectureServiceImpl service;

  @Mock
  private LectureRepository lectureRepository;

  @Mock
  private UsersRepository usersRepository;

  @Mock
  private ReservationRepository reservationRepository;

  @Test
  public void should_return_the_list_of_ThematicPath() {
    //given
    Map<String, String> lectures = new HashMap<>();
    lectures.put("time", "title");
    List<ThematicPaths> expectedResult = List.of(ThematicPaths.builder()
        .title("thematicPathsTitle")
        .lecture(lectures)
        .build());
    //when
    when(lectureRepository.findAll()).thenReturn(List.of(LectureEntity.builder()
        .id(UUID.randomUUID())
        .subject("title")
        .thematicPathsTitle("thematicPathsTitle")
        .times("time")
        .build()));
    //then
    assertEquals(expectedResult, service.getConferenceInformation());
  }

  @Test
  public void should_throw_UserNotFoundException_when_user_does_not_exist() {
    //given
    String login = "login";
    //when
    when(usersRepository.findUsersEntityByLogin(login)).thenReturn(Optional.empty());
    //then
    assertThrows(UserNotFoundException.class, () -> service.getLecturesByLogin(login));
  }

  @Test
  public void should_return_the_list_of_lecture_by_login_when_user_exists() {
    //given
    String login = "login";
    UsersEntity user = UsersEntity.builder()
        .id(UUID.randomUUID())
        .login(login)
        .email("email")
        .build();
    UUID lecturesId = UUID.randomUUID();
    ReservationEntity reservation = ReservationEntity.builder()
        .id(UUID.randomUUID())
        .lectureId(lecturesId)
        .userId(user.getId())
        .build();
    LectureEntity lecture = LectureEntity.builder()
        .id(lecturesId)
        .subject("Subject")
        .times("times")
        .thematicPathsTitle("paths")
        .build();
    //when
    when(usersRepository.findUsersEntityByLogin(login)).thenReturn(Optional.of(user));
    when(reservationRepository.findAllByUserId(user.getId())).thenReturn(List.of(reservation));
    when(lectureRepository.getAllByIdIsIn(List.of(lecturesId))).thenReturn(List.of(lecture));
    //then
    assertEquals(List.of(lecture).size(),service.getLecturesByLogin(login).size());
    assertEquals(List.of(lecture).get(0).getId(),service.getLecturesByLogin(login).get(0).getId());
    assertEquals(List.of(lecture).get(0).getSubject(),service.getLecturesByLogin(login).get(0).getSubject());
    assertEquals(List.of(lecture).get(0).getThematicPathsTitle(),service.getLecturesByLogin(login).get(0).getThematicPathsTitle());
    assertEquals(List.of(lecture).get(0).getTimes(),service.getLecturesByLogin(login).get(0).getTimes());

  }
}