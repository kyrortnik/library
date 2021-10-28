package com.epam.repository;

import com.epam.entity.Reserve;

import java.util.List;

public interface ReserveDAO extends AbstractDAO<Reserve>{

    List<Reserve> getReservesForUser(Long userId);

    Reserve getByUserAndProductId(Reserve reserve);
}
