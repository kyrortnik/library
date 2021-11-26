package com.epam;

import com.epam.entity.Page;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;


public interface UserService {

    UserDTO register(User user, String secondPassword) throws ServiceException;

    UserDTO login(String login, String enteredPassword) throws ServiceException;

    Page<UserDTO> getUsersPage(Page<UserDTO> pageableRequest) throws ServiceException;


}
