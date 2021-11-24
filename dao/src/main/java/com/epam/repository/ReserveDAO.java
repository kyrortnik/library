package com.epam.repository;

import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;

import java.util.List;

public interface ReserveDAO extends BaseDAO<ReserveRow> {

    List<ReserveRow> findReservesForUser(Long userId) throws DAOException;

    ReserveRow findByUserAndProductId(ReserveRow reserve) throws DAOException;

    boolean deleteByUserId(Long userId) throws DAOException;

    int countReservesForUser(long userId) throws DAOException;

    List<ReserveRow> findReservesByUserId(long userId, int row) throws DAOException;

    boolean deleteByUserAndProduct(Long userId,Long bookId) throws DAOException;

    //    boolean orderForReserveExists(ReserveRow reserveRow) throws DAOException;


}
