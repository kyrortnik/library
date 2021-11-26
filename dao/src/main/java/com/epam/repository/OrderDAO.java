package com.epam.repository;

import com.epam.entity.Order;
import com.epam.exception.DAOException;

public interface OrderDAO extends BaseDAO<Order> {


    Order getByUserId(Long userId) throws DAOException;

    boolean deleteFromOrder(Long userId, Long bookId) throws DAOException;


}
