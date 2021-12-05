package com.epam.repository;

import com.epam.entity.Pageable;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;

public interface UserDAO extends BaseDAO<User> {

    /**
     * Saves new user in database plus hashes and salts password
     *
     * @param user, User to save
     * @return UserDTO to work with on higher layers, null UserDTO if couldn't register
     * @throws DAOException throws DAOException
     */
    UserDTO register(User user) throws DAOException;

    /**
     * Checks if such user exists and credentials are correct
     *
     * @param login,    login received from client
     * @param password, password received from client
     * @return UserDTO to work with on higher layers, null UserDTO if didn't find such user
     * @throws DAOException throws DAOException
     */
    UserDTO login(String login, char[] password) throws DAOException;

    /**
     * Gets pageable with UserDTO - functionality for admin user
     *
     * @param daoProductPageable, pageable with  request params and empty List<UserDTO> elements
     * @return Pageable with UserDTO elements
     * @throws DAOException throwsDAOException
     */
    Pageable<UserDTO> findUsersPageByParameters(Pageable<UserDTO> daoProductPageable) throws DAOException;
}
