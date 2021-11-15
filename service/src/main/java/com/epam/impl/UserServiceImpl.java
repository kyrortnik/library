package com.epam.impl;

import com.epam.entity.*;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.DAOFactory;
import com.epam.repository.UserDAO;
import com.epam.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epam.validator.ServiceValidator.*;

public class UserServiceImpl implements UserService {

    private static final UserDAO userDAO = DAOFactory.getInstance().createUserDAO();

    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());


    @Override
    public boolean registration(User user,String secondPassword) throws ServiceException {
        if (!validation(user)){
            return false;
        }
        boolean passwordEquals = user.getPassword().equals(secondPassword);
        try{
            return passwordEquals && (userDAO.find(user) == null) && userDAO.save(user);
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
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
            log.log(Level.SEVERE,"Exception: "+ e);
            throw new ServiceException(e);
        }
    }


    @Override
    public UserDTO find(User user) throws ServiceException {
        if (!validation(user)){
            return null;
        }try{
            return new UserDTO(userDAO.find(user));
        }catch (DAOException e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }



    @Override
    public User findById(long id) throws ServiceException {
        if (!validation(id)) {
            return null;
        } try{
                return userDAO.findById(id);
            }catch (DAOException e){
                log.log(Level.SEVERE,"Exception: " + e);
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
            log.log(Level.SEVERE,"Exception: " + e);
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
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }

    @Override
    public Page<UserDTO> getUsersPage(Page<UserDTO> page) throws ServiceException {
        try{
            Pageable<UserDTO> pageableDAO = convertToPageableUserDTO(page);
            Pageable<UserDTO> filteredDaoPageable = userDAO.findPageByParameters(pageableDAO);
            return convertToServicePage(filteredDaoPageable);
        }catch (Exception e){
            log.log(Level.SEVERE,"Exception: " + e);
            throw new ServiceException(e);
        }

    }


    private Page<UserDTO> convertToServicePage(Pageable<UserDTO> pageableDAO) {
        Page<UserDTO> page = new Page<>();
        page.setPageNumber(pageableDAO.getPageNumber());
        page.setLimit(pageableDAO.getLimit());
        page.setTotalElements(pageableDAO.getTotalElements());
        page.setElements(pageableDAO.getElements());
        page.setFilter((pageableDAO.getFilter()));
        page.setSortBy(pageableDAO.getSortBy());
        page.setDirection(pageableDAO.getDirection());
        return page;
    }


    private Pageable<UserDTO> convertToPageableUserDTO(Page<UserDTO> page) {
        final Pageable<UserDTO> pageableDAO = new Pageable<>();
        pageableDAO.setPageNumber(page.getPageNumber());
        pageableDAO.setLimit(page.getLimit());
        pageableDAO.setTotalElements(page.getTotalElements());
        pageableDAO.setElements(page.getElements());
        pageableDAO.setFilter(page.getFilter());
        pageableDAO.setSortBy(page.getSortBy());
        pageableDAO.setDirection(page.getDirection());
        return pageableDAO;
    }


    @Override
    public List<User> getUsers() throws ServiceException {
        try {
            return userDAO.getAll();
        }catch (DAOException e){
            throw new ServiceException(e);
        }

    }
}
