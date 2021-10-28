package com.epam.repository;

import com.epam.entity.Order;

public interface OrderDAO extends AbstractDAO<Order> {

    Order getByUserId(Order order);
}
