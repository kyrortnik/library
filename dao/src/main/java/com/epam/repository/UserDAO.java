package com.epam.repository;

import com.epam.entity.Pageable;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;

public interface UserDAO extends BaseDAO<User> {

    boolean changePassword(User user, String newPassword) throws DAOException;

    User findByLogin(User user) throws DAOException;

 //   UserDTO findUserByLogin(String login);

//    boolean findUserByLogin(User user);


     Pageable<UserDTO> findPageByParameters(Pageable<UserDTO> daoProductPageable) throws DAOException;
}
