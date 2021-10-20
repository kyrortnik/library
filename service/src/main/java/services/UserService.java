package services;

import entity.User;
import entity.UserDTO;

import java.util.List;

public interface UserService {

    boolean registration(User user);

    UserDTO logination(User user);

    User findUserWithId(long id);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    List<User> getUsers();


}
