package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.dao.LectureRepository;
import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceImplTest {

  @InjectMocks
  private  LectureServiceImpl service;

  @Mock
  private LectureRepository repository;

  @Test
  public void should_return_the_list_of_ThematicPath(){
    //given
    Map<String,String>lectures=new HashMap<>();
    lectures.put("time","title");
    List<ThematicPaths> expectedResult=List.of(ThematicPaths.builder()
        .title("thematicPathsTitle")
        .lecture(lectures)
        .build());
    //when
    when(repository.findAll()).thenReturn(List.of(LectureEntity.builder()
        .id(UUID.randomUUID())
        .title("title")
        .thematicPathsTitle("thematicPathsTitle")
        .times("time")
        .build()));
    //then
  assertEquals(expectedResult,service.getConferenceInformation());
  }
}