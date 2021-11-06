package com.epam.repository;

import com.epam.entity.ReserveRow;

import java.util.List;

public interface ReserveDAO extends AbstractDAO<ReserveRow>{

    List<ReserveRow> getReservesForUser(Long userId);

    ReserveRow getByUserAndProductId(ReserveRow reserve);

    boolean deleteByUserId(Long userId);

    boolean orderForReserveExists(ReserveRow reserveRow);

    int countReservesForUser(long userId);


    List<ReserveRow> findReservationsByUserId(long userId, int row);


}
