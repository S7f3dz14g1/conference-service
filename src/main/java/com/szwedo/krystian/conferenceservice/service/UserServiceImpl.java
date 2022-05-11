package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.dao.UsersRepository;
import com.szwedo.krystian.conferenceservice.entity.UsersEntity;
import com.szwedo.krystian.conferenceservice.exception.UserEntityExistsException;
import com.szwedo.krystian.conferenceservice.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class UserServiceImpl implements UserService {

  private final UsersRepository repository;

  @Override
  public void updateEmail(String login, String oldEmail, String newEmail) {
    Optional<UsersEntity> user = repository.findUsersEntityByLoginAndEmail(login, oldEmail);
    user.ifPresent(u -> {
      Optional<UsersEntity> otherUser = repository.findUsersEntityByLoginAndEmail(login, newEmail);
      otherUser.ifPresent(o -> {
        throw new UserEntityExistsException(login);
      });
      repository.save(UsersEntity.builder()
          .email(newEmail)
          .login(login)
          .id(u.getId())
          .build());
    });
    user.orElseThrow(() -> new UserNotFoundException(login));
  }
}