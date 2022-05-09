package com.szwedo.krystian.conferenceservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "Users")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class UsersEntity {
  @Id
  private UUID id;
  private String login;
  private String email;
}