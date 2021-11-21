package com.epam.impl;

import com.epam.OrderService;
import com.epam.entity.Order;
import com.epam.entity.Reserve;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.repository.OrderDAO;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    //mock
    private final OrderDAO orderDAO = Mockito.mock(OrderDAO.class);

    //testing class
    private final OrderService orderService = new OrderServiceImpl(orderDAO);

    // captors

    //parameters
//    private ArgumentCaptor<String> updatedProductCaptor = ArgumentCaptor.forClass(String.class);

    private final Order order = new Order(1L,"2 3",1L);



    @Test
    public void testCreate_positive() throws DAOException, ServiceException {

        when(orderDAO.getByUserId(order.getUserId())).thenReturn(null);
        when(orderDAO.save(order)).thenReturn(true);
        assertTrue(orderService.create(order));

    }

    @Test
    public void testUpdate_positive() throws DAOException,ServiceException{

        when(orderDAO.getByUserId(order.getUserId())).thenReturn(order);
        when(orderDAO.update(order)).thenReturn(true);
        assertTrue(orderService.update(order));

    }

    @Test
    public void testDelete_positive() throws DAOException,ServiceException {

        when(orderDAO.delete(order)).thenReturn(true);
        assertTrue(orderService.delete(order));

    }

    @Test
    public void testDeleteFromOrder_positive() throws DAOException, ServiceException {

        Order originalOrder = new Order(1L,"34",1L);
        Order existingOrder = new Order(1L,"2 3 34",1L);

        when(orderDAO.find(originalOrder)).thenReturn(existingOrder);
        when(orderDAO.deleteFromOrder(originalOrder,"2 3")).thenReturn(true);
        when(orderDAO.find(existingOrder)).thenReturn(existingOrder);

        assertTrue(orderService.deleteFromOrder(originalOrder));
    }

    @Test
    public void testGetByUserId_positive() throws DAOException,ServiceException {

        when(orderDAO.getByUserId(order.getUserId())).thenReturn(order);
        assertEquals(order,orderService.getByUserId(order.getUserId()));
    }

    //Product is already in order == true
    @Test
    public void testProductAlreadyOrdered_positive() throws ServiceException {
        Reserve reserve = new Reserve(1L,2L,3L);
        when(orderService.getByUserId(reserve.getUserId())).thenReturn(order);
        assertEquals(order,orderService.getByUserId(reserve.getUserId()));
        assertTrue(orderService.productAlreadyOrdered(reserve));
    }

    @Test
    public void testProductsAlreadyOrdered_positive() throws ServiceException{
        List<Reserve> reserves = new ArrayList<>();

        reserves.add(new Reserve(1L,1L,1L));
        reserves.add(new Reserve(2L,2L,2L));
        reserves.add(new Reserve(3L,3L,3L));

        assertFalse(orderService.productsAlreadyOrdered(reserves));

    }




}