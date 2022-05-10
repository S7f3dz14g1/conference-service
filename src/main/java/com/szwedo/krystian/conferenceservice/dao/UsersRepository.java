package com.szwedo.krystian.conferenceservice.dao;

import com.szwedo.krystian.conferenceservice.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends CrudRepository<UsersEntity, UUID> {
  Optional<UsersEntity> findUsersEntityByLogin(String login);

  Optional<UsersEntity> findUsersEntityByLoginAndEmail(String login, String email);

  int countUsersEntitiesByLogin(String login);
}
