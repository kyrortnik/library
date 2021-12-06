package com.epam.repository;

import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;

public interface ReserveDAO extends BaseDAO<ReserveRow> {

    /**
     * Deletes reserve by userId and productId - a user can have multiple reserves at a time
     *
     * @param userId, to delete reserve by
     * @param bookId, to delete reserve by
     * @return true if reserve is deleted, false if not
     * @throws DAOException throws DAOException
     */
    boolean deleteByUserAndProduct(Long userId, Long bookId) throws DAOException;

}
