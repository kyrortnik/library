package com.epam;

import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

public interface ReserveService {

    boolean save(Reserve reserve) throws ServiceException;

    boolean delete(Long userId, Long bookId) throws ServiceException;


}
