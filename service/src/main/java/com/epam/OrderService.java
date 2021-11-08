package com.epam;

import com.epam.entity.Order;
import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

import java.rmi.ServerException;
import java.util.List;

public interface OrderService {

    boolean save(Order order) throws ServiceException;

    boolean create(Order order);

    boolean update(Order order);

    boolean delete(Order order);

    List<Order> getAll();

    Order getByUserId(Order order);

    Order getByUserId(Long userId);

    boolean relationExists(Order order, String bookId);


    boolean productAlreadyOrdered(Reserve reserve);

    boolean productsAlreadyOrdered(List<Reserve> reserveList);





}
