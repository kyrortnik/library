package com.epam.impl;

import com.epam.UserService;
import com.epam.entity.Page;
import com.epam.entity.Pageable;
import com.epam.entity.User;
import com.epam.entity.UserDTO;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.UserDAO;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class UserServiceImplTest {

    //mock
    private final UserDAO userDAO = Mockito.mock(UserDAO.class);

    //testing class
    private final UserService userService = new UserServiceImpl(userDAO);

    //parameters
    private final long userId = 1L;
    private final String login = "login";
    private final String password = "password";
    private final String role = "role";

    // Page parameters
    private int pageNumber = 1;
    private long totalElements = 10;
    private int limit = 10;
    private String direction = "ASC";
    private String sortBy = "login";

    //captures

//    @Test
//    public void testRegistration_positive() throws DAOException, ServiceException {
//
//        User user = new User(userId,login,password,role);
//        when(userDAO.find(user)).thenReturn(null);
//        when(userDAO.save(user)).thenReturn(true);
//        assertTrue(userService.register(user,"secondPassword"));
//    }

    @Test
    public void testLogination_positive() throws ServiceException, DAOException {
        User user = new User(userId,login,password,role);
        when(userDAO.findByLogin(user)).thenReturn(user);
        assertEquals(user,userService.login(login,password));
    }

    @Test
    public void testFind_positive() throws DAOException, ServiceException {
        User user = new User(userId,login,password,role);
        UserDTO userDTO = new UserDTO(userId,login,role);
        when(userDAO.find(user)).thenReturn(user);
        assertEquals(userDTO,userService.find(user));
    }

    @Test
    public void testFindById_positive() throws DAOException, ServiceException {
        User user = new User(userId,login,password,role);
        when(userDAO.findById(userId)).thenReturn(user);
        assertEquals(user,userService.findById(userId));
    }

    @Test
    public void testUpdateUser_positive() throws ServiceException, DAOException {
        User user = new User(userId,login,password,role);
        when(userDAO.update(user)).thenReturn(true);
        assertTrue(userService.updateUser(user));
    }

    @Test
    public void testDeleteUser_positive() throws DAOException, ServiceException {
        User user = new User(userId,login,password,role);
        when(userDAO.delete(userId)).thenReturn(true);
        assertTrue(userService.deleteUser(userId));

    }

    @Test
    public void testGetUsersPage_positive() throws DAOException, ServiceException {

        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO(userId,login,role));
        users.add(new UserDTO(2L,"log","pass"));
        users.add(new UserDTO(3L,"l","p"));

        List<UserDTO> emptyElements = new ArrayList<>();
        Page<UserDTO> userDTOPage = new Page<>(pageNumber, totalElements, limit, emptyElements, sortBy, direction);
        Pageable<UserDTO> userDTOPageable = new Pageable<>(pageNumber,totalElements,limit,emptyElements,sortBy,direction);
        Pageable<UserDTO> userPageableReturn = new Pageable<>(pageNumber,totalElements,limit,users,sortBy,direction);
        Page<UserDTO> userDTOPageExpected = new Page<>(pageNumber, totalElements, limit, users, sortBy, direction);
        when(userDAO.findPageByParameters(userDTOPageable)).thenReturn(userPageableReturn);
        assertEquals(userService.getUsersPage(userDTOPage),userDTOPageExpected);
    }

    @Test
    public void testGetUsers() throws DAOException, ServiceException {

        List<User> users = new ArrayList<>();
        users.add(new User(userId,login,role));
        users.add(new User(2L,"log","pass"));
        users.add(new User(3L,"l","p"));

        when(userDAO.getAll()).thenReturn(users);
        assertEquals(users,userService.getUsers());

    }
}
