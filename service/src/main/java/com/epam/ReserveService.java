package com.epam;

import com.epam.entity.*;
import com.epam.exception.ServiceException;

import javax.xml.ws.Service;
import java.util.List;

public interface ReserveService {

    boolean save(Reserve reserve)throws ServiceException;


   List<Reserve> getReservesForUser(Long userId) throws ServiceException;


   boolean delete(Reserve reserve) throws ServiceException;

    boolean deleteReservesByUserId(Long userId) throws ServiceException;

    boolean productExistsInOrder(Reserve reserve) throws ServiceException;

    int countReservesForUser(long userId) throws ServiceException;

    List<Reserve> findReservationsByUserId(long userId,int row) throws ServiceException;

//    Reserve findReserveByUserAndProduct(Reserve reserve) throws ServiceException;




}
