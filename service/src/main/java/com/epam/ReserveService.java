package com.epam;

import com.epam.entity.*;
import com.epam.exception.ServiceException;

import java.util.List;

public interface ReserveService {

    boolean save(Reserve reserve)throws ServiceException;

    List<Reserve> getReservesForUser(Long userId);


    boolean deleteReservesByUserId(Long userId);

    boolean productExistsInOrder(Reserve reserve);

    int countReservesForUser(long userId);

    List<Reserve> findReservationsByUserId(long userId,int row);




}
