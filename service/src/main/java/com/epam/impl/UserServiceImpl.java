package com.epam.impl;

import com.epam.UserService;
import com.epam.entity.Page;
import com.epam.entity.Pageable;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.UserDAO;
import com.epam.validator.ServiceValidator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {


    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());
    private final UserDAO userDAO;

    private final ServiceValidator serviceValidator;

    public UserServiceImpl(UserDAO userDAO, ServiceValidator serviceValidator) {
        this.serviceValidator = serviceValidator;
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO register(User user, String secondPassword) throws ServiceException {

        serviceValidator.validation(user);
        serviceValidator.validation(secondPassword);
        UserDTO registeredUser = null;
        try {
            if (user.getPassword().equals(secondPassword)) {
                registeredUser = userDAO.register(user);
            }
            return registeredUser;
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }

    }

    @Override
    public UserDTO login(String login, String enteredPassword) throws ServiceException {
        serviceValidator.validation(login);
        serviceValidator.validation(enteredPassword);
        UserDTO foundUser;
        try {
            foundUser = userDAO.login(login, enteredPassword);
            return foundUser;
        } catch (DAOException e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }
    }


    @Override
    public Page<UserDTO> getUsersPage(Page<UserDTO> page) throws ServiceException {
        serviceValidator.validation(page);
        try {
            Pageable<UserDTO> pageableDAO = convertToPageableUserDTO(page);
            Pageable<UserDTO> filteredDaoPageable = userDAO.findUsersPageByParameters(pageableDAO);
            return convertToServicePage(filteredDaoPageable);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception: " + e);
            throw new ServiceException(e);
        }

    }


    private Page<UserDTO> convertToServicePage(Pageable<UserDTO> pageableDAO) {
        Page<UserDTO> page = new Page<>();
        page.setPageNumber(pageableDAO.getPageNumber());
        page.setLimit(pageableDAO.getLimit());
        page.setTotalElements(pageableDAO.getTotalElements());
        page.setElements(pageableDAO.getElements());
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
        pageableDAO.setSortBy(page.getSortBy());
        pageableDAO.setDirection(page.getDirection());
        return pageableDAO;
    }


}
