package com.szwedo.krystian.conferenceservice.dao;

import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
  @Query(value = "SELECT r FROM Reservations r WHERE r.user_id = :userId")
  List<ReservationEntity> findAllByUserId(UUID userId);
}
