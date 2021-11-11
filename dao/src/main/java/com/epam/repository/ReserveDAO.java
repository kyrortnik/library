package com.epam.repository;

import com.epam.entity.ReserveRow;
import com.epam.exception.DAOException;

import java.util.List;

public interface ReserveDAO extends AbstractDAO<ReserveRow>{

    List<ReserveRow> getReservesForUser(Long userId) throws DAOException;

    ReserveRow getByUserAndProductId(ReserveRow reserve) throws DAOException;

    boolean deleteByUserId(Long userId) throws DAOException;

    @Override
    boolean delete(ReserveRow reserveRow) throws DAOException;

    @Override
    ReserveRow find(ReserveRow reserveRow) throws DAOException;

    boolean orderForReserveExists(ReserveRow reserveRow) throws DAOException;

    int countReservesForUser(long userId) throws DAOException;


    List<ReserveRow> findReservationsByUserId(long userId, int row) throws DAOException;


}
