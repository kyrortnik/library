package com.epam.repository;

import com.epam.entity.Order;
import com.epam.exception.DAOException;

public interface OrderDAO extends BaseDAO<Order> {

    /**
     * Finds order by provided userId - a user can have only one active order at a time
     *
     * @param userId, id to search user by
     * @return order found by userId
     * @throws DAOException throws DAOException
     */
    Order getByUserId(Long userId) throws DAOException;

    /**
     * Deletes a book from order
     *
     * @param userId, userId to find order by - a user can have only one active order at a time
     * @param bookId, book to be deleted from order
     * @return true if book is deleted, false if not
     * @throws DAOException throws DAOException
     */
    boolean deleteFromOrder(Long userId, Long bookId) throws DAOException;


}
