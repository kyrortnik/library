package com.epam;

import com.epam.exception.ServiceException;

import java.util.List;

public interface OrderService {

    /**
     * Validated params from client and passes them to DAO layer in order to create Order
     *
     * @param userId,  to created Order for
     * @param bookIds, to fill Order with
     * @return true if Order created, false if not
     * @throws ServiceException throws ServiceException
     */
    boolean create(Long userId, List<Long> bookIds) throws ServiceException;

    /**
     * Validated params from client and passes them to DAO layer in order to delete Book from Order
     *
     * @param userId, to find Order by - a user can have one order at a time
     * @param bookId, Book to delete from Order
     * @return true if Book is deleted from Order, false if not
     * @throws ServiceException throws ServiceException
     */
    boolean deleteFromOrder(Long userId, Long bookId) throws ServiceException;

    /**
     * Validated params from client and passes them to DAO layer in order to delete Order
     *
     * @param id, orderId to delete Order by
     * @return true if Order deleted,false if not
     * @throws ServiceException throws ServiceException
     */
    boolean delete(Long id) throws ServiceException;


}
