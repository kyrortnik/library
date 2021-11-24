package com.epam.repository;

import com.epam.entity.Order;
import com.epam.exception.DAOException;

import java.util.List;

public interface OrderDAO extends BaseDAO<Order> {

//    Order getByUserId(Order order) throws DAOException;

    Order getByUserId(Long userId) throws DAOException;

    boolean deleteFromOrder(Long userId,Long bookId) throws DAOException;


}
