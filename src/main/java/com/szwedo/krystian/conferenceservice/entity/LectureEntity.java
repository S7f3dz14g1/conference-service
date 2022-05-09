package com.szwedo.krystian.conferenceservice.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "Lecture")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class LectureEntity{
  @Id
  UUID id;
  String subject;
  @Column(name = "thematic_paths_title")
  String thematicPathsTitle;
  String times;
}