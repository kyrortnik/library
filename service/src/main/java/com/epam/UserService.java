package com.epam;

import com.epam.entity.Page;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;


public interface UserService {

    /**
     * Validated params from client and passes them to DAO layer in order to register User
     *
     * @param user,           User to register
     * @param secondPassword, second password which should be equal to password in User
     * @return UserDTO to work with on higher layers, UserDTO null if registration failed
     * @throws ServiceException throws ServiceException
     */
    UserDTO register(User user, char[] secondPassword) throws ServiceException;

    /**
     * Validated params from client and passes them to DAO layer in order to login User
     *
     * @param login,           User login
     * @param enteredPassword, User password
     * @return UserDTO to work with on higher layers, UserDTO null if login failed
     * @throws ServiceException throws ServiceException
     */
    UserDTO login(String login, char[] enteredPassword) throws ServiceException;

    /**
     * Validated params from client and passes them to DAO layer in order to get Page<UserDTO>
     *
     * @param pageableRequest, page with params and empty List<UserDTO> elements
     * @return page with updated List<UserDTO> elements
     * @throws ServiceException throws ServiceException
     */
    Page<UserDTO> getUsersPage(Page<UserDTO> pageableRequest) throws ServiceException;


}
