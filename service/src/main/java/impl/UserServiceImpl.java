package impl;

import entity.User;
import repository.DAOFactory;
import repository.UserDAO;
import services.UserService;

public class UserServiceImpl implements UserService {


    private static final UserDAO userDAO = DAOFactory.getInstance().createUserDAO();

    @Override
    public boolean registrate(User user) {
        return userDAO.saveEntity(user);
    }
}
