package com.epam;

import com.epam.exception.ServiceException;

import java.util.List;

public interface OrderService {


    boolean create(Long userId, List<Long> bookIds) throws ServiceException;

    boolean deleteFromOrder(Long userId, Long bookId) throws ServiceException;

    boolean delete(Long id) throws ServiceException;


}
