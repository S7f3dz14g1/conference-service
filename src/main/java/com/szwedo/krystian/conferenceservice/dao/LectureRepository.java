package com.szwedo.krystian.conferenceservice.dao;

import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LectureRepository extends CrudRepository<LectureEntity, UUID> {
   List<LectureEntity> findAll();
}