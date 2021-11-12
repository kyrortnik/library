package com.epam;

import com.epam.entity.Page;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.ServiceException;

import java.util.List;

public interface UserService {

   boolean registration(User user,String password2) throws ServiceException;

    UserDTO logination(User user) throws ServiceException;

    User findUserWithId(long id) throws ServiceException;

    boolean updateUser(User user) throws ServiceException;


    /*TODO change to (Long id)*/

    boolean deleteUser(User user) throws ServiceException;

//    boolean findUserByLogin(User user);

    List<User> getUsers() throws ServiceException;

    Page<UserDTO> getAll(Page<UserDTO> pageableRequest) throws ServiceException;

    UserDTO get(User user) throws ServiceException;


}
