package com.epam;

import com.epam.entity.Order;

import java.rmi.ServerException;
import java.util.List;

public interface OrderService {

    boolean save(Order order) throws ServerException;

    boolean create(Order order);

    boolean update(Order order);

    boolean delete(Order order);

    List<Order> getAll();

    Order getByUserId(Order order);




}
