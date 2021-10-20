package repository;

import entity.User;
import entity.UserDTO;

public interface UserDAO extends AbstractDAO<User> {

    boolean changePassword(User user, String newPassword);

 //   UserDTO findUserByLogin(String login);
}
