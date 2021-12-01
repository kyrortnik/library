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
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class UserServiceImplTest {

    //mock
    private final UserDAO userDAO = Mockito.mock(UserDAO.class, withSettings().verboseLogging());
    private final ServiceValidator serviceValidator = ServiceValidator.getInstance();

    //testing class
    private final UserService userService = new UserServiceImpl(userDAO, serviceValidator);

    //parameters
    private final long userId = 1L;
    private final String login = "login";
    private final String password = "password";
    private final String secondPassword = "password";
    private final String role = "role";

    // Page parameters
    private final Long pageNumber = 1L;
    private final long totalElements = 10;
    private final int limit = 10;
    private final String direction = "ASC";
    private final String sortBy = "login";


    private final List<UserDTO> emptyElements = new ArrayList<>();

    private final List<UserDTO> elements = Arrays.asList(

            new UserDTO(1L, "admin", "admin"),
            new UserDTO(2L, "kanye", "user"),
            new UserDTO(3L, "jim", "user")

    );


    @Test
    public void testRegister_positive() throws DAOException, ServiceException {
        User user = new User(login, password, role);
        UserDTO actualUserDTO = new UserDTO(userId, login, role);
        when(userDAO.register(user)).thenReturn(actualUserDTO);

        UserDTO foundUser = userService.register(user, secondPassword);

        verify(userDAO).register(user);
        assertEquals(actualUserDTO, foundUser);

    }

    @Test
    public void testRegister_ServiceException() throws DAOException {
        User user = new User(login, password, role);
        DAOException daoException = new DAOException("testing message");
        when(userDAO.register(user)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            userService.register(user, secondPassword);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());

    }

    @Test
    public void testLogin_positive() throws DAOException, ServiceException {
        UserDTO actualUserDTO = new UserDTO(userId, login, role);
        when(userDAO.login(login, password)).thenReturn(actualUserDTO);

        UserDTO foundUser = userService.login(login, password);

        verify(userDAO).login(login, password);
        assertEquals(foundUser, actualUserDTO);
    }


    @Test
    public void testLogin_ServiceException() throws DAOException {
        DAOException daoException = new DAOException("testing message");
        when(userDAO.login(login, password)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            userService.login(login, password);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());

    }

    @Test
    public void testGetUsersPage_positive() throws DAOException, ServiceException {
        Pageable<UserDTO> pageableEmptyElements = new Pageable<>(pageNumber, 0, limit, emptyElements, sortBy, direction);
        Page<UserDTO> pageEmptyElements = new Page<>(pageNumber, 0, limit, emptyElements, sortBy, direction);
        Pageable<UserDTO> pageableElements = new Pageable<>(pageNumber, 0, limit, elements, sortBy, direction);
        Page<UserDTO> pageWithElements = new Page<>(pageNumber, 0, limit, elements, sortBy, direction);

        when(userDAO.findUsersPageByParameters(pageableEmptyElements)).thenReturn(pageableElements);

        Page<UserDTO> returnPage = userService.getUsersPage(pageEmptyElements);

        verify(userDAO).findUsersPageByParameters(pageableEmptyElements);
        assertEquals(pageWithElements, returnPage);
    }

    @Test
    public void testGetUsersPage_ServiceException() throws DAOException {
        DAOException daoException = new DAOException("testing message");
        Pageable<UserDTO> pageableEmptyElements = new Pageable<>(pageNumber, 0, limit, emptyElements, sortBy, direction);
        Page<UserDTO> pageEmptyElements = new Page<>(pageNumber, 0, limit, emptyElements, sortBy, direction);

        when(userDAO.findUsersPageByParameters(pageableEmptyElements)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            userService.getUsersPage(pageEmptyElements);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());

    }

}
