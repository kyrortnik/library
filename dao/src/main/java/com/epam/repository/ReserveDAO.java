package com.epam.repository;

import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;

public interface ReserveDAO extends BaseDAO<ReserveRow> {

    boolean deleteByUserAndProduct(Long userId, Long bookId) throws DAOException;

}
