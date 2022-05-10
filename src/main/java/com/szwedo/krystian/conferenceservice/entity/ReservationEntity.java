package com.szwedo.krystian.conferenceservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "Reservations")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ReservationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private UUID userId;
  private UUID lectureId;
}
