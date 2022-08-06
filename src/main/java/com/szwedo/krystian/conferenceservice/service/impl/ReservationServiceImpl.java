package com.szwedo.krystian.conferenceservice.service.impl;

import com.szwedo.krystian.conferenceservice.controller.ReservationRequest;
import com.szwedo.krystian.conferenceservice.dao.LectureRepository;
import com.szwedo.krystian.conferenceservice.dao.ReservationRepository;
import com.szwedo.krystian.conferenceservice.dao.UsersRepository;
import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import com.szwedo.krystian.conferenceservice.entity.UsersEntity;
import com.szwedo.krystian.conferenceservice.exception.*;
import com.szwedo.krystian.conferenceservice.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.szwedo.krystian.conferenceservice.files.File.sendMessage;

@Service
@AllArgsConstructor
class ReservationServiceImpl implements ReservationService {

    private final LectureRepository lectureRepository;
    private final ReservationRepository reservationRepository;
    private final UsersRepository usersRepository;

    @Override
    public void reservationToLecture(ReservationRequest request) {
        Optional<LectureEntity> lecture = lectureRepository
                .findLectureEntityBySubject(request.title_lecture());
        lecture.ifPresent(e -> {
            int numberOfOccupiedPlaces = reservationRepository
                    .countAllByLectureId(e.getId());
            if (numberOfOccupiedPlaces > 4) {
                throw new LectureIsFullyBookedException(request.title_lecture());
            }
        });
        LectureEntity lectureEntity = lecture.orElseThrow(() -> new LectureNotFoundException(request.title_lecture()));
        Optional<UsersEntity> user = usersRepository.findUsersEntityByLoginAndEmail(request.login(), request.email());
        user.ifPresentOrElse(e -> makeReservationWhenUserExists(e, lectureEntity),
                () -> makeReservationWhenUserDoesNotExist(UsersEntity
                                .builder()
                                .login(request.login())
                                .email(request.email())
                                .build(),
                        lectureEntity));
    }

    private void makeReservationWhenUserDoesNotExist(UsersEntity usersEntity,
                                                     LectureEntity lecture) {
        int numberOfUsers = usersRepository
                .countUsersEntitiesByLogin(usersEntity.getLogin());
        if (numberOfUsers != 0) {
            throw new UserEntityExistsException(usersEntity.getLogin());
        } else {
            usersEntity = usersRepository
                    .save(UsersEntity
                            .builder()
                            .login(usersEntity.getLogin())
                            .email(usersEntity.getEmail())
                            .build());
            makeReservationLecture(lecture, usersEntity);
            sendMessage(usersEntity.getLogin(), lecture);
        }
    }

    private void makeReservationWhenUserExists(UsersEntity usersEntity,
                                               LectureEntity lecture) {
        List<UUID> lecturesId = lectureRepository
                .findLectureEntitiesByTimes(lecture.getTimes())
                .stream()
                .map(LectureEntity::getId)
                .toList();
        List<ReservationEntity> reservations = reservationRepository
                .findAllByUserId(usersEntity.getId());
        reservations.forEach(e -> {
            if (lecturesId.contains(e.getLectureId())) throw new ReservationExistsException();
        });
        makeReservationLecture(lecture, usersEntity);
        sendMessage(usersEntity.getLogin(), lecture);
    }

    private void makeReservationLecture(LectureEntity lecture,
                                        UsersEntity usersEntity) {
        reservationRepository
                .save(ReservationEntity
                        .builder()
                        .lectureId(lecture.getId())
                        .userId(usersEntity.getId())
                        .build());
    }

    @Override
    public void cancelReservation(String email,
                                  String login,
                                  String lectureSubject) {
        Optional<UsersEntity> user = usersRepository
                .findUsersEntityByLoginAndEmail(login, email);
        Optional<LectureEntity> lecture = lectureRepository
                .findLectureEntityBySubject(lectureSubject);
        user.ifPresent(u -> lecture
                .ifPresent(l -> {
                    int numberOfReservation = reservationRepository
                            .deleteByUserIdAndLectureId(u.getId(), l.getId());
                    if (numberOfReservation == 0)
                        throw new UnableToCancelReservationException();
                }));
        user.orElseThrow(UnableToCancelReservationException::new);
        lecture.orElseThrow(UnableToCancelReservationException::new);
    }
}