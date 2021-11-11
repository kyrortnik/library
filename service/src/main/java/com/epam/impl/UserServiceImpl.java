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


    @Override
    public boolean registration(User user,String password2) throws ServiceException {
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
    public UserDTO logination(User user) throws ServiceException {
        if (!validation(user)){
            return null;
        }
        try {
            user = userDAO.find(user);
            return (user == null) ? null : new UserDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Methods getter from DB
     * */


    @Override
    public UserDTO get(User user) throws ServiceException {
        if (!validation(user)){
            return null;
        }try{
            return new UserDTO(userDAO.find(user));
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }



    @Override
    public User findUserWithId(long id) throws ServiceException {
        if (!validation(id)) {
            return null;
        } try{
                return userDAO.findById(id);
            }catch (DAOException e){
                throw new ServiceException(e);
            }
        }


    @Override
    public boolean updateUser(User user) throws ServiceException {
        if(!validation(user)){
            return false;
        }try{
            return userDAO.update(user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean deleteUser(User user) throws ServiceException {
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
   /* @Override
    public boolean findUserByLogin(User user) {
        if (!validation(user)){
            return false;
        }try{
            return userDAO.findUserByLogin(user);
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }*/

    @Override
    public List<User> getUsers() throws ServiceException {
        try {
            return userDAO.getAll();
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }
}
