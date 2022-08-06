package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.model.UserDetails;

import java.util.List;

public interface UserService {
    void updateEmail(String login, String oldEmail, String newEmail);

    List<UserDetails> getAllUsers();
}
