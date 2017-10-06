package com.service;

import com.model.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
}
