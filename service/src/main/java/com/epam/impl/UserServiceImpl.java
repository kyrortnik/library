package com.epam.impl;

import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.DAOFactory;
import com.epam.repository.UserDAO;
import com.epam.UserService;

import java.util.List;


/** validations are needed*/

public class UserServiceImpl implements UserService {


    private static final UserDAO userDAO = DAOFactory.getInstance().createUserDAO();

    @Override
    public boolean registration(User user,String password2) {
        boolean equals = user.getPassword().equals(password2);
        try{
            return equals && userDAO.save(user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

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
    public boolean findUserByLogin(User user) {
        return userDAO.findUserByLogin(user);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getAll();
    }
}
