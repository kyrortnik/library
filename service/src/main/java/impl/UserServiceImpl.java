package impl;

import entity.User;
import entity.UserDTO;
import exception.DAOException;
import exceptions.ServiceException;
import repository.DAOFactory;
import repository.UserDAO;
import services.UserService;

import java.util.List;


/** validations are needed*/

public class UserServiceImpl implements UserService {


    private static final UserDAO userDAO = DAOFactory.getInstance().createUserDAO();

    @Override
    public boolean registration(User user) {
        return userDAO.save(user);
    }

    @Override
    public UserDTO logination(User user) {
        try {
            user = userDAO.get(user);
            return (user == null) ? null : new UserDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserWithId(long id) {
        return userDAO.getById(id);
    }

    @Override
    public boolean updateUser(User user) {
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(User user) {
        return userDAO.delete(user);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getAll();
    }
}
