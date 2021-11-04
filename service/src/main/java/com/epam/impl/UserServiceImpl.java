package com.epam.impl;

import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.DAOFactory;
import com.epam.repository.UserDAO;
import com.epam.UserService;

import java.util.List;

import static com.epam.validator.ServiceValidator.*;



public class UserServiceImpl implements UserService {


    private static final UserDAO userDAO = DAOFactory.getInstance().createUserDAO();

/**
 * Implemented methods
 * */
    @Override
    public boolean registration(User user,String password2) {
        if (!validation(user)){
            return false;
        }
        boolean passwordEquals = user.getPassword().equals(password2);
        try{
            return passwordEquals && userDAO.save(user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public UserDTO logination(User user) {
        if (!validation(user)){
            return null;
        }
        try {
            user = userDAO.get(user);
            return (user == null) ? null : new UserDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Methods getter from DB
     * */


    @Override
    public User get(User user) {
        if (!validation(user)){
            return null;
        }try{
            return userDAO.get(user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }



    @Override
    public User findUserWithId(long id) {
        if (!validation(id)) {
            return null;
        } try{
                return userDAO.getById(id);
            }catch (DAOException e){
                throw new ServiceException(e);
            }
        }


    @Override
    public boolean updateUser(User user) {
        if(!validation(user)){
            return false;
        }try{
            return userDAO.update(user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean deleteUser(User user) {
        if (!validation(user)){
            return false;
        }try{
            return userDAO.delete(user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    /**
     * TODO Do I need this?
     * */
    @Override
    public boolean findUserByLogin(User user) {
        if (!validation(user)){
            return false;
        }try{
            return userDAO.findUserByLogin(user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public List<User> getUsers() {
        try {
            return userDAO.getAll();
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }
}
