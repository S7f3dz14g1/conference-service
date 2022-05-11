package com.szwedo.krystian.conferenceservice.dao;

import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {

  List<ReservationEntity> findAllByUserId(UUID userId);

  int countAllByLectureId(UUID lectureId);

  @Transactional
  int deleteByUserIdAndLectureId(UUID userId,UUID lectureId);
}
