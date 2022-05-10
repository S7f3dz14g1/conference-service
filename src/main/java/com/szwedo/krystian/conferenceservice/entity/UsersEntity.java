package com.szwedo.krystian.conferenceservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String login;
  private String email;
}