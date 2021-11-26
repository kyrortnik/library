package com.epam.repository;

import com.epam.entity.Pageable;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;

public interface UserDAO extends BaseDAO<User> {

    UserDTO register(User user) throws DAOException;

    UserDTO login(String login, String password) throws DAOException;

    Pageable<UserDTO> findUsersPageByParameters(Pageable<UserDTO> daoProductPageable) throws DAOException;
}
