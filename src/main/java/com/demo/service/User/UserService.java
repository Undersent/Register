package com.demo.service.User;

import com.demo.model.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    User findByConfirmationEmailToken(String confirmationEmailToken);
}
