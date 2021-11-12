package com.epam.repository;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;

public interface UserDAO extends AbstractDAO<User> {

    boolean changePassword(User user, String newPassword) throws DAOException;

 //   UserDTO findUserByLogin(String login);

//    boolean findUserByLogin(User user);

//    boolean registration(User user,String password2);

     Pageable<UserDTO> findPageByFilter(Pageable<UserDTO> daoProductPageable) throws DAOException;
}
