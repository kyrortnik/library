package com.epam.impl;

import com.epam.entity.*;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.DAOFactory;
import com.epam.repository.UserDAO;
import com.epam.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            return passwordEquals && (userDAO.find(user) == null) && userDAO.save(user);
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

    @Override
    public Page<UserDTO> getAll(Page<UserDTO> pageRequest) throws ServiceException {
        try{
            Pageable<UserDTO> pageable = convertToPageableUserDTO(pageRequest);
            Pageable<UserDTO> filteredDaoPageable = userDAO.findPageByFilter(pageable);
            return convertToServicePage(filteredDaoPageable);
        }catch (Exception e){
            throw new ServiceException(e);
        }

    }




    private Page<UserDTO> convertToServicePage(Pageable<UserDTO> userRowPageable) {
        Page<UserDTO> page = new Page<>();
        page.setPageNumber(userRowPageable.getPageNumber());
        page.setLimit(userRowPageable.getLimit());
        page.setTotalElements(userRowPageable.getTotalElements());
        page.setElements(userRowPageable.getElements());
        page.setFilter((userRowPageable.getFilter()));
        page.setSortBy(userRowPageable.getSortBy());
        page.setDirection(userRowPageable.getDirection());
        return page;
    }

    private List<UserDTO> convertToUserDTOs( List<User> users) {
        List<UserDTO> list = new ArrayList<>();
        for (User user : users) {
            list.add(new UserDTO(user));
        }
        return list;
    }


    private Pageable<UserDTO> convertToPageableUserDTO(Page<UserDTO> pageableRequest) {
        final Pageable<UserDTO> pageable = new Pageable<>();
        pageable.setPageNumber(pageableRequest.getPageNumber());
        pageable.setLimit(pageableRequest.getLimit());
        pageable.setTotalElements(pageableRequest.getTotalElements());
        pageable.setElements(pageableRequest.getElements());
        pageable.setFilter(pageableRequest.getFilter());
        pageable.setSortBy(pageableRequest.getSortBy());
        pageable.setDirection(pageableRequest.getDirection());
        return pageable;
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
