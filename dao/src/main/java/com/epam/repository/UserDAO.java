package com.epam.repository;

import com.epam.entity.User;

public interface UserDAO extends AbstractDAO<User> {

    boolean changePassword(User user, String newPassword);

 //   UserDTO findUserByLogin(String login);

    boolean findUserByLogin(User user);
}
