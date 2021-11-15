package com.epam.repository;

import com.epam.entity.Order;
import com.epam.exception.DAOException;

public interface OrderDAO extends BaseDAO<Order> {

//    Order getByUserId(Order order) throws DAOException;

    Order getByUserId(Long userId) throws DAOException;


    boolean deleteFromOrder(Order orderToUpdate,String newProducts) throws DAOException;

//    Order getByUserIdAndProductId(Long userId,Long productId);
}
