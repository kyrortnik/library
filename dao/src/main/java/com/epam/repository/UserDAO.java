package com.epam.repository;

import com.epam.entity.User;
import com.epam.exception.DAOException;

public interface UserDAO extends AbstractDAO<User> {

    boolean changePassword(User user, String newPassword) throws DAOException;

 //   UserDTO findUserByLogin(String login);

//    boolean findUserByLogin(User user);

//    boolean registration(User user,String password2);
}
