package com.szwedo.krystian.conferenceservice.dao;

import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LectureRepository extends CrudRepository<LectureEntity, UUID> {
  List<LectureEntity> findAll();

  Optional<LectureEntity> findLectureEntityBySubject(String subject);

  List<LectureEntity> findLectureEntitiesByTimes(String times);

  List<LectureEntity> getAllByIdIsIn(List<UUID> lectureIds);
}