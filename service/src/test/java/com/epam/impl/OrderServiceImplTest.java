package com.epam.impl;

import com.epam.OrderService;
import com.epam.entity.Order;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.OrderDAO;
import com.epam.validator.ServiceValidator;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class OrderServiceImplTest {

    //mock
    private final OrderDAO orderDAO = Mockito.mock(OrderDAO.class, withSettings().verboseLogging());
    private final ServiceValidator serviceValidator = ServiceValidator.getInstance();

    //testing class
    private final OrderService orderService = new OrderServiceImpl(orderDAO, serviceValidator);

    // Order parameters
    Long id = 1L;
    List<Long> bookIds = Arrays.asList(2L, 3L);
    Long userId = 1L;
    Long bookId = 2L;


    @Test
    public void testCreate_positive() throws DAOException, ServiceException {

        Order order = new Order(bookIds, userId);
        when(orderDAO.save(order)).thenReturn(true);

        boolean result = orderService.create(userId, bookIds);

        verify(orderDAO).save(order);
        assertTrue(result);

    }

    @Test
    public void testCreate_updateCase() throws DAOException, ServiceException {

        Order order = new Order(bookIds, userId);
        when(orderDAO.save(order)).thenReturn(false);
        when(orderDAO.update(order)).thenReturn(true);

        boolean result = orderService.create(userId, bookIds);

        verify(orderDAO).save(order);
        verify(orderDAO).update(order);
        assertTrue(result);

    }

    @Test
    public void testCreate_ServiceException() throws DAOException {

        Order order = new Order(bookIds, userId);
        DAOException daoException = new DAOException("testing message");

        when(orderDAO.save(order)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            orderService.create(userId, bookIds);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());
    }


    @Test
    public void testDelete_positive() throws DAOException, ServiceException {

        when(orderDAO.delete(id)).thenReturn(true);

        boolean result = orderService.delete(id);

        verify(orderDAO).delete(id);
        assertTrue(result);
    }


    @Test
    public void testDelete_ServiceException() throws DAOException {

        DAOException daoException = new DAOException("testing message");

        when(orderDAO.delete(id)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            orderService.delete(id);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());
    }


    @Test
    public void testDeleteFromOrder_positive() throws DAOException, ServiceException {
        when(orderDAO.deleteFromOrder(id, bookId)).thenReturn(true);

        boolean result = orderService.deleteFromOrder(userId, bookId);

        verify(orderDAO).deleteFromOrder(id, bookId);
        assertTrue(result);
    }

    @Test
    public void testDeleteFromOrder_ServiceException() throws DAOException {

        DAOException daoException = new DAOException("testing message");

        when(orderDAO.deleteFromOrder(id, bookId)).thenThrow(daoException);
        ServiceException actualException = new ServiceException();

        try {
            orderService.deleteFromOrder(id, bookId);
        } catch (ServiceException e) {
            actualException = e;

        }
        assertEquals((new ServiceException(daoException)).getMessage(), actualException.getMessage());
    }


}