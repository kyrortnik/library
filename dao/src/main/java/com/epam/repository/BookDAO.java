package com.epam.repository;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.exception.DAOException;


public interface BookDAO extends BaseDAO<BookRow> {

    /**
     * Gets pageable with BookRows to show on client side
     *
     * @param daoProductPageable, pageable with  request params and empty List<BookRow> elements
     * @return Pageable with elements
     * @throws DAOException throws DAOException
     */
    Pageable<BookRow> getBookRowsPage(Pageable<BookRow> daoProductPageable) throws DAOException;

    /**
     * Gets pageable with BookRows which were reserved for current user
     *
     * @param requestPageable, pageable with  request params and empty List<BookRow> elements
     * @param userId,          represents user to find reserved BookRows for
     * @return Pageable with reserved elements
     * @throws DAOException throws DAOException
     */
    Pageable<BookRow> getReservedBookRowsPage(Pageable<BookRow> requestPageable, Long userId) throws DAOException;

    /**
     * Gets pageable with BookRows which were added to order for current user
     *
     * @param pageableBookRowsRequest, pageable with  request params and empty List<BookRow> elements
     * @param userId                   represents user to find BookRows in order for
     * @return Pageable with ordered elements
     * @throws DAOException throws DAOException
     */
    Pageable<BookRow> getOrderedBookRowsPage(Pageable<BookRow> pageableBookRowsRequest, Long userId) throws DAOException;

}
