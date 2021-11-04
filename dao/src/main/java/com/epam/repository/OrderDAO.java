package com.epam.repository;

import com.epam.entity.Order;

public interface OrderDAO extends AbstractDAO<Order> {

    Order getByUserId(Order order);

    Order getByUserId(Long userId);

    boolean deleteBbyUserId(Long userId);

    @Override
    boolean update(Order order);

//    Order getByUserIdAndProductId(Long userId,Long productId);
}
