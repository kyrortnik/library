package com.epam;

import com.epam.entity.*;
import com.epam.exception.ServiceException;

import java.util.List;

public interface ReserveService {

    boolean save(Reserve reserve)throws ServiceException;

    List<Reserve> getReservesForUser(Long userId) throws ServiceException;

    boolean delete(Long userId,Long bookId) throws ServiceException;

    boolean deleteReservesByUserId(Long userId) throws ServiceException;

    int countReservesForUser(long userId) throws ServiceException;

    List<Reserve> getReservesByUserId(long userId, int offset) throws ServiceException;





}
