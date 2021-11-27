package com.epam.repository;

import com.epam.entity.BookRow;
import com.epam.entity.Pageable;
import com.epam.exception.DAOException;


public interface BookDAO extends BaseDAO<BookRow> {

    Pageable<BookRow> findPageWithParameters(Pageable<BookRow> daoProductPageable) throws DAOException;


    Pageable<BookRow> getReservedBookRowsPage(Pageable<BookRow> requestPageable, Long userId) throws DAOException;

    Pageable<BookRow> getOrderedBookRowsPage(Pageable<BookRow> pageableBookRowsRequest,Long userId) throws DAOException;

}
