package com.epam;

import com.epam.entity.Order;
import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

import java.rmi.ServerException;
import java.util.List;

public interface OrderService {

    boolean save(Order order) throws ServiceException;

    boolean create(Order order) throws ServiceException;

    boolean update(Order order) throws ServiceException;

    boolean delete(Order order) throws ServiceException;

    List<Order> getAll() throws ServiceException;

    Order getByUserId(Order order) throws ServiceException;

    Order getByUserId(Long userId) throws ServiceException;

    boolean relationExists(Order order, String bookId);


    boolean productAlreadyOrdered(Reserve reserve) throws ServiceException;

    boolean productsAlreadyOrdered(List<Reserve> reserveList) throws ServiceException;





}
