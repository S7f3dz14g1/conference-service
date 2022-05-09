package com.szwedo.krystian.conferenceservice.entity;

import lombok.*;

import javax.persistence.Entity;
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
  private UUID id;
  private UUID user_id;
  private UUID lecture_id;
}
