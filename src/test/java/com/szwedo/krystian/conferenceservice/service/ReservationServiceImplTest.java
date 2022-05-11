package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.controller.ReservationRequest;
import com.szwedo.krystian.conferenceservice.dao.LectureRepository;
import com.szwedo.krystian.conferenceservice.dao.ReservationRepository;
import com.szwedo.krystian.conferenceservice.dao.UsersRepository;
import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import com.szwedo.krystian.conferenceservice.entity.UsersEntity;
import com.szwedo.krystian.conferenceservice.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReservationServiceImplTest {

  @InjectMocks
  private ReservationServiceImpl service;
  @Mock
  private ReservationRepository reservationRepository;
  @Mock
  private UsersRepository usersRepository;
  @Mock
  private LectureRepository lectureRepository;

  @Test
  public void should_throw_LectureIsFullyBookedException_when_number_of_occupied_places_is_more_then_four() {
    //given
    int numberOfOccupiedPlaces = 6;
    ReservationRequest request = getRequest();
    LectureEntity lecture = getLecture();
    //when
    when(lectureRepository.findLectureEntityBySubject(request.title_lecture())).thenReturn(Optional.of(lecture));
    when(reservationRepository.countAllByLectureId(lecture.getId())).thenReturn(numberOfOccupiedPlaces);
    //then
    assertThrows(LectureIsFullyBookedException.class, () ->
        service.reservationToLecture(request));
  }

  @Test
  public void should_throw_LectureNotFoundException_when_lecture_does_not() {
    //given
    ReservationRequest request = getRequest();
    //when
    when(lectureRepository.findLectureEntityBySubject(request.title_lecture())).thenReturn(Optional.empty());
    //then
    assertThrows(LectureNotFoundException.class, () ->
        service.reservationToLecture(request));
  }

  @Test
  public void should_throw_ReservationExistsException_when_reservation_already_exists() {
    //given
    int numberOfOccupiedPlaces = 1;
    ReservationRequest request = getRequest();
    LectureEntity lecture = getLecture();
    UsersEntity user = getUserEntity();
    ReservationEntity reservationEntity = getReservation(user.getId(), lecture.getId());
    //when
    when(lectureRepository.findLectureEntityBySubject(request.title_lecture())).thenReturn(Optional.of(lecture));
    when(reservationRepository.countAllByLectureId(lecture.getId())).thenReturn(numberOfOccupiedPlaces);
    when(usersRepository.findUsersEntityByLoginAndEmail(request.login(), request.email())).thenReturn(Optional.of(user));
    when(lectureRepository.findLectureEntitiesByTimes(lecture.getTimes())).thenReturn(List.of(lecture));
    when(reservationRepository.findAllByUserId(user.getId())).thenReturn(List.of(reservationEntity));
    //then
    assertThrows(ReservationExistsException.class, () ->
        service.reservationToLecture(request));
  }

  @Test
  public void should_throw_UserEntityExistsException_when_create_new_user_with_existing_nick() {
    //given
    int numberOfOccupiedPlaces = 1;
    ReservationRequest request = getRequest();
    LectureEntity lecture = getLecture();
    UsersEntity user = getUserEntity();
    //when
    when(lectureRepository.findLectureEntityBySubject(request.title_lecture())).thenReturn(Optional.of(lecture));
    when(reservationRepository.countAllByLectureId(lecture.getId())).thenReturn(numberOfOccupiedPlaces);
    when(usersRepository.findUsersEntityByLoginAndEmail(request.login(), request.email())).thenReturn(Optional.empty());
    when(usersRepository.countUsersEntitiesByLogin(user.getLogin())).thenReturn(1);
    //then
    assertThrows(UserEntityExistsException.class, () ->
        service.reservationToLecture(request));
  }

  @Test
  public void should_make_reservation_when_user_exists() {
    int numberOfOccupiedPlaces = 1;
    ReservationRequest request = getRequest();
    LectureEntity lecture = getLecture();
    UsersEntity user = getUserEntity();
    ArgumentCaptor<ReservationEntity> argumentCaptor = ArgumentCaptor.forClass(ReservationEntity.class);
    //when
    when(lectureRepository.findLectureEntityBySubject(request.title_lecture())).thenReturn(Optional.of(lecture));
    when(reservationRepository.countAllByLectureId(lecture.getId())).thenReturn(numberOfOccupiedPlaces);
    when(usersRepository.findUsersEntityByLoginAndEmail(request.login(), request.email())).thenReturn(Optional.of(user));
    when(lectureRepository.findLectureEntitiesByTimes(lecture.getTimes())).thenReturn(List.of(lecture));
    when(reservationRepository.findAllByUserId(user.getId())).thenReturn(List.of());
    service.reservationToLecture(request);
    //then
    verify(reservationRepository).save(argumentCaptor.capture());
    assertEquals(argumentCaptor.getValue().getLectureId(), lecture.getId());
    assertEquals(argumentCaptor.getValue().getUserId(), user.getId());
  }

//  @Test
//  public void should_make_reservation_when_user_does_not_exist() {
//    //given
//    int numberOfOccupiedPlaces = 1;
//    ReservationRequest request = getRequest();
//    LectureEntity lecture = getLecture();
//    UsersEntity user = getUserEntity();
//    ArgumentCaptor<ReservationEntity> argumentReservationEntity = ArgumentCaptor.forClass(ReservationEntity.class);
//    ArgumentCaptor<UsersEntity> argumentUsersEntity = ArgumentCaptor.forClass(UsersEntity.class);
//    //when
//    when(lectureRepository.findLectureEntityBySubject(request.title_lecture())).thenReturn(Optional.of(lecture));
//    when(reservationRepository.countAllByLectureId(lecture.getId())).thenReturn(numberOfOccupiedPlaces);
//    when(usersRepository.findUsersEntityByLoginAndEmail(request.login(), request.email())).thenReturn(Optional.empty());
//    when(usersRepository.countUsersEntitiesByLogin(user.getLogin())).thenReturn(0);
//    when(usersRepository.save(UsersEntity.builder().login(user.getLogin()).email(user.getEmail()).build())).thenReturn(user);
//    service.reservationToLecture(request);
//    //then
////    verify(usersRepository).save(argumentUsersEntity.capture());
////    assertEquals(argumentUsersEntity.getValue().getLogin(), user.getLogin());
////    assertEquals(argumentUsersEntity.getValue().getEmail(), user.getEmail());
//    verify(reservationRepository).save(argumentReservationEntity.capture());
//    assertEquals(argumentReservationEntity.getValue().getLectureId(), lecture.getId());
//    assertEquals(argumentReservationEntity.getValue().getUserId(), user.getId());
//  }

  private UsersEntity getUserEntity() {
    return UsersEntity.builder()
        .id(UUID.randomUUID())
        .email("email")
        .login("login")
        .build();
  }

  private LectureEntity getLecture() {
    return LectureEntity.builder()
        .id(UUID.randomUUID())
        .thematicPathsTitle("t")
        .subject("title")
        .times("tim")
        .build();
  }

  private ReservationRequest getRequest() {
    return ReservationRequest.builder()
        .login("login")
        .email("email")
        .title_lecture("title")
        .build();
  }

  private ReservationEntity getReservation(UUID userId, UUID lectureId) {
    return ReservationEntity.builder()
        .id(UUID.randomUUID())
        .lectureId(lectureId)
        .userId(userId)
        .build();
  }

  @Test
  public void should_throw_UnableToCancelReservationException_when_user_does_not_exist() {
    //given
    String login = "login";
    String email = "email";
    String lectureSubject="subject";
    //when
    when(usersRepository.findUsersEntityByLoginAndEmail(login,email)).thenReturn(Optional.empty());
    //then
    assertThrows(UnableToCancelReservationException.class, () ->
        service.cancelReservation(email,login,lectureSubject));
  }

  @Test
  public void should_throw_UnableToCancelReservationException_when_lecture_does_not_exist() {
    //given
    String login = "login";
    String email = "email";
    String lectureSubject="subject";
    UsersEntity user = UsersEntity.builder().id(UUID.randomUUID()).email(email).login(login).build();
    //when
    when(usersRepository.findUsersEntityByLoginAndEmail(login,email)).thenReturn(Optional.of(user));
    when(lectureRepository.findLectureEntityBySubject(lectureSubject)).thenReturn(Optional.empty());
    //then
    assertThrows(UnableToCancelReservationException.class, () ->
        service.cancelReservation(email,login,lectureSubject));
  }

  @Test
  public void should_throw_UnableToCancelReservationException_when_reservation_does_not_exist() {
    //given
    String login = "login";
    String email = "email";
    String lectureSubject="subject";
    UsersEntity user = UsersEntity.builder().id(UUID.randomUUID()).email(email).login(login).build();
    LectureEntity lecture=LectureEntity.builder().id(UUID.randomUUID()).subject(lectureSubject).build();
    //when
    when(usersRepository.findUsersEntityByLoginAndEmail(login,email)).thenReturn(Optional.of(user));
    when(lectureRepository.findLectureEntityBySubject(lectureSubject)).thenReturn(Optional.empty());
    when(reservationRepository.deleteByUserIdAndLectureId(user.getId(),lecture.getId())).thenReturn(0);
    //then
    assertThrows(UnableToCancelReservationException.class, () ->
        service.cancelReservation(email,login,lectureSubject));
  }

  @Test
  public void should_cancel_reservation_when_data_are_correct() {
    //given
    String login = "login";
    String email = "email";
    String lectureSubject="subject";
    ArgumentCaptor<UUID> argumentUser = ArgumentCaptor.forClass(UUID.class);
    ArgumentCaptor<UUID> argumentLecture = ArgumentCaptor.forClass(UUID.class);
    UsersEntity user = UsersEntity.builder().id(UUID.randomUUID()).email(email).login(login).build();
    LectureEntity lecture=LectureEntity.builder().id(UUID.randomUUID()).subject(lectureSubject).build();
    //when
    when(usersRepository.findUsersEntityByLoginAndEmail(login,email)).thenReturn(Optional.of(user));
    when(lectureRepository.findLectureEntityBySubject(lectureSubject)).thenReturn(Optional.of(lecture));
    when(reservationRepository.deleteByUserIdAndLectureId(user.getId(),lecture.getId())).thenReturn(1);
    service.cancelReservation(email,login,lectureSubject);
    //then
    verify(reservationRepository).deleteByUserIdAndLectureId(
        argumentUser.capture(),
        argumentLecture.capture()
    );
    assertEquals(argumentUser.getValue(), user.getId());
    assertEquals(argumentLecture.getValue(), lecture.getId());
  }
}