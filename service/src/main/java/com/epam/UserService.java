package com.epam;

import com.epam.entity.Page;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;

import java.util.List;


public interface UserService {

   UserDTO register(User user,String secondPassword) throws ServiceException;

    UserDTO login(String login,String enteredPassword) throws ServiceException;

    UserDTO find(User user) throws ServiceException;

    User findById(long id) throws ServiceException;

    Page<UserDTO> getUsersPage(Page<UserDTO> pageableRequest) throws ServiceException;

    boolean updateUser(User user) throws ServiceException;

    boolean deleteUser(Long id) throws ServiceException;

    List<User> getUsers() throws ServiceException;






}
