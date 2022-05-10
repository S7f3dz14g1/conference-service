package com.szwedo.krystian.conferenceservice.files;

import com.szwedo.krystian.conferenceservice.entity.LectureEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class File {

  public static void sendMessage(String login, LectureEntity lecture) {
    try {
      FileWriter writer = new FileWriter("powiadomienia.txt", true);

      writer.append("\ndata wysłania  ").append(LocalDate.now().toString());
      writer.append(" \ndo ").append(login);
      writer.append("\nZapisałeś się na prelekcje ").append(lecture.toString());

      writer.close();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
}