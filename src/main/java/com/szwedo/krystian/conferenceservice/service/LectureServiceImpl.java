package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.dao.LectureRepository;
import com.szwedo.krystian.conferenceservice.dao.ReservationRepository;
import com.szwedo.krystian.conferenceservice.dao.UsersRepository;
import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import com.szwedo.krystian.conferenceservice.entity.UsersEntity;
import com.szwedo.krystian.conferenceservice.exception.UserNotFoundException;
import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class LectureServiceImpl implements LectureService {

  private final LectureRepository lectureRepository;
  private final UsersRepository usersRepository;
  private final ReservationRepository reservationRepository;

  @Override
  public List<ThematicPaths> getConferenceInformation() {
    return mapToThematicPaths(lectureRepository.findAll());
  }

  private List<ThematicPaths> mapToThematicPaths(List<LectureEntity> lecture) {
    return lecture.stream().map(p ->
            ThematicPaths.builder()
                .title(p.getThematicPathsTitle())
                .lecture(getLectures(lecture, p.getThematicPathsTitle()))
                .build())
        .toList();
  }

  private Map<String, String> getLectures(List<LectureEntity> lecture, String title) {
    Map<String, String> lectures = new HashMap<>();
    lecture.forEach(p -> {
      if (p.getThematicPathsTitle().equals(title))
        lectures.put(p.getTimes(), p.getSubject());
    });
    return lectures;
  }

  @Override
  public List<LectureEntity> getLecturesByLogin(String login) {
    Optional<UsersEntity> user = usersRepository.findUsersEntityByLogin(login);
    return user.map(this::getUserLectures)
        .orElseThrow(() -> new UserNotFoundException(login));
  }

  private List<LectureEntity> getUserLectures(UsersEntity user) {
    List<UUID> lecturesId = getLecturesIdByUserId(user.getId());
    List<LectureEntity> lectureEntities = lectureRepository.getAllByIdIsIn(lecturesId);
    return mapToLectureEntities(lectureEntities);
  }

  private List<LectureEntity> mapToLectureEntities(List<LectureEntity> lectureEntities) {
    return lectureEntities
        .stream()
        .map(l -> LectureEntity
            .builder()
            .id(l.getId())
            .subject(l.getSubject())
            .thematicPathsTitle(l.getThematicPathsTitle())
            .times(l.getTimes())
            .build())
        .toList();
  }

  private List<UUID> getLecturesIdByUserId(UUID userID) {
    return reservationRepository
        .findAllByUserId(userID)
        .stream()
        .map(ReservationEntity::getLectureId)
        .toList();
  }
}