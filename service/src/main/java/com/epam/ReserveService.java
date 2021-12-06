package com.epam;

import com.epam.entity.Reserve;
import com.epam.exception.ServiceException;

public interface ReserveService {

    /**
     * Validated params from client and passes them to DAO layer in order to save Reserve
     *
     * @param reserve, Reserve to save
     * @return true if Reserve saved, false if not
     * @throws ServiceException throws ServiceException
     */
    boolean save(Reserve reserve) throws ServiceException;

    /**
     * Validated params from client and passes them to DAO layer in order to delete Reserve
     *
     * @param userId, to find needed Reserve by - a user can have multiple Reserves at a time
     * @param bookId, to find needed Reserve by - a user can have multiple Reserves at a time
     * @return true if Reserve deleted, false if not
     * @throws ServiceException throws ServiceException
     */
    boolean delete(Long userId, Long bookId) throws ServiceException;


}
