package com.szwedo.krystian.conferenceservice.service.impl;

import com.szwedo.krystian.conferenceservice.dao.UsersRepository;
import com.szwedo.krystian.conferenceservice.entity.UsersEntity;
import com.szwedo.krystian.conferenceservice.exception.UserEntityExistsException;
import com.szwedo.krystian.conferenceservice.exception.UserNotFoundException;
import com.szwedo.krystian.conferenceservice.model.UserDetails;
import com.szwedo.krystian.conferenceservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl service;
  @Mock
  private UsersRepository repository;

  @Test
  public void should_return_list_of_user_details(){
    //given
    List<UserDetails> expectedResult=List.of(UserDetails.builder().email("email").login("login").build());
    //when
    when(repository.findAll()).thenReturn(List.of(UsersEntity.builder().email("email").login("login").build()));
    //then
    assertEquals(expectedResult.get(0).email(),service.getAllUsers().get(0).email());
    assertEquals(expectedResult.get(0).login(),service.getAllUsers().get(0).login());
  }

  @Test
  public void should_throw_UserNotFoundException_when_user_does_not_exists(){
    //given
    String login= Mockito.anyString();
    String email= Mockito.anyString();
    //when
    when(repository.findUsersEntityByLoginAndEmail(login,email)).thenReturn(Optional.empty());
    //then
    assertThrows(UserNotFoundException.class,()->
        service.updateEmail(login,email,email));
  }

  @Test
  public void should_throw_UserEntityExistsException_when_user_try_to_change_email_(){
    //given
    String login= "login";
    String newEmail= "newEmail";
    String oldEmail= "oldEmial";
    //when
    when(repository.findUsersEntityByLoginAndEmail(login,oldEmail)).thenReturn(Optional.of(UsersEntity.builder().build()));
    when(repository.findUsersEntityByLoginAndEmail(login,newEmail)).thenReturn(Optional.of(UsersEntity.builder().build()));
    //then
    assertThrows(UserEntityExistsException.class,()->
        service.updateEmail(login,oldEmail,newEmail));
  }

  @Test
  public void should_throw_update_email_when_date_are_correct(){
    //given
    String login= "login";
    String newEmail= "newEmail";
    String oldEmail= "oldEmial";
    ArgumentCaptor<UsersEntity>captor= ArgumentCaptor.forClass(UsersEntity.class);
    //when
    when(repository.findUsersEntityByLoginAndEmail(login,oldEmail)).thenReturn(Optional.of(UsersEntity.builder().build()));
    when(repository.findUsersEntityByLoginAndEmail(login,newEmail)).thenReturn(Optional.empty());
    service.updateEmail(login,oldEmail,newEmail);
    //then
    verify(repository).save(captor.capture());
    assertEquals(captor.getValue().getEmail(),newEmail);
    assertEquals(captor.getValue().getLogin(),login);
  }
}