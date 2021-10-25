package com.epam;

import com.epam.entity.User;
import com.epam.entity.UserDTO;

import java.util.List;

public interface UserService {

    boolean registration(User user);

    UserDTO logination(User user);

    User findUserWithId(long id);

    boolean updateUser(User user);


    /*TODO change to (Long id)*/

    boolean deleteUser(User user);

    boolean findUserByLogin(User user);

    List<User> getUsers();


}
